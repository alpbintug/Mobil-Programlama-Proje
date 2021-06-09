package com.example.a17011066_alp_bintug_uzun_project;

import java.util.List;

public class Combine {
    //region VARIABLES
    private List<Cloth> clothes;
    private String combineName;
    //endregion
    //region CONSTRUCTORS & GETTERS & SETTERS
    public Combine(List<Cloth> clothes, String combineName) {
        this.clothes = clothes;
        this.combineName = combineName;
    }

    public Combine() {
    }

    public List<Cloth> getClothes() {
        return clothes;
    }

    public void setClothes(List<Cloth> clothes) {
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
