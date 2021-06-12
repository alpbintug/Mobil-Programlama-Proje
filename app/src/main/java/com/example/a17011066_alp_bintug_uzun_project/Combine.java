package com.example.a17011066_alp_bintug_uzun_project;

import java.util.ArrayList;

public class Combine {
    //region VARIABLES
    private String combineName;
    private int ID;
    //endregion
    //region CONSTRUCTORS & GETTERS & SETTERS
    public Combine(String combineName, int ID) {
        this.combineName = combineName;
        this.ID = ID;
    }

    public Combine() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCombineName() {
        return combineName;
    }

    public void setCombineName(String combineName) {
        this.combineName = combineName;
    }
    //endregion

}
