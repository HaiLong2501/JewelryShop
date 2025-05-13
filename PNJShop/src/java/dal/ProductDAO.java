/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.util.Vector;
import model.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.OrderDetail;

/**
 *
 * @author Hai Long
 */
public class ProductDAO extends DBContext {

    public Vector<Product> getAllProduct(String sql) {
        Vector<Product> listProduct = new Vector<>();
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9), rs.getInt(10));

                listProduct.add(p);
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return listProduct;
    }

    public Product ProductFromDetail(String pId) {
        Product p = null;

        String sql = "SELECT productID,productName,imageURL,price,discount,quantity\n"
                + "FROM tblProducts\n"
                + "WHERE productID=?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setString(1, pId);

            ResultSet rs = ptm.executeQuery();

            if (rs.next()) {
                p = new Product(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getDouble(5), 1);

                return p;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return p;
    }

    public int insertProduct(Product p) {
        String sql = "insert into tblProducts values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int n = 0;

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setString(1, p.getProductID());
            ptm.setString(2, p.getProductName());
            ptm.setDouble(3, p.getPrice());
            ptm.setDouble(4, p.getDiscount());
            ptm.setString(5, p.getCategoryID());
            ptm.setString(6, p.getProductLineID());
            ptm.setInt(7, p.getMaterialID());
            ptm.setString(8, p.getImage());
            ptm.setInt(9, p.getStatus());
            ptm.setInt(10, p.getQuantity());

            n = ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        return n;
    }

    public Product searchProduct(String productID) {
        String sql = "select * from tblProducts where productID = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, productID);

            ResultSet rs = ptm.executeQuery();

            if (rs.next()) {
                Product p = new Product(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9), rs.getInt(10));

                return p;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return null;
    }

    public void updateProduct(Product p) {
        String sql = "UPDATE [dbo].[tblProducts]\n"
                + "   SET [productName] = ?\n"
                + "      ,[price] = ?\n"
                + "      ,[discount] = ?\n"
                + "      ,[status] = ?\n"
                + "      ,[quantity] = ?\n"
                + " WHERE productID = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setString(1, p.getProductName());
            ptm.setDouble(2, p.getPrice());
            ptm.setDouble(3, p.getDiscount());
            ptm.setInt(4, p.getStatus());
            ptm.setInt(5, p.getQuantity());
            ptm.setString(6, p.getProductID());

            ptm.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void changeStatus(String productId, int status) {
        String sql = "update tblProducts set status = ? where productId = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setInt(1, status);

            ptm.setString(2, productId);

            ptm.executeUpdate();
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
    }

    public int deleteProduct(String productID) {
        int n = 0;

        OrderDetailDAO odDAO = new OrderDetailDAO();

        String sql = "delete from tblProducts where productID = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setString(1, productID);

            Vector<OrderDetail> od = odDAO.searchODByProductID(productID);

            if (!od.isEmpty()) {
                changeStatus(productID, 0);
                return n;
            } else {
                n = ptm.executeUpdate();
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return n;
    }

    public Vector<Product> getProductsByProdLine(String productLineID) {
        Vector<Product> list = new Vector<>();

        String sql = "select * from tblProducts where productLineID = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setString(1, productLineID);

            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9), rs.getInt(10));

                list.add(p);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return list;
    }

    public Vector<Product> getProductsByCateID(String categoryID) {
        Vector<Product> list = new Vector<>();

        String sql = "select * from tblProducts where categoryID = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setString(1, categoryID);

            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9), rs.getInt(10));

                list.add(p);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return list;
    }

    public Vector<Product> getProductsByMateID(int materialID) {
        Vector<Product> list = new Vector<>();

        String sql = "select * from tblProducts where materialID = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setInt(1, materialID);

            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9), rs.getInt(10));

                list.add(p);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return list;
    }

    public Vector<Product> getProductsByBrandID(int brandID) {
        Vector<Product> list = new Vector<>();

        String sql = "select p.* from tblProducts p join tblProductDetails pd on p.productID = pd.productID \n"
                + "where pd.brandID = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setInt(1, brandID);

            ResultSet rs = ptm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getInt(9), rs.getInt(10));

                list.add(p);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {

        ProductDAO pDao = new ProductDAO();

        String id = "TS01";

        int brandID = 1;

        Product list = pDao.ProductFromDetail("GCDD00Y000110");

        System.out.println(list);
    }

}
