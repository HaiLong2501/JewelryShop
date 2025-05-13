/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hai Long
 */
public class ProductLine {
    private String productLineID, productLineName, describe, image;

    public ProductLine() {
    }

    public ProductLine(String productLineID, String productLineName, String describe, String image) {
        this.productLineID = productLineID;
        this.productLineName = productLineName;
        this.describe = describe;
        this.image = image;
    }

    public String getProductLineID() {
        return productLineID;
    }

    public void setProductLineID(String productLineID) {
        this.productLineID = productLineID;
    }

    public String getProductLineName() {
        return productLineName;
    }

    public void setProductLineName(String productLineName) {
        this.productLineName = productLineName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
    
}
