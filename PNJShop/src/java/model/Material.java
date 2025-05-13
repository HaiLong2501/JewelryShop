/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Hai Long
 */
public class Material {
    private int materialID;
    private String materialName;

    public Material() {
    }

    public Material(int materialIDl, String materialName) {
        this.materialID = materialIDl;
        this.materialName = materialName;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialIDl(int materialID) {
        this.materialID = materialID;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    
    
}
