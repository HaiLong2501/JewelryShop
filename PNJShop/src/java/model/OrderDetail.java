/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hai Long
 */
public class OrderDetail {
    private int detailID;
    private int quantity;
    private int orderID;
    private String productID;
    private double totalPrice, price, discount;

    public OrderDetail() {
    }

    public OrderDetail(int detailID, int quantity, int orderID, String productID, double totalPrice, double price, double discount) {
        this.detailID = detailID;
        this.quantity = quantity;
        this.orderID = orderID;
        this.productID = productID;
        this.totalPrice = totalPrice;
        this.price = price;
        this.discount = discount;
    }
    
    

    public OrderDetail(int quantity, int orderID, String productID, double totalPrice, double price, double discount) {
        this.detailID = detailID;
        this.quantity = quantity;
        this.orderID = orderID;
        this.productID = productID;
        this.totalPrice = totalPrice;
        this.price = price;
        this.discount = discount;
    }
    
    
    
    

    public OrderDetail(int detailID, int quantity, int orderID, String productID, double totalPrice, double price) {
        this.detailID = detailID;
        this.quantity = quantity;
        this.orderID = orderID;
        this.productID = productID;
        this.totalPrice = totalPrice;
        this.price = price;
    }

    public OrderDetail(int quantity, int orderID, String productID, double totalPrice, double price) {
        this.quantity = quantity;
        this.orderID = orderID;
        this.productID = productID;
        this.totalPrice = totalPrice;
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "detailID=" + detailID + ", quantity=" + quantity + ", orderID=" + orderID + ", productID=" + productID + ", totalPrice=" + totalPrice + ", price=" + price + '}';
    }

       
    
}
