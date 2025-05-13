/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.Vector;
import model.Role;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hai Long
 */
public class RoleDAO extends DBContext{
    
    public Vector<Role> getAllRoles(String sql){
        Vector<Role> listRole = new Vector<>();
        
        try {
            PreparedStatement ptm = connection.prepareStatement(sql);
            
            ResultSet rs = ptm.executeQuery();
            
            while (rs.next()) {                
                Role r = new Role(rs.getInt(1),
                                  rs.getString(2));
                
                listRole.add(r);
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }
        
        return listRole;
    }
    
    public static void main(String[] args) {
        String sql = "select * from tblRoles";
        
        RoleDAO rDAO = new RoleDAO();
        
        Vector<Role> list = rDAO.getAllRoles(sql);
        
        for (Role role : list) {
            System.out.println(role);
        }
    }
}
