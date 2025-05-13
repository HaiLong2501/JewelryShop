/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.Vector;
import model.OrderDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hai Long
 */
public class OrderDetailDAO extends DBContext {

    public Vector<OrderDetail> getAllDetails(String sql) {
        Vector<OrderDetail> listDetails = new Vector<>();

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ResultSet rs = ptm.executeQuery();

            while (rs.next()) {
                OrderDetail od = new OrderDetail(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7));

                listDetails.add(od);
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

        return listDetails;
    }

    public void insertOrderDetail(OrderDetail od) {
        String sql = "INSERT INTO [dbo].[tblOrderDetails]\n"
                + "           ([quantity]\n"
                + "           ,[orderID]\n"
                + "           ,[productID]\n"
                + "           ,[totalPrice]\n"
                + "           ,[price]\n"
                + "           ,[discount])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?, ?)";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setInt(1, od.getQuantity());
            pt.setInt(2, od.getOrderID());
            pt.setString(3, od.getProductID());
            pt.setDouble(4, od.getTotalPrice());
            pt.setDouble(5, od.getPrice());
            pt.setDouble(6, od.getDiscount());

            pt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public Vector<OrderDetail> getOrderDetailsByOrderID(int orderID) {
        Vector<OrderDetail> list = new Vector<>();
        String sql = "SELECT * FROM tblOrderDetails WHERE orderID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setInt(1, orderID);
            ResultSet rs = pt.executeQuery();

            while (rs.next()) {
                OrderDetail od = new OrderDetail(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7));
                list.add(od);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Vector<OrderDetail> searchODByProductID(String id) {
        String sql = "select * from tblOrderDetails where productID = ?";
        
        Vector<OrderDetail> list = new Vector<>();

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setString(1, id);

            ResultSet rs = pt.executeQuery();

            while (rs.next()) {
                OrderDetail od = new OrderDetail(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7));

                list.add(od);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return list;
    }
    public OrderDetail searchOD(int id) {
        String sql = "select * from tblOrderDetails where detailID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setInt(1, id);

            ResultSet rs = pt.executeQuery();

            if (rs.next()) {
                OrderDetail od = new OrderDetail(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getDouble(6),
                        rs.getDouble(7));

                return od;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return null;
    }

    public void updateOrderDetail(OrderDetail od) {
        String sql = "UPDATE [dbo].[tblOrderDetails]\n"
                + "   SET [quantity] = ?\n"
                + "      ,[orderID] = ?\n"
                + "      ,[productID] = ?\n"
                + "      ,[totalPrice] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[discount] = ?\n"
                + " WHERE detailID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setInt(1, od.getQuantity());
            pt.setInt(2, od.getOrderID());
            pt.setString(3, od.getProductID());
            pt.setDouble(4, od.getTotalPrice());
            pt.setDouble(5, od.getPrice());
            pt.setDouble(6, od.getDiscount());
            pt.setInt(7, od.getDetailID());

            pt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void deleteOrderDetail(OrderDetail od) {
        String sql = "delete from tblOrderDetails where detailID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setInt(1, od.getDetailID());

            pt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public static void main(String[] args) {
        String sql = "select * from tblOrderDetails";

        OrderDetailDAO odDAO = new OrderDetailDAO();

        Vector<OrderDetail> list = odDAO.getAllDetails(sql);

        for (OrderDetail od : list) {
            System.out.println(od);
        }

//        OrderDetail od = new OrderDetail(500000, 20, 5, 5);
//        
//        odDAO.insertOrderDetail(od);
//        
//        System.out.println("After update: ");
//        list = odDAO.getAllDetails(sql);
//        for (OrderDetail orderDetail : list) {
//            System.out.println(orderDetail);
//        }
//        int id = 6;
//
//        OrderDetail od = odDAO.searchOD(id);
//
//        System.out.println("After search order detail with id = " + id);
//
//        if (od == null) {
//            System.out.println("not found");
//        } else {
//            od.setPrice(200000);
//            od.setQuantity(100);
//            od.setOrderID(1);
//            od.setProductID(1);
//            
//            odDAO.updateOrderDetail(od);
//            
//            System.out.println("After update");
//            list = odDAO.getAllDetails(sql);
//            
//            for (OrderDetail orderDetail : list) {
//                System.out.println(orderDetail);
//            }
        int id = 6;

        OrderDetail od = odDAO.searchOD(id);

        if (od == null) {
            System.out.println("After search order detail with id = " + id
                    + ": not found");
        } else {
            odDAO.deleteOrderDetail(od);
            System.out.println("After delete");
            list = odDAO.getAllDetails(sql);
            for (OrderDetail orderDetail : list) {
                System.out.println(orderDetail);
            }
        }
    }

}
