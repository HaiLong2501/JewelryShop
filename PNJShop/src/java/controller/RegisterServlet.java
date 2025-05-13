/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author Hai Long
 */
@WebServlet(name="RegisterServlet", urlPatterns={"/register"})
public class RegisterServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        UserDAO uDao = new UserDAO();

        String service = request.getParameter("service");

        String state = request.getParameter("state");

        System.out.println("state: " + state);

        String redirectUrl = request.getParameter("redirectUrl");
        
        if ("register".equals(service)) {

            String submit = request.getParameter("submit");

            if (submit == null) {

                request.setAttribute("redirectUrl", redirectUrl);

                request.getRequestDispatcher("jsp/Register.jsp").forward(request, response);
            } else {

                String fullName = request.getParameter("fullName");

                String userName = request.getParameter("userName");

                String email = request.getParameter("email");

                String password = request.getParameter("password");

                String passwordCheck = request.getParameter("passwordCheck");

//                String phone = request.getParameter("phone");
                String gender = request.getParameter("gender");

                String error = "";

                boolean check = false;

                if (fullName == null || fullName.trim().isEmpty()
                        || email == null || email.trim().isEmpty()
                        || password == null || password.trim().isEmpty()
                        || passwordCheck == null || passwordCheck.trim().isEmpty()
                        || userName == null || userName.trim().isEmpty()
                        //                        || phone == null || phone.trim().isEmpty()
                        || gender == null || gender.isEmpty()) {

                    error = "Vui lòng điền đầy đủ thông tin!";

                    check = true;
                }

//                if (uDao.checkPhone(phone) != null) {
//                    error = "Số điện thoại đã tồn tại";
//
//                    check = true;
//                }
                if (uDao.checkEmail(email) != null) {
                    error = "Email đã tồn tại";

                    check = true;
                }

                if (uDao.checkUserName(userName) != null) {
                    error = "Tên đăng nhập đã tồn tại";

                    check = true;
                }

                if (!password.equals(passwordCheck)) {
                    error = "Mật khẩu nhập lại không khớp!";

                    check = true;
                }

                if (check) {
                    request.setAttribute("error", error);

                    request.setAttribute("redirectUrl", redirectUrl);

                    request.getRequestDispatcher("jsp/Register.jsp").forward(request, response);
                } else {
                    System.out.println(gender);
                    User u = new User(fullName, password, 2, email, true, userName, gender);

                    System.out.println(u);

                    uDao.registerUser(u);

                    request.setAttribute("report", "Vui lòng đăng nhập để tiếp tục mua hàng");

                    request.setAttribute("service", "login");

                    request.setAttribute("redirectUrl", redirectUrl);

                    request.getRequestDispatcher("jsp/Login.jsp").forward(request, response);
                }
            }
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
