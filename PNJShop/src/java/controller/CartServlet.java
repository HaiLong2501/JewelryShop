/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CartDAO;
import dal.CategoryDAO;
import dal.MaterialDAO;
import dal.ProductDAO;
import dal.ProductLineDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import model.Cart;
import model.Category;
import model.Material;
import model.Product;
import model.ProductLine;

/**
 *
 * @author Hai Long
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

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

        CategoryDAO cDao = new CategoryDAO();

        CartDAO ctDao = new CartDAO();

        MaterialDAO mDao = new MaterialDAO();

        ProductLineDAO plDao = new ProductLineDAO();

        ProductDAO pDao = new ProductDAO();

        String service = request.getParameter("service");

        if (service == null) {
            service = "showCart";
        }

        String search = request.getParameter("search");

        if (search == null) {
            Vector<Category> listCate = cDao.getAllCategories("select * from tblCategories");

            Vector<Material> listMate = mDao.getAllMaterials("select * from tblMaterials");

            Vector<ProductLine> listProdLine = plDao.getAllProductLine("select * from tblProductLines");

            request.setAttribute("listCate", listCate);

            request.setAttribute("listMate", listMate);

            request.setAttribute("listProdLine", listProdLine);

            if (service.equals("addCart")) {

                String pId = request.getParameter("pId");

                Product p = pDao.searchProduct(pId);

                Cart cart = ctDao.getCart(pId);

                String redirectUrl = request.getParameter("redirectUrl");

                if (session.getAttribute(pId) == null) {
                    cart.setQuantity(1);

                    session.setAttribute(pId, cart);
                } else {
                    Cart oldCart = (Cart) session.getAttribute(pId);
                    if (oldCart.getQuantity() > p.getQuantity()) {
                        oldCart.setQuantity(p.getQuantity());
                    } else {
                        oldCart.setQuantity(oldCart.getQuantity() + 1);
                    }
                    request.setAttribute("quantity", p.getQuantity());
                    session.setAttribute(pId, oldCart);
                }
                response.sendRedirect(redirectUrl);
            }

            if (service.equals("showCart")) {
                Vector<Cart> vector = new Vector<>();
                Map<String, Integer> quantityStorage = new HashMap<>();
                Enumeration enu = session.getAttributeNames();
                while (enu.hasMoreElements()) {
                    String key = (String) enu.nextElement();
                    
                    System.out.println("key: " + key);

                    if (!key.equals("user")) {
                        Cart cart = (Cart) session.getAttribute(key);
                        quantityStorage.put(key, pDao.searchProduct(key).getQuantity());
                        vector.add(cart);
                    }

                }
                request.setAttribute("listQuantity", quantityStorage);
                request.setAttribute("data", vector);
                request.getRequestDispatcher("jsp/Cart.jsp").forward(request, response);
            }

            if (service.equals("removeAll")) {
                Enumeration enu = session.getAttributeNames();
                while (enu.hasMoreElements()) {
                    String key = (String) enu.nextElement();
                    if (!key.equals("user")) {
                        session.removeAttribute(key);
                    }
                }

                response.sendRedirect("cart");
            }

            if (service.equals("removeItem")) {
                String pId = request.getParameter("pId");
                session.removeAttribute(pId);
                response.sendRedirect("cart");
            }

        } else {
            Vector<Product> listProd = pDao.getAllProduct("select * from tblProducts where productName like N'%" + search + "%'");

            request.setAttribute("listProd", listProd);

            request.getRequestDispatcher("jsp/ListProduct.jsp").forward(request, response);
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
