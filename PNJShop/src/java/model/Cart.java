/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hai Long
 */
public class Cart {
    private String productID, productName, image;
    private double price, discount;
    private int quantity;

    public Cart() {
    }

    public Cart(String productID, String productName, String image, double price, double discount, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{" + "productID=" + productID + ", productName=" + productName + ", image=" + image + ", price=" + price + ", discount=" + discount + ", quantity=" + quantity + '}';
    }
    
    
    
}
