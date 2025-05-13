/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.ProductLine;

/**
 *
 * @author Hai Long
 */
public class ProductLineDAO extends DBContext{
    public Vector<ProductLine> getAllProductLine(String sql) {
        Vector<ProductLine> listProdLine = new Vector<>();

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ResultSet rs = ptm.executeQuery();

            while (rs.next()) {
                ProductLine pl = new ProductLine(rs.getString(1), rs.getString(2), 
                                                rs.getString(3), rs.getString(4));

                listProdLine.add(pl);
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

        return listProdLine;
    }
    
    public void insertProductLine(ProductLine c) {
        String sql = "insert into tblProductLines values(?, ?, ?, ?)";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setString(1, c.getProductLineID());
            pt.setString(2, c.getProductLineName());
            pt.setString(3, c.getDescribe());
            pt.setString(4, c.getImage());

            pt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
    
    public void deleteProductLine(ProductLine c) {
        String sql = "delete from tblProductLines where productLineID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setString(1, c.getProductLineID());

            pt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
    
    public ProductLine searchProductLine(String id) {
        String sql = "select * from tblProductLines where productLineID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setString(1, id);

            ResultSet rs = pt.executeQuery();

            while (rs.next()) {
                ProductLine c = new ProductLine(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));

                return c;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return null;
    }

    public void updateProductLine(ProductLine c) {
        String sql = "UPDATE [dbo].[tblProductLines]\n"
                + "   SET [productLineName] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[image] = ?\n"
                + " WHERE productLineID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setString(1, c.getProductLineName());
            pt.setString(2, c.getDescribe());
            pt.setString(3, c.getImage());
            pt.setString(4, c.getProductLineID());

            pt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }

    }
}
