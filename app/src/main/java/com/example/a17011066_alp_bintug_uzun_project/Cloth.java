package com.example.a17011066_alp_bintug_uzun_project;

import android.graphics.Color;

import java.util.Date;

public class Cloth {
    //region VARIABLES
    private int clothType;
    private Color color;
    private String pattern;
    private float price;
    private Date datePurchased;
    private String photoPath;
    //endregion
    //region CONSTRUCTER & GETTER & SETTER
    public Cloth(int clothType, Color color, String pattern, float price, Date datePurchased, String photoPath) {
        this.clothType = clothType;
        this.color = color;
        this.pattern = pattern;
        this.price = price;
        this.datePurchased = datePurchased;
        this.photoPath = photoPath;
    }

    public Cloth() {
    }

    public int getClothType() {
        return clothType;
    }

    public void setClothType(int clothType) {
        this.clothType = clothType;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(Date datePurchased) {
        this.datePurchased = datePurchased;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
    //endregion
}
