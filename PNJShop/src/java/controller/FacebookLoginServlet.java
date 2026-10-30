package controller;

import config.FacebookConfig;
import dal.UserDAO;
import dal.UserFacebookDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import model.FacebookProfile;
import model.User;

/**
 * Login with Facebook (OAuth 2.0 authorization code flow).
 * GET /login-facebook?redirectUrl=... starts the flow,
 * Facebook then calls back GET /login-facebook?code=...&state=...
 *
 * @author Hai Long
 */
@WebServlet(name = "FacebookLoginServlet", urlPatterns = {"/login-facebook"})
public class FacebookLoginServlet extends HttpServlet {

    private static final String LOGIN_PAGE = "login?service=login&fbError=";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("state") != null) {
            handleCallback(request, response);
        } else {
            startLogin(request, response);
        }
    }

    private void startLogin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        if (!FacebookConfig.isConfigured()) {
            System.out.println("Facebook login is not configured (FB_APP_ID / FB_APP_SECRET / FB_REDIRECT_URI)");
            response.sendRedirect(LOGIN_PAGE + "unavailable");
            return;
        }

        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        String state = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        HttpSession session = request.getSession(true);
        session.setAttribute("fbState", state);
        session.setAttribute("fbRedirect", safeRedirect(request, request.getParameter("redirectUrl")));

        response.sendRedirect(FacebookConfig.DIALOG_URL
                + "?client_id=" + encode(FacebookConfig.getAppId())
                + "&redirect_uri=" + encode(FacebookConfig.getRedirectUri())
                + "&state=" + encode(state)
                + "&scope=" + encode(FacebookConfig.SCOPE)
                + "&response_type=code");
    }

    private void handleCallback(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(true);
        String expectedState = (String) session.getAttribute("fbState");
        String redirect = (String) session.getAttribute("fbRedirect");
        session.removeAttribute("fbState");
        session.removeAttribute("fbRedirect");

        if (expectedState == null || !expectedState.equals(request.getParameter("state"))) {
            response.sendRedirect(LOGIN_PAGE + "invalid");
            return;
        }
        if (request.getParameter("error") != null) {
            response.sendRedirect(LOGIN_PAGE + "cancelled");
            return;
        }

        try {
            UserFacebookDAO fbDao = new UserFacebookDAO();
            FacebookProfile profile = fbDao.getProfile(fbDao.getToken(request.getParameter("code")));

            UserDAO uDao = new UserDAO();
            // Look up by Facebook id first so returning users never get a duplicate account
            User u = uDao.findByFacebookID(profile.getId());

            if (u == null) {
                String email = profile.getEmail();
                if (email == null || email.trim().isEmpty()) {
                    response.sendRedirect(LOGIN_PAGE + "noemail");
                    return;
                }

                User existing = uDao.checkEmail(email);
                if (existing != null) {
                    if (existing.getFacebookID() != null) {
                        // email already belongs to an account linked to another Facebook
                        response.sendRedirect(LOGIN_PAGE + "linked");
                        return;
                    }
                    uDao.setFacebookID(existing.getUserID(), profile.getId());
                    u = existing;
                } else {
                    User newUser = new User();
                    newUser.setName(profile.getName());
                    newUser.setEmail(email);
                    newUser.setFacebookID(profile.getId());
                    uDao.insertFacebookUser(newUser);
                    u = uDao.findByFacebookID(profile.getId());
                    if (u == null) {
                        response.sendRedirect(LOGIN_PAGE + "failed");
                        return;
                    }
                }
            }

            if (!u.getActivate()) {
                response.sendRedirect(LOGIN_PAGE + "locked");
                return;
            }

            // Always reload from the database so the session user has a userID
            User sessionUser = uDao.findByID(u.getUserID());
            if (sessionUser == null) {
                response.sendRedirect(LOGIN_PAGE + "failed");
                return;
            }
            session.setAttribute("user", sessionUser);

            if (sessionUser.getRoleID() == 1) {
                response.sendRedirect("admin");
            } else {
                response.sendRedirect(redirect != null ? redirect : "home");
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Facebook login failed: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            if (!response.isCommitted()) {
                response.sendRedirect(LOGIN_PAGE + "failed");
            }
        }
    }

    /**
     * Only allows going back to a page of this shop. Existing pages send
     * window.location.href, so absolute URLs on the same host and port are
     * accepted and reduced to path + query; anything else goes to home.
     */
    private String safeRedirect(HttpServletRequest req, String url) {
        if (url == null || url.trim().isEmpty() || url.contains("\\")) {
            return "home";
        }
        try {
            URI uri = new URI(url.trim());
            if (uri.getScheme() != null || uri.getHost() != null) {
                String scheme = uri.getScheme() == null ? "" : uri.getScheme().toLowerCase();
                if (!scheme.equals("http") && !scheme.equals("https")) {
                    return "home";
                }
                int port = uri.getPort() != -1 ? uri.getPort() : (scheme.equals("https") ? 443 : 80);
                if (!req.getServerName().equalsIgnoreCase(uri.getHost()) || port != req.getServerPort()) {
                    return "home";
                }
                String path = uri.getRawPath();
                if (path == null || path.isEmpty() || path.startsWith("//")) {
                    return "home";
                }
                return uri.getRawQuery() != null ? path + "?" + uri.getRawQuery() : path;
            }
            if (url.startsWith("//")) {
                return "home";
            }
            return url;
        } catch (URISyntaxException e) {
            return "home";
        }
    }

    private static String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
