package com.example.android.projectfanta;

/**
 * Created by User on 5/17/2018.
 */

public class FreshFood {

    private String freshFoodName;
    private int Thumbnail,caloriesInt;
    private String proteinInt, carbsInt, sugarInt, fatInt, fiberInt;

    public FreshFood() {

    }

    public FreshFood(String freshFoodName, int thumbnail, String proteinInt, String carbsInt, String sugarInt, String fatInt, int caloriesInt, String fiberInt) {
        this.freshFoodName = freshFoodName;
        Thumbnail = thumbnail;
        this.proteinInt = proteinInt;
        this.carbsInt = carbsInt;
        this.sugarInt = sugarInt;
        this.fatInt = fatInt;
        this.caloriesInt = caloriesInt;
        this.fiberInt = fiberInt;
    }

    public String getFreshFoodName() {
        return freshFoodName;
    }

    public void setFreshFoodName(String freshFoodName) {
        this.freshFoodName = freshFoodName;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }

    public int getCaloriesInt() {
        return caloriesInt;
    }

    public void setCaloriesInt(int caloriesInt) {
        this.caloriesInt = caloriesInt;
    }

    public String getProteinInt() {
        return proteinInt;
    }

    public void setProteinInt(String proteinInt) {
        this.proteinInt = proteinInt;
    }

    public String getCarbsInt() {
        return carbsInt;
    }

    public void setCarbsInt(String carbsInt) {
        this.carbsInt = carbsInt;
    }

    public String getSugarInt() {
        return sugarInt;
    }

    public void setSugarInt(String sugarInt) {
        this.sugarInt = sugarInt;
    }

    public String getFatInt() {
        return fatInt;
    }

    public void setFatInt(String fatInt) {
        this.fatInt = fatInt;
    }

    public String getFiberInt() {
        return fiberInt;
    }

    public void setFiberInt(String fiberInt) {
        this.fiberInt = fiberInt;
    }
}
