/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Hai Long
 */
public class Order {
    private int orderID;
    private Date orderDate;
    private String paymentMethod;
    private double total;
    private int userID;
    private String shippingAddress;
    private String phone;

    public Order() {
    }

    public Order(int orderID, Date orderDate, String paymentMethod, double total, int userID, String shippingAddress, String phone) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.paymentMethod = paymentMethod;
        this.total = total;
        this.userID = userID;
        this.shippingAddress = shippingAddress;
        this.phone = phone;
    }

    public Order(Date orderDate, String paymentMethod, double total, int userID, String shippingAddress, String phone) {
        this.orderDate = orderDate;
        this.paymentMethod = paymentMethod;
        this.total = total;
        this.userID = userID;
        this.shippingAddress = shippingAddress;
        this.phone = phone;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", orderDate=" + orderDate + ", paymentMethod=" + paymentMethod + ", total=" + total + ", userID=" + userID + ", shippingAddress=" + shippingAddress + ", phone=" + phone + '}';
    }

    
    
}
