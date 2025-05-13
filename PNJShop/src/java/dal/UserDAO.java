/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.Vector;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hai Long
 */
public class UserDAO extends DBContext {

    private static final String LOGIN = "SELECT * FROM [dbo].[tblUsers]\n"
            + " WHERE userName = ? AND password = ?";

    public boolean checkLogin(String userName, String password) {
        boolean check = false;

        try {
            PreparedStatement ptm = connection.prepareStatement(LOGIN);

            ptm.setString(1, userName);
            ptm.setString(2, password);

            ResultSet rs = ptm.executeQuery();

            if (rs.next()) {
                check = true;
            }

        } catch (SQLException ex) {
            ex.getStackTrace();
        }

        return check;
    }

    public Vector<User> getAllUsers(String sql) {
        Vector<User> listUser = new Vector<>();

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ResultSet rs = ptm.executeQuery();

            while (rs.next()) {
                User u = new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getString(5), rs.getString(6),
                        rs.getBoolean(7),
                        rs.getString(8), rs.getString(9));

                listUser.add(u);
            }
        } catch (SQLException ex) {
            ex.getStackTrace();
        }

        return listUser;

    }

    public void insertUser(User u) {
        String sql = "INSERT INTO [dbo].[tblUsers]\n"
                + "           ([fullName]\n"
                + "           ,[password]\n"
                + "           ,[roleID]\n"
                + "           ,[address]\n"
                + "           ,[email]\n"
                + "           ,[activate]\n"
                + "           ,[UserName]\n"
                + "           ,[gender])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setString(1, u.getName());
            System.out.println("FullName: " + u.getName());
            ptm.setString(2, u.getPassword());
            System.out.println("Password: " + u.getPassword());
            ptm.setInt(3, u.getRoleID());
            System.out.println("RoleID: " + u.getRoleID());
            ptm.setString(4, u.getAddress());
            System.out.println("Address: " + u.getAddress());
            ptm.setString(5, u.getEmail());
            System.out.println("Email: " + u.getEmail());
            ptm.setBoolean(6, u.getActivate());
            System.out.println("Active: " + u.getActivate());
            ptm.setString(7, u.getUserName());
            System.out.println("UserName: " + u.getUserName());
            ptm.setString(8, u.getGender());
            System.out.println("Gender: " + u.getGender());
            
//            int rowsAffected = ptm.executeUpdate();
//        return rowsAffected > 0;
            

            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        return false;
    }

    public void registerUser(User u) {
        String sql = "INSERT INTO [dbo].[tblUsers]\n"
                + "           ([fullName]\n"
                + "           ,[password]\n"
                + "           ,[roleID]\n"
                + "           ,[email]\n"
                + "           ,[activate]\n"
                + "           ,[UserName]\n"
                + "            ,[gender])"
                + "     VALUES\n"
                + "           (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setString(1, u.getName());
            ptm.setString(2, u.getPassword());
            ptm.setInt(3, u.getRoleID());
            ptm.setString(4, u.getEmail());
            ptm.setBoolean(5, u.getActivate());
            ptm.setString(6, u.getUserName());
            ptm.setString(7, u.getGender());

            ptm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User searchUser(String userName) {

        String sql = "select * from tblUsers where UserName = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setString(1, userName);

            ResultSet rs = ptm.executeQuery();

            while (rs.next()) {
                User u = new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getString(5), rs.getString(6),
                        rs.getBoolean(7),
                        rs.getString(8), rs.getString(9));

                return u;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return null;
    }

//    public User checkPhone(String phone) {
//        String sql = "select * from tblUsers where phone = ?";
//
//        try {
//            PreparedStatement ptm = connection.prepareStatement(sql);
//
//            ptm.setString(1, phone);
//
//            ResultSet rs = ptm.executeQuery();
//
//            if (rs.next()) {
//                User u = new User(rs.getInt(1), rs.getString(2),
//                        rs.getString(3), rs.getInt(4),
//                        rs.getString(5), rs.getString(6),
//                        rs.getString(7),
//                        rs.getBoolean(8),
//                        rs.getString(9), rs.getString(10));
//
//                return u;
//            }
//        } catch (SQLException e) {
//            e.getStackTrace();
//        }
//
//        return null;
//    }

    public User checkUserName(String userName) {
        String sql = "select * from tblUsers where UserName = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setString(1, userName);

            ResultSet rs = ptm.executeQuery();

            if (rs.next()) {
                User u = new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getString(5), rs.getString(6),
                        rs.getBoolean(7),
                        rs.getString(8), rs.getString(9));

                return u;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return null;
    }

    public User checkEmail(String email) {
        String sql = "select * from tblUsers where email = ?";

        try {
            PreparedStatement ptm = connection.prepareStatement(sql);

            ptm.setString(1, email);

            ResultSet rs = ptm.executeQuery();

            if (rs.next()) {
                User u = new User(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4),
                        rs.getString(5), rs.getString(6),
                        rs.getBoolean(7),
                        rs.getString(8), rs.getString(9));

                return u;
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return null;
    }

    public void updateUser(User u) {
        String sql = "UPDATE [dbo].[tblUsers]\n"
                + "   SET "
                + "      [fullName] = ?\n"
                + "      ,[password] = ?\n"
                + "      ,[roleID] = ?\n"
                + "      ,[address] = ?\n"
                + "      ,[phone] = ?\n"
                + "      ,[email] = ?\n"
                + "      ,[activate] = ?\n"
                + " WHERE userID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setString(1, u.getName());
            pt.setString(2, u.getPassword());
            pt.setInt(3, u.getRoleID());
            pt.setString(4, u.getAddress());
            pt.setString(5, u.getPhone());
            pt.setString(6, u.getEmail());
            pt.setBoolean(7, u.getActivate());
            pt.setInt(8, u.getUserID());

            pt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void deleteUser(User u) {
        String sql = "delete from tblUsers where userID = ?";

        try {
            PreparedStatement pt = connection.prepareStatement(sql);

            pt.setInt(1, u.getUserID());

            pt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public static void main(String[] args) {
        String sql = "select * from tblUsers";

        UserDAO uDAO = new UserDAO();

//        User u = new User("bcd", "123", 2, "b@example.com", true, "b");
//        
//        uDAO.registerUser(u);
        System.out.println(uDAO.checkEmail("a@example.com"));

//        User u = new User("U006", "Đào Hải L", "password6", 1, "Vĩnh Phúc", 
//                          "0987654321", "l@example.com", true);
//        
//        System.out.println("After insert: ");
//        
//        uDAO.insertUser(u);
//        
//        list = uDAO.getAllUsers(sql);
//        
//        for (User user : list) {
//            System.out.println(user);
//        }
//        String id = "U006";
//
//        User u = uDAO.searchUser(id);
//
//        System.out.println("After find user with id: " + id);
//
//        if (u == null) {
//            System.out.println("not found");
//        } else {
//            System.out.println(u);
//        }
//            String id = "U006";
//            User u = uDAO.searchUser(id);
//            
//            System.out.println("After find user with id = " + id + " to update");
//            
//            if(u == null) System.out.println("not found");
//            else{
//                u.setFullName("DHL");
//                u.setPassword("pass6");
//                u.setRoleID(2);
//                u.setAddress("Hà Nội");
//                u.setPhone("0123456789");
//                u.setEmail("l@gmail.com");
//                u.setActivate(false);
//                
//                uDAO.updateUser(u);
//                
//                System.out.println("After update:");
//                
//                list = uDAO.getAllUsers(sql);
//                
//                for (User user : list) {
//                    System.out.println(user);
//                }
//            }
//        String id = "U006";
//
//        User u = uDAO.searchUser(id);
//
//        System.out.println("After find user with " + id + " to delete");
//
//        if (u == null) {
//            System.out.println("not found");
//        } else {
//            uDAO.deleteUser(u);
//            list = uDAO.getAllUsers(sql);
//
//            for (User user : list) {
//                System.out.println(user);
//            }
//        }
    }
}
