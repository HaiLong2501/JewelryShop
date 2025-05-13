/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import model.Material;

/**
 *
 * @author Hai Long
 */
public class MaterialDAO extends DBContext{
    public Vector<Material> getAllMaterials(String sql) {
        Vector<Material> listMaterial = new Vector<>();

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ResultSet rs = ptm.executeQuery();

            while (rs.next()) {
                Material m = new Material(rs.getInt(1), rs.getString(2));

                listMaterial.add(m);
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

        return listMaterial;
    }
}
