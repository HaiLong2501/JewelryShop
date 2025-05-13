/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.Vector;
import model.ProductDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Hai Long
 */
public class ProductDetailDAO extends DBContext{
    public Vector<ProductDetail> getProdDetail(String sql){
        Vector<ProductDetail> list = new Vector<>();
        
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            
            ResultSet rs = ptm.executeQuery();
            
            while (rs.next()) {                
                ProductDetail pd = new ProductDetail(
                                                     rs.getString(1), 
                                                     rs.getDouble(2), 
                                                     rs.getString(3), 
                                                     rs.getString(4), 
                                                     rs.getString(5), 
                                                     rs.getInt(6));
                
                list.add(pd);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public ProductDetail getDetailById(String productID){
        String sql = "select * from tblProductDetails where productID = ?";
        
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            
            ptm.setString(1, productID);
            
            ResultSet rs = ptm.executeQuery();
            
            if(rs.next()){
                ProductDetail pd = new ProductDetail(
                                                     rs.getString(1), 
                                                     rs.getDouble(2), 
                                                     rs.getString(3), 
                                                     rs.getString(4), 
                                                     rs.getString(5), 
                                                     rs.getInt(6));
                
                return pd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        
    }
}
