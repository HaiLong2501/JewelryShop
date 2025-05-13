/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
import jakarta.servlet.http.HttpSession;
import java.util.Vector;
import model.Category;
import model.Material;
import model.Product;
import model.ProductLine;
import model.User;

/**
 *
 * @author Hai Long
 */
@WebServlet(name = "ListProductServlet", urlPatterns = {"/list"})
public class ListProductServlet extends HttpServlet {

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

        User u = (User) session.getAttribute("user");

        CategoryDAO cDao = new CategoryDAO();

        MaterialDAO mDao = new MaterialDAO();

        ProductLineDAO plDao = new ProductLineDAO();

        ProductDAO pDao = new ProductDAO();

        ProductDetailDAO pdDao = new ProductDetailDAO();

        String cId = request.getParameter("cId");

        String plId = request.getParameter("plId");

        String mId = request.getParameter("mId");

        String bId = request.getParameter("bId");
        
        String service = request.getParameter("service");

//        String key = request.getParameter("search");
        String key = request.getParameter("key");

        Vector<Category> listCate = cDao.getAllCategories("select * from tblCategories");

        Vector<Material> listMate = mDao.getAllMaterials("select * from tblMaterials");

        Vector<ProductLine> listProdLine = plDao.getAllProductLine("select * from tblProductLines");

        request.setAttribute("listCate", listCate);

        request.setAttribute("listMate", listMate);

        request.setAttribute("listProdLine", listProdLine);

        Vector<Product> listProd = new Vector<>();

        if (key != null) {
//            listProd = pDao.getAllProduct("select * from tblProducts where productName like '%"+key+"%' or\n"
//                    + "productName like \n"
//                    + "(select '%' + SUBSTRING('"+key+"', 3, len('"+key+"') - 1) + '%'\n"
//                    + "from tblProducts where productID = '"+key+"') ");

            listProd = pDao.getAllProduct("select * from tblProducts where productName like N'%" + key + "%' or "
                    + "productID = '" + key + "'");

            request.setAttribute("listProd", listProd);

        }
        
        if(service != null){
            listProd = pDao.getAllProduct("select * from tblProducts where discount > 0");
            request.setAttribute("listProd", listProd);
        }

        if (plId != null) {
            listProd = pDao.getProductsByProdLine(plId);

            request.setAttribute("listProd", listProd);

        }

        if (cId != null) {
            listProd = pDao.getProductsByCateID(cId);

            request.setAttribute("listProd", listProd);

        }

        if (mId != null) {
            listProd = pDao.getProductsByMateID(Integer.parseInt(mId));

            request.setAttribute("listProd", listProd);

        }

        if (bId != null) {
            listProd = pDao.getProductsByBrandID(Integer.parseInt(bId));

            request.setAttribute("listProd", listProd);

        }

            request.getRequestDispatcher("jsp/ListProduct.jsp").forward(request, response);

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
