
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.UserDAO;
import dal.UserGoogleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.UserGoogleDto;

/**
 *
 * @author Hai Long
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(true);

        UserDAO uDao = new UserDAO();

        UserGoogleDAO ugDao = new UserGoogleDAO();

        String service = request.getParameter("service");

        String state = request.getParameter("state");

        System.out.println("state: " + state);

        String redirectUrl = request.getParameter("redirectUrl");

        if ("logout".equals(service)) {
            session.invalidate();

            if (redirectUrl != null) {
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect("home");
            }
        }

        if (state != null) {

            String paramsState[] = state.split("\\|");

            if ("loginGoogle".equals(paramsState[0])) {

                String code = request.getParameter("code");
                String accessToken = ugDao.getToken(code);
                User u = ugDao.getUserInfo(accessToken);
                System.out.println("User: " + u);
                if (uDao.checkEmail(u.getEmail()) == null) {
                    u.setActivate(true);
                    u.setRoleID(2);
                    u.setUserName(u.getEmail());
                    System.out.println("User to login: " + u);
                    uDao.insertUser(u);
                }
                System.out.println("User login: " + u);
                System.out.println("Email: " + u.getEmail());
                session.setAttribute("user", u);
                response.sendRedirect(paramsState[1]);
            }
        }

        if ("login".equals(service)) {

            String submit = request.getParameter("submit");

            if (submit == null) {
                request.setAttribute("redirectUrl", redirectUrl);

                request.setAttribute("service", service);

                request.getRequestDispatcher("jsp/Login.jsp").forward(request, response);
            } else {

                String userName = request.getParameter("userName");

                String password = request.getParameter("password");

                if (uDao.checkLogin(userName, password)) {
                    User u = uDao.searchUser(userName);

                    System.out.println("user input: " + u);

                    session.setAttribute("user", u);

                    if (redirectUrl != null && !redirectUrl.isEmpty()) {
                        if (u != null) {
                            if (u.getRoleID() == 1) {
                                response.sendRedirect("admin");
                            } else {
                                response.sendRedirect(redirectUrl);
                            }
                        }
                    } else {
                        response.sendRedirect(request.getContextPath() + "/index.html"); // Mặc định về trang chính
                    }

                } else {
                    request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");

                    request.setAttribute("redirectUrl", redirectUrl);
                    request.getRequestDispatcher("jsp/Login.jsp").forward(request, response);
                }
            }
        }
        

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
