/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dal.BrandDAO;
import dal.CategoryDAO;
import dal.MaterialDAO;
import dal.ProductDAO;
import dal.ProductDetailDAO;
import dal.ProductLineDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;
import model.Brand;
import model.Category;
import model.Material;
import model.Product;
import model.ProductDetail;
import model.ProductLine;

/**
 *
 * @author Hai Long
 */
@WebServlet(name="DetailServlet", urlPatterns={"/detail"})
public class DetailServlet extends HttpServlet {
   
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
        
        CategoryDAO cDao = new CategoryDAO();

        MaterialDAO mDao = new MaterialDAO();

        ProductLineDAO plDao = new ProductLineDAO();
        
        ProductDAO pDao = new ProductDAO();
        
        ProductDetailDAO pdDao = new ProductDetailDAO();
        
        BrandDAO bDao = new BrandDAO();
        
        String pId = request.getParameter("pId");
        
        Product p = pDao.searchProduct(pId);
        
        ProductDetail pd = pdDao.getDetailById(pId);
        
        Brand b = bDao.getBrandByBrandID(pd.getBrandID());
        
        Vector<Category> listCate = cDao.getAllCategories("select * from tblCategories");
        
        Vector<Material> listMate = mDao.getAllMaterials("select * from tblMaterials");
        
        Vector<ProductLine> listProdLine = plDao.getAllProductLine("select * from tblProductLines");
        
        request.setAttribute("listCate", listCate);
        
        request.setAttribute("listMate", listMate);
        
        request.setAttribute("listProdLine", listProdLine);
        
        request.setAttribute("product", p);
        
        request.setAttribute("productDetail", pd);
        
        request.setAttribute("brand", b);
        
        request.getRequestDispatcher("jsp/ProductDetail.jsp").forward(request, response);
        
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
