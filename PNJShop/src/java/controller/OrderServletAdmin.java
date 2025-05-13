/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.OrderDAO;
import dal.OrderDetailDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import model.Order;
import model.OrderDetail;
import model.Product;

/**
 *
 * @author Hai Long
 */
@WebServlet(name = "OrderServletAdmin", urlPatterns = {"/order"})
public class OrderServletAdmin extends HttpServlet {

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

        System.out.println("service: " + service);

        ProductDAO pDao = new ProductDAO();

        if (service == null) {
            service = "listOrder";
        }

        OrderDAO oDao = new OrderDAO();

        OrderDetailDAO odDao = new OrderDetailDAO();

        if (service.equals("listOrder")) {
            String submit = request.getParameter("submit");

            String key = request.getParameter("search");

            System.out.println("key: " + key);

            Vector<Order> listOrder = oDao.getAllOrders("select * from tblOrders");

            if (submit != null) {
                if (key != null && !key.trim().isEmpty()) {
                    System.out.println("11");
                    listOrder = oDao.getAllOrders("select * from tblOrders where userID = " + key);
                }

            }

            Map<Integer, Vector<OrderDetail>> listOD = new HashMap<>();

            for (Order order : listOrder) {
                Vector<OrderDetail> details = odDao.getOrderDetailsByOrderID(order.getOrderID());

                listOD.put(order.getOrderID(), details);
            }

            Vector<OrderDetail> details = odDao.getOrderDetailsByOrderID(5);

            request.setAttribute("listOD", listOD);

            request.setAttribute("listOrder", listOrder);

            request.getRequestDispatcher("admin/ListOrder.jsp").forward(request, response);
        }

        if (service.equals("updateOrder")) {
            System.out.println("is here");
            String userID = request.getParameter("userID");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            String cId = request.getParameter("cId");

            String dId[] = request.getParameterValues("dId");
            String productID[] = request.getParameterValues("productID");
            String quantityStr[] = request.getParameterValues("quantity");

            for (int i = 0; i < productID.length; i++) {

                Product p = pDao.searchProduct(productID[i]);

                OrderDetail detail = new OrderDetail(Integer.parseInt(dId[i]),
                        Integer.parseInt(quantityStr[i]),
                        Integer.parseInt(cId),
                        productID[i],
                        (p.getPrice() - p.getPrice() * p.getDiscount() / 100) * Integer.parseInt(quantityStr[i]),
                        p.getPrice(), p.getDiscount());

                odDao.updateOrderDetail(detail);
            }

            double total = 0;

            Vector<OrderDetail> list = odDao.getOrderDetailsByOrderID(Integer.parseInt(cId));

            for (OrderDetail od : list) {
                total += od.getTotalPrice();
            }

            Order o = oDao.searchOrder(Integer.parseInt(cId));

            System.out.println("total: " + total);

            o.setTotal(total);
            o.setShippingAddress(address);
            o.setPhone(phone);

            System.out.println("order update: " + o);

            oDao.updateOrder(o);

            response.sendRedirect("order");
        }
        
        if(service.equals("deleteOrder")){
            String oId = request.getParameter("oId");
            
            Order o = oDao.searchOrder(Integer.parseInt(oId));
            
            oDao.deleteOrder(o);
            
            response.sendRedirect("order");
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
