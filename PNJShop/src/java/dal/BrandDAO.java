/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.Brand;

/**
 *
 * @author Hai Long
 */
public class BrandDAO extends DBContext{
    public Vector<Brand> getAllBrands(String sql){
        Vector<Brand> list = new Vector<>();
        
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            
            ResultSet rs = ptm.executeQuery();
            
            while (rs.next()) {                
                Brand b = new Brand(rs.getInt(1), 
                                          rs.getString(2),
                                          rs.getString(3));
                
                list.add(b);
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        
        return list;
    }
    
    public Brand getBrandByBrandID(int brandID){
        String sql = "select * from tblBrands where brandID = ?";
        
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            
            ptm.setInt(1, brandID);
            
            ResultSet rs = ptm.executeQuery();
            
            if(rs.next()){
                Brand b = new Brand(rs.getInt(1), rs.getString(2), rs.getString(3));
                
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
