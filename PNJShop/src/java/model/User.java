/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hai Long
 */
public class User {
//    usergoogle

    private String id;

    private boolean verified_email;
    
    private String name;

    private String given_name;

    private String family_name;

    private String picture;

//    user login by input
    private int userID;
    private String userName, password, address, phone, email;
    private int roleID;
    private boolean activate;
    private String gender;

    public User() {
    }
    
//    constructor login user
    public User(String id, String email, boolean verified_email, String name, String given_name, String family_name, String picture, 
            String password, String address) {
		this.id = id;
		this.email = email;
		this.verified_email = verified_email;
		this.name = name;
		this.given_name = given_name;
		this.family_name = family_name;
		this.picture = picture;
                this.password = password;
                this.address = address;
	}

    public User(int userID, String email) {
        this.userID = userID;
        this.email = email;
    }
    
    

    public User(int userID, String name, String password, int roleID, String address, String email, boolean activate, String userName, String gender) {
        this.userID = userID;
        this.userName = userName;
        this.name = name;
        this.password = password;
        this.address = address;
        this.email = email;
        this.roleID = roleID;
        this.activate = activate;
        this.gender = gender;
    }

    public User(String name, String password, int roleID, String email, boolean activate, String userName) {
        this.userName = userName;
        this.name = name;
        this.roleID = roleID;
        this.password = password;
        this.email = email;
        this.activate = activate;
    }

    public User(String name, String password, int roleID, String email, boolean activate, String userName, String gender) {
        this.userName = userName;
        this.name = name;
        this.roleID = roleID;
        this.password = password;
        this.email = email;
        this.activate = activate;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public boolean getActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }
    
    public String userGoogleToString() {
		return "UserGoogleDto{" + "id=" + id + ", email=" + email + ", verified_email=" + verified_email + ", name=" + name + ", given_name=" + given_name + ", family_name=" + family_name + ", picture=" + picture + '}';
	}
    
    

//    @Override
//    public String toString() {
//        return "User{" + "userID=" + userID + ", userName=" + userName + ", name=" + name + ", password=" + password + ", address=" + address + ", phone=" + phone + ", email=" + email + ", roleID=" + roleID + ", activate=" + activate + ", gender=" + gender + '}';
//    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", verified_email=" + verified_email + ", name=" + name + ", given_name=" + given_name + ", family_name=" + family_name + ", picture=" + picture + ", userID=" + userID + ", userName=" + userName + ", password=" + password + ", address=" + address + ", phone=" + phone + ", email=" + email + ", roleID=" + roleID + ", activate=" + activate + ", gender=" + gender + '}';
    }

}
