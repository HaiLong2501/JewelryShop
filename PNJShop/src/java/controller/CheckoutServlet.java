/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.CategoryDAO;
import dal.MaterialDAO;
import dal.OrderDAO;
import dal.OrderDetailDAO;
import dal.ProductDAO;
import dal.ProductLineDAO;
import dal.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Vector;
import java.sql.Date;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import model.Cart;
import model.Category;
import model.Material;
import model.Order;
import model.OrderDetail;
import model.Product;
import model.ProductLine;
import model.User;
import service.EmailService;

/**
 *
 * @author Hai Long
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/checkout"})
public class CheckoutServlet extends HttpServlet {

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

        EmailService sendMail = new EmailService();

        UserDAO uDao = new UserDAO();

        CategoryDAO cDao = new CategoryDAO();

        MaterialDAO mDao = new MaterialDAO();

        OrderDAO oDao = new OrderDAO();

        OrderDetailDAO odDao = new OrderDetailDAO();

        ProductLineDAO plDao = new ProductLineDAO();

        ProductDAO pDao = new ProductDAO();

        String service = request.getParameter("service");

        String search = request.getParameter("search");

        Vector<Category> listCate = cDao.getAllCategories("select * from tblCategories");

        Vector<Material> listMate = mDao.getAllMaterials("select * from tblMaterials");

        Vector<ProductLine> listProdLine = plDao.getAllProductLine("select * from tblProductLines");

        request.setAttribute("listCate", listCate);

        request.setAttribute("listMate", listMate);

        request.setAttribute("listProdLine", listProdLine);

        Product storage = null;

        Vector<Cart> listStorage = new Vector<>();

        String submit = request.getParameter("submit");

        if (submit == null) {
            if (service.equals("addFromCart")) {
                
                Vector<Cart> vector = new Vector<>();
                Enumeration enu = session.getAttributeNames();
                while (enu.hasMoreElements()) {
                    String key = (String) enu.nextElement();

                    if (!key.equals("user")) {
                        Cart cart = (Cart) session.getAttribute(key);
                        String quantityStr = request.getParameter("amount_" + cart.getProductID());
                        if(quantityStr != null){
                            int quantity =Integer.parseInt(quantityStr);
                            cart.setQuantity(quantity);
                        }
                        vector.add(cart);
                    }

                }
                listStorage = vector;
                request.setAttribute("data", vector);
                request.getRequestDispatcher("jsp/Checkout.jsp").forward(request, response);
            }

            if (service.equals("addFromDetail")) {

                String pId = request.getParameter("pId");

                Product p = pDao.ProductFromDetail(pId);

//                storage = p;
                request.setAttribute("product", p);

                request.getRequestDispatcher("jsp/Checkout.jsp").forward(request, response);
            }
        } else {
            String fullname = request.getParameter("customer_fullname");
            String phone = request.getParameter("customer_phone");
            String email = request.getParameter("customer_email");
            String province = request.getParameter("province");
            String district = request.getParameter("district");
            String ward = request.getParameter("ward");
            String village = request.getParameter("village");
            String pay = request.getParameter("pay_type");
            String customer_gender = request.getParameter("customer_gender");

            String error = null;

            boolean check = false;

            error = "Vui lòng điền đầy đủ thông tin!";

            if (customer_gender == null || customer_gender.trim().isEmpty()) {
                request.setAttribute("errorGender", error);
                check = true;
            }

            if (fullname == null || fullname.trim().isEmpty()
                    || phone == null || phone.trim().isEmpty()
                    || email == null || email.trim().isEmpty()) {
                request.setAttribute("errorInfo", error);
                check = true;
            }

            if (province == null || province.trim().isEmpty()
                    || district == null || district.trim().isEmpty()
                    || ward == null || ward.trim().isEmpty()
                    || village == null || village.trim().isEmpty()) {
                request.setAttribute("errorAddress", error);
                check = true;
            }

            if (pay == null || pay.trim().isEmpty()) {
                request.setAttribute("errorPay", error);
                check = true;
            }

            if (check) {

                Vector<Cart> vector = new Vector<>();
                User u = null;
                Enumeration enu = session.getAttributeNames();
                while (enu.hasMoreElements()) {
                    String key = (String) enu.nextElement();

                    if (!key.equals("user")) {
                        Cart cart = (Cart) session.getAttribute(key);
                        System.out.println("cart error: " + cart);
                        vector.add(cart);
                    }

                }
                if (!vector.isEmpty()) {
                    request.setAttribute("data", vector);

                } else {
                    storage = pDao.ProductFromDetail(request.getParameter("pId"));
                    request.setAttribute("product", storage);
                }

                request.setAttribute("error", error);

                request.getRequestDispatcher("jsp/Checkout.jsp").forward(request, response);
            } else {

                if (pay.equals("cod")) {
                    String[] pIds = request.getParameterValues("pId");
                    String[] amounts = request.getParameterValues("amount");
                    double total = Double.parseDouble(request.getParameter("total"));

                    User u = null;

                    Enumeration enu = session.getAttributeNames();
                    while (enu.hasMoreElements()) {
                        String key = (String) enu.nextElement();

                        if (key.equals("user")) {
                            u = (User) session.getAttribute(key);
                        }

                    }

                    System.out.println("email: " + email);

                    User uData = uDao.checkEmail(email);
                    System.out.println("User mua:" + uData);

                    Order o = new Order(Date.valueOf(LocalDate.now()),
                            pay,
                            total,
                            uData.getUserID(),
                            village + " - " + ward + " - " + district + " - " + province,
                            phone);

                    System.out.println("order: " + o);

                    int orderID = oDao.insertOrder(o);

                    for (int i = 0; i < pIds.length; i++) {
                        System.out.println("Sản phẩm thứ " + i + ": ");
                        System.out.println("pId: " + pIds[i]);
                        System.out.println("amount: " + amounts[i]);

                        int amount = Integer.parseInt(amounts[i]);

                        Product p = pDao.searchProduct(pIds[i]);

                        OrderDetail od = new OrderDetail(amount,
                                orderID,
                                pIds[i],
                                (p.getPrice() - p.getPrice() * p.getDiscount() / 100) * amount,
                                p.getPrice(),
                                p.getDiscount());

                        System.out.println("OrderDetail: " + od.toString());

                        odDao.insertOrderDetail(od);

                        System.out.println("Amount: " + amount);

                        System.out.println("quantity: " + p.getQuantity());

                        p.setQuantity(p.getQuantity() - amount);

                        pDao.updateProduct(p);

                        System.out.println("quantity after add: " + p.getQuantity());

                        session.removeAttribute(pIds[i]);

                    }

                    try {
                        sendMail.sendConfirmOrder(u, odDao.getOrderDetailsByOrderID(orderID));
                    } catch (MessagingException ex) {
                        ex.printStackTrace();

                    }

                    request.getRequestDispatcher("jsp/SuccessfulOrder.jsp").forward(request, response);

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
