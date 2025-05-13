/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BrandDAO;
import dal.CategoryDAO;
import dal.MaterialDAO;
import dal.ProductDAO;
import dal.ProductLineDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import model.Brand;
import model.Category;
import model.Material;
import model.Product;
import model.ProductLine;
import model.User;

/**
 *
 * @author Hai Long
 */
@WebServlet(name = "Home/Servlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryDAO cDao = new CategoryDAO();

        MaterialDAO mDao = new MaterialDAO();

        HttpSession session = request.getSession(true);

        User u = (User) session.getAttribute("user");

        ProductLineDAO plDao = new ProductLineDAO();

        ProductDAO pDao = new ProductDAO();

        BrandDAO bDao = new BrandDAO();

        String service = request.getParameter("service");

        if (service == null) {
            service = "ListHome";
        }

        if (service.equals("ListHome")) {
            String search = request.getParameter("search");

            Vector<Category> listCate = cDao.getAllCategories("select * from tblCategories");

            Vector<Material> listMate = mDao.getAllMaterials("select * from tblMaterials");

            Vector<ProductLine> listProdLine = plDao.getAllProductLine("select * from tblProductLines");

            Vector<Brand> listBrand = bDao.getAllBrands("select * from tblBrands");

            Vector<Product> listProd = pDao.getAllProduct("select * from tblProducts");

            request.setAttribute("listProd", listProd);

            request.setAttribute("listCate", listCate);

            request.setAttribute("listMate", listMate);

            request.setAttribute("listProdLine", listProdLine);

            request.setAttribute("listBrand", listBrand);

        }

        request.getRequestDispatcher("jsp/Home.jsp").forward(request, response);

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
