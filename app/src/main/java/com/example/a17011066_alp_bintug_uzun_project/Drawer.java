package com.example.a17011066_alp_bintug_uzun_project;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Drawer {
    //region VARIABLES
    private int clothType;
    private String tag;
    private int ID;
    //endregion
    //region CONTRUCTORS & GETTERS & SETTERS

    public Drawer( int clothType,String tag, int ID){
        this.clothType = clothType;
        this.tag = tag;
        this.ID = ID;
    }

    public Drawer() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public int getClothType() {
        return clothType;
    }

    public void setClothType(int clothType) {
        this.clothType = clothType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    //endregion

}
