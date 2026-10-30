package config;

/**
 * Facebook Login settings. App ID, secret and redirect URI are read from
 * environment variables so they never end up in the repository.
 *
 * @author Hai Long
 */
public class FacebookConfig {

    public static final String GRAPH_VERSION = "v21.0";
    public static final String DIALOG_URL = "https://www.facebook.com/" + GRAPH_VERSION + "/dialog/oauth";
    public static final String TOKEN_URL = "https://graph.facebook.com/" + GRAPH_VERSION + "/oauth/access_token";
    public static final String ME_URL = "https://graph.facebook.com/" + GRAPH_VERSION + "/me";
    public static final String SCOPE = "public_profile,email";

    public static String getAppId() {
        return System.getenv("FB_APP_ID");
    }

    public static String getAppSecret() {
        return System.getenv("FB_APP_SECRET");
    }

    public static String getRedirectUri() {
        return System.getenv("FB_REDIRECT_URI");
    }

    public static boolean isConfigured() {
        return !isBlank(getAppId()) && !isBlank(getAppSecret()) && !isBlank(getRedirectUri());
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
