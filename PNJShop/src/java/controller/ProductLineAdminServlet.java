/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.ProductLineDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import model.ProductLine;

/**
 *
 * @author Hai Long
 */
@WebServlet(name="ProductLineAdminServlet", urlPatterns={"/productline"})
public class ProductLineAdminServlet extends HttpServlet {
   
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
        String service = request.getParameter("service");
        
        if (service == null) {
            service = "listProductLine";
        }
        
        ProductLineDAO cDao = new ProductLineDAO();
        
        
        
        if (service.equals("listProductLine")) {
            Vector<ProductLine> listProdLine= cDao.getAllProductLine("select * from tblProductLines");
            String submit = request.getParameter("submit");
            String key = request.getParameter("search");
            System.out.println("submit: " + submit);
            System.out.println("key: " + key);
            if(submit != null){
                listProdLine= cDao.getAllProductLine("select * from tblProductLines where productLineName like N'%" + key + "%' "
                        + "or productLineID = '" + key + "'");
            }
            request.setAttribute("listProdLine", listProdLine);
            request.getRequestDispatcher("admin/ListProductLine.jsp").forward(request, response);
        }
        
        if(service.equals("updateProdLine")){
            String description = request.getParameter("description");
            
            String plId = request.getParameter("plId");
            
            System.out.println("plId: " + plId);
            
            ProductLine c = cDao.searchProductLine(plId);
            
            System.out.println("Cate: " + c);
            
            System.out.println("desc: " + description);
            
            c.setDescribe(description);
            
            System.out.println("cate after set: " + c);
            
            cDao.updateProductLine(c);
            
            response.sendRedirect("productline");
        }
        
        if(service.equals("insertProductLine")){
            String prodLineID = request.getParameter("productLineID");
            
            Vector<ProductLine> listProdLine= cDao.getAllProductLine("select * from tblProductLines");
            request.setAttribute("listProdLine", listProdLine);
            
            if(cDao.searchProductLine(prodLineID) != null){
                request.setAttribute("errorID", "ID đã tồn tại");
                request.getRequestDispatcher("admin/ListProductLine.jsp").forward(request, response);
            }else{
                
                String productLineName = request.getParameter("productLineName");

                String description = request.getParameter("description");

                String image = request.getParameter("image");

                ProductLine c = new ProductLine(prodLineID, productLineName, description, image);

                cDao.insertProductLine(c);

                response.sendRedirect("productline");
            }
            
        }
        
        if(service.equals("deleteProductLine")){
            String plId = request.getParameter("plId");
            ProductLine c = cDao.searchProductLine(plId);
            cDao.deleteProductLine(c);
            response.sendRedirect("productline");
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
