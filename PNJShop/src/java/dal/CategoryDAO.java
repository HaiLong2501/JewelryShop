/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.Vector;
import model.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hai Long
 */
public class CategoryDAO extends DBContext {

    public Vector<Category> getAllCategories(String sql) {
        Vector<Category> listCate = new Vector<>();

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ResultSet rs = ptm.executeQuery();

            while (rs.next()) {
                Category c = new Category(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));

                listCate.add(c);
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

        return listCate;
    }

    public void insertCategory(Category c) {
        String sql = "insert into tblCategories values(?, ?, ?, ?)";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setString(1, c.getCategoryID());
            pt.setString(2, c.getCategoryName());
            pt.setString(3, c.getDescribe());
            pt.setString(4, c.getImage());

            pt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public Category searchCategory(String id) {
        String sql = "select * from tblCategories where categoryID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setString(1, id);

            ResultSet rs = pt.executeQuery();

            while (rs.next()) {
                Category c = new Category(rs.getString(1),
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

    public void updateCategory(Category c) {
        String sql = "UPDATE [dbo].[tblCategories]\n"
                + "   SET [categoryName] = ?\n"
                + "      ,[description] = ?\n"
                + "      ,[image] = ?\n"
                + " WHERE categoryID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setString(1, c.getCategoryName());
            pt.setString(2, c.getDescribe());
            pt.setString(3, c.getImage());
            pt.setString(4, c.getCategoryID());

            pt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }

    }

    public void deleteCategory(Category c) {
        String sql = "delete from tblCategories where categoryID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setString(1, c.getCategoryID());

            pt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public static void main(String[] args) {
        String sql = "select * from tblCategories";

        CategoryDAO cDAO = new CategoryDAO();

        Vector<Category> list = cDAO.getAllCategories(sql);

        for (Category category : list) {
            System.out.println(category);
        }

//        Category c = new Category("C006", "Trang sức", "Đẹp tuyệt cà là vời");
//        
//        cDAO.insertCategory(c);
//        
//        System.out.println("After insert Categories with id = " + c.getCategoryID());
//        
//        list = cDAO.getAllCategories(sql);
//        
//        for (Category category : list) {
//            System.out.println(category);
//        }
//        String id = "C006";
//        
//        Category c = cDAO.searchCategory(id);
//        
//        System.out.println("After search category with id = " + id);
//        
//        if(c == null) System.out.println("not found");
//        else{
//            System.out.println(c);
//        }
//        String id = "C006";
//        
//        Category c = cDAO.searchCategory(id);
//        
//        System.out.println("After search category with id = " + id);
//        
//        if(c == null) System.out.println("not found");
//        else{
//            c.setCategoryName("Mỹ phẩm");
//            c.setDescribe("Hàng xịn chất lượng cao nha");
//            cDAO.updateCategory(c);
//            System.out.println("After update: ");
//            list = cDAO.getAllCategories(sql);
//            for (Category category : list) {
//                System.out.println(category);
//            }
//        }
//        String id = "C006";
//        
//        Category c = cDAO.searchCategory(id);
//        
//        System.out.println("After search category with id = " + id);
//        
//        if(c == null)System.out.println("not found");
//        else{
//            cDAO.deleteCategory(c);
//            System.out.println("After delete: ");
//            list = cDAO.getAllCategories(sql);
//            for (Category category : list) {
//                System.out.println(category);
//            }
//        }
    }
}
