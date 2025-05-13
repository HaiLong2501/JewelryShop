/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hai Long
 */
public class ProductDetail {
    private String productID;
    private double weight;
    private String mainStone, secondaryStone, gender;
    private int brandID;

    public ProductDetail() {
    }

    public ProductDetail(String productID, double weight, String mainStone, String secondaryStone, String gender, int brandID) {
        this.productID = productID;
        this.weight = weight;
        this.mainStone = mainStone;
        this.secondaryStone = secondaryStone;
        this.gender = gender;
        this.brandID = brandID;
    }

    public ProductDetail(double weight, String mainStone, String secondaryStone, String gender, int brandID) {
        this.weight = weight;
        this.mainStone = mainStone;
        this.secondaryStone = secondaryStone;
        this.gender = gender;
        this.brandID = brandID;
    }



    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getMainStone() {
        return mainStone;
    }

    public void setMainStone(String mainStone) {
        this.mainStone = mainStone;
    }

    public String getSecondaryStone() {
        return secondaryStone;
    }

    public void setSecondaryStone(String secondaryStone) {
        this.secondaryStone = secondaryStone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }
    
    
}
