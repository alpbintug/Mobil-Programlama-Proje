package com.example.a17011066_alp_bintug_uzun_project;

import android.graphics.Color;

import java.util.Date;

public class Cloth {

    private int clothType;
    private Color color;
    private String pattern;
    private float price;
    private Date datePurchased;

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
}
