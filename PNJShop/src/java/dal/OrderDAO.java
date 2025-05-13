/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Statement;
import java.sql.Date;
import java.util.Vector;
import model.Order;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hai Long
 */
public class OrderDAO extends DBContext {

    public Vector<Order> getAllOrders(String sql) {
        Vector<Order> listOrder = new Vector<>();

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ResultSet rs = ptm.executeQuery();

            while (rs.next()) {
                Order o = new Order(rs.getInt(1),
                        rs.getDate(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7));

                listOrder.add(o);
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

        return listOrder;
    }

    public int insertOrder(Order o) {
        String sql = "INSERT INTO [dbo].[tblOrders]\n"
                + "           ([orderDate]\n"
                + "           ,[paymentMethod]\n"
                + "           ,[total]\n"
                + "           ,[userID]\n"
                + "           ,[shippingAddress]\n"
                + "           ,[phone])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?)";

        int orderID = -1;

        try {
            PreparedStatement ptm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ptm.setDate(1, o.getOrderDate());
            ptm.setString(2, o.getPaymentMethod());
            ptm.setDouble(3, o.getTotal());
            ptm.setInt(4, o.getUserID());
            ptm.setString(5, o.getShippingAddress());
            ptm.setString(6, o.getPhone());

            int affectedRows = ptm.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ptm.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderID = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.getStackTrace();
        }
        return orderID;
    }

    public Order searchOrder(int id) {
        String sql = "select * from tblOrders where orderID = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setInt(1, id);

            ResultSet rs = ptm.executeQuery();

            while (rs.next()) {
                Order o = new Order(rs.getInt(1),
                        rs.getDate(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getInt(5),
                        rs.getString(6),
                        rs.getString(7));
                return o;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return null;
    }

    public void updateOrder(Order o) {
        String sql = "UPDATE [dbo].[tblOrders]\n"
                + "   SET [orderDate] = ?\n"
                + "      ,[paymentMethod] = ?\n"
                + "      ,[total] = ?\n"
                + "      ,[userID] = ?\n"
                + "      ,[shippingAddress] = ?\n"
                + "      ,[phone] = ?\n"
                + " WHERE orderID = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setDate(1, o.getOrderDate());
            ptm.setString(2, o.getPaymentMethod());
            ptm.setDouble(3, o.getTotal());
            ptm.setInt(4, o.getUserID());
            ptm.setString(5, o.getShippingAddress());
            ptm.setString(6, o.getPhone());
            ptm.setInt(7, o.getOrderID());

            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(Order o) {
        String sql = "delete from tblOrders where orderID = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setInt(1, o.getOrderID());

            ptm.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public static void main(String[] args) {
        String sql = "select * from tblOrders";

        OrderDAO oDAO = new OrderDAO();

        Vector<Order> list = oDAO.getAllOrders(sql);

        Order o = oDAO.searchOrder(5);
        
        System.out.println("Order: " + o);
        
        o.setTotal(154159875);
        
        oDAO.updateOrder(o);

//        Order o = new Order(new Date(2025 - 1900, 1, 23), 200000, "U004");
//        
//        oDAO.insertOrder(o);
//        System.out.println("After search:");
//        list = oDAO.getAllOrders(sql);
//        for (Order order : list) {
//            System.out.println(order);
//        }
//        System.out.println("Search Order");
//        Order o = oDAO.searchOrder(7);
//
//        if (o == null) {
//            System.out.println("Order is not existed!");
//        } else {
//            System.out.println(o);
//        }
//        Order o = oDAO.searchOrder(7);
//
//        if (o == null) {
//            System.out.println("Order is not existed");
//        } else {
//            o.setTotal(220000);
//            o.setUserID("U002");
//            System.out.print("Order: ");
//            System.out.println(o);
//            oDAO.updateOrder(o);
//            System.out.println("After update:");
//            list = oDAO.getAllOrders(sql);
//            for (Order order : list) {
//                System.out.println(order);
//            }
//            
//        }
        

    }
}
