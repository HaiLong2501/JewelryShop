/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import model.Category;

/**
 *
 * @author Hai Long
 */
@WebServlet(name = "CategoryServletAdmin", urlPatterns = {"/category"})
public class CategoryServletAdmin extends HttpServlet {

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
        
        String service = request.getParameter("service");
        
        if (service == null) {
            service = "listCategory";
        }
        
        CategoryDAO cDao = new CategoryDAO();
        
        
        
        if (service.equals("listCategory")) {
            Vector<Category> listCate= cDao.getAllCategories("select * from tblCategories");
            String submit = request.getParameter("submit");
            String key = request.getParameter("search");
            System.out.println("submit: " + submit);
            System.out.println("key: " + key);
            if(submit != null){
                listCate= cDao.getAllCategories("select * from tblCategories where categoryName like N'%" + key + "%' "
                        + "or categoryID = '" + key + "'");
            }
            request.setAttribute("listCate", listCate);
            request.getRequestDispatcher("admin/ListCategory.jsp").forward(request, response);
        }
        
        if(service.equals("updateCategory")){
            String description = request.getParameter("description");
            
            String cId = request.getParameter("cId");
            
            System.out.println("cId: " + cId);
            
            Category c = cDao.searchCategory(cId);
            
            System.out.println("Cate: " + c);
            
            System.out.println("desc: " + description);
            
            c.setDescribe(description);
            
            System.out.println("cate after set: " + c);
            
            cDao.updateCategory(c);
            
            response.sendRedirect("category");
        }
        
        if(service.equals("insertCategory")){
            String categoryID = request.getParameter("categoryID");
            
            Vector<Category> listCate= cDao.getAllCategories("select * from tblCategories");
            request.setAttribute("listCate", listCate);
            
            if(cDao.searchCategory(categoryID) != null){
                request.setAttribute("errorID", "ID đã tồn tại");
                request.getRequestDispatcher("admin/ListCategory.jsp").forward(request, response);
            }else{
                
                String categoryName = request.getParameter("categoryName");

                String description = request.getParameter("description");

                String image = request.getParameter("image");

                Category c = new Category(categoryID, categoryName, description, image);

                cDao.insertCategory(c);

                response.sendRedirect("category");
            }
            
        }
        
        if(service.equals("deleteCategory")){
            String cId = request.getParameter("cId");
            Category c = cDao.searchCategory(cId);
            cDao.deleteCategory(c);
            response.sendRedirect("category");
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
