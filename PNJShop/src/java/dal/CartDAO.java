/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Cart;

/**
 *
 * @author Hai Long
 */
public class CartDAO extends DBContext{
    public Cart getCart(String productID) {
        Cart cart = null;
        String sql = "SELECT productID,productName,imageURL,price,discount,quantity\n"
                + "FROM tblProducts\n"
                + "WHERE productID=?";
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            ptm.setString(1, productID);
            ResultSet rs = ptm.executeQuery();
            if(rs.next()){
                 cart = new Cart(rs.getString(1), 
                    rs.getString(2), 
                    rs.getString(3),
                    rs.getDouble(4),
                    rs.getDouble(5),
                    0);
            }
           
        } catch (SQLException ex) {
           ex.getStackTrace();
        }
        return cart;
    }
}
