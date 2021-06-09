package com.example.a17011066_alp_bintug_uzun_project;

import java.util.ArrayList;

public class Combine {
    //region VARIABLES
    private ArrayList<Cloth> clothes;
    private String combineName;
    //endregion
    //region CONSTRUCTORS & GETTERS & SETTERS
    public Combine(ArrayList<Cloth> clothes, String combineName) {
        this.clothes = clothes;
        this.combineName = combineName;
    }

    public Combine() {
    }

    public ArrayList<Cloth> getClothes() {
        return clothes;
    }

    public void setClothes(ArrayList<Cloth> clothes) {
        this.clothes = clothes;
    }

    public String getCombineName() {
        return combineName;
    }

    public void setCombineName(String combineName) {
        this.combineName = combineName;
    }
    //endregion

}
