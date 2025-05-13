/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
import java.util.Vector;
import model.Category;
import model.Material;
import model.Product;
import model.ProductLine;

/**
 *
 * @author Hai Long
 */
@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {

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
        ProductDAO pDao = new ProductDAO();

        CategoryDAO cDao = new CategoryDAO();

        ProductLineDAO plDao = new ProductLineDAO();

        MaterialDAO mDao = new MaterialDAO();

        String submit = request.getParameter("submit");

        String service = request.getParameter("service");

        if (service == null) {
            service = "listProduct";
        }

        Vector<Product> listProd;

        Vector<Category> listCate = cDao.getAllCategories("select * from tblCategories");

        Vector<ProductLine> listProdLine = plDao.getAllProductLine("select * from tblProductLines");

        Vector<Material> listMate = mDao.getAllMaterials("select * from tblMaterials");

        request.setAttribute("listCate", listCate);
        request.setAttribute("listProdLine", listProdLine);
        request.setAttribute("listMate", listMate);

        String key = request.getParameter("search");

        if (service.equals("listProduct")) {
            if (submit == null) {
                listProd = pDao.getAllProduct("select * from tblProducts");

            } else {

                listProd = pDao.getAllProduct("select * from tblProducts where productName like N'%" + key + "%' or "
                        + "productID = '" + key + "'");
            }

            System.out.println("done");

            request.setAttribute("listProd", listProd);
            request.getRequestDispatcher("admin/ListProduct.jsp").forward(request, response);
        }

        if (service.equals("updateProduct")) {
            String pId = request.getParameter("pId");
            System.out.println("pId: " + pId);
            String productName = request.getParameter("productName");
            String priceStr = request.getParameter("price");

            priceStr = priceStr.replace(".", "");
            System.out.println("priceStr: " + priceStr);
            double price = Double.parseDouble(priceStr);

            double discount = Double.parseDouble(request.getParameter("discount"));
            String statusStr = request.getParameter("status");
            System.out.println("status: " + statusStr);
            int status = "on".equalsIgnoreCase(statusStr) ? 1 : 0;
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            Product p = new Product(pId, productName, price, discount, status, quantity);

            System.out.println(p);

            pDao.updateProduct(p);

            response.sendRedirect("admin");
        }

        if (service.equals("deleteProduct")) {
            String pId = request.getParameter("pId");
            pDao.deleteProduct(pId);
            response.sendRedirect("admin");
        }

        if (service.equals("insertProduct")) {
            String productID = request.getParameter("productID");
            listProd = pDao.getAllProduct("select * from tblProducts");
            if (pDao.searchProduct(productID) != null) {
                System.out.println("error");
                request.setAttribute("errorID", "ID is existed");
                request.setAttribute("listProd", listProd);
                request.getRequestDispatcher("admin/ListProduct.jsp").forward(request, response);
            } else {

                String productName = request.getParameter("productName");
                double price = Double.parseDouble(request.getParameter("price"));
                double discount = 0;
                String discounStr = request.getParameter("discount");
                if (discounStr != null || !discounStr.trim().isEmpty()) {
                    try {
                        discount = Double.parseDouble(discounStr);
                    } catch (NumberFormatException e) {
                        discount = 0;
                    }
                }

                String cateID = request.getParameter("cate");
                String prodLineID = request.getParameter("prodLine");
                int mateID = Integer.parseInt(request.getParameter("mate"));
                String image = request.getParameter("image");
                int status = Integer.parseInt(request.getParameter("status"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                Product p = new Product(productID, productName, price, discount, cateID, prodLineID, mateID, image, status, quantity);

                pDao.insertProduct(p);

                response.sendRedirect("admin");

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
