package com.example.a17011066_alp_bintug_uzun_project;

import java.util.List;

public class Drawer {
    //region VARIABLES
    private List<Cloth> clothes;
    private int clothType;
    //endregion
    //region CONTRUCTORS & GETTERS & SETTERS
    public Drawer(List<Cloth> clothes, int clothType) {
        this.clothes = clothes;
        this.clothType = clothType;
    }

    public Drawer() {
    }

    public List<Cloth> getClothes() {
        return clothes;
    }

    public void setClothes(List<Cloth> clothes) {
        this.clothes = clothes;
    }

    public int getClothType() {
        return clothType;
    }

    public void setClothType(int clothType) {
        this.clothType = clothType;
    }
    //endregion
    //region ADDITIONAL FUNCTIONS
    public boolean changeClothType(int clothType){
        if(clothType<5&&clothType>=0){
            this.clothType = clothType;
            clothes.clear();
            return true;
        }
        return false;
    }
    //endregion
}
