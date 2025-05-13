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
public class Product {
    //fielđs
    private String productID;
    private String productName;
    private double price;
    private double discount;
    private String categoryID;
    private String productLineID, image;
    private int materialID;
    private int status, quantity;
    
    //constructor
    public Product() {
    }

    public Product(String productID, String productName, double price, double discount, int status, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.status = status;
        this.quantity = quantity;
    }
    
    
            
    public Product(String productID, String productName, String image, double price, double discount, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.quantity = quantity;
    }
public Product(String productID, String productName, double price, double discount, String categoryID, String productLineID, int materialID, String image, int status, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.categoryID = categoryID;
        this.productLineID = productLineID;
        this.materialID = materialID;
        this.image = image;
        this.status = status;
        this.quantity = quantity;
    }

    public Product(String productName, double price, double discount, String categoryID, String productLineID, int materialID, String image, int status, int quantity) {
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.categoryID = categoryID;
        this.productLineID = productLineID;
        this.materialID = materialID;
        this.image = image;
        this.status = status;
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getProductLineID() {
        return productLineID;
    }

    public void setProductLineID(String productLineID) {
        this.productLineID = productLineID;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    

    @Override
    public String toString() {
        return "Product{" + "productID=" + productID + ", productName=" + productName + ", price=" + price + ", discount=" + discount + ", categoryID=" + categoryID + ", productLineID=" + productLineID + ", image=" + image + ", materialID=" + materialID + ", status=" + status + '}';
    }

    
    
    

    
}
