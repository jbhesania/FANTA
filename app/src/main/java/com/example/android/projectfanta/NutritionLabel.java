package com.example.android.projectfanta;

/* A nutrition label refers to a specific named food */
public class NutritionLabel {
    String name;
    float calories;
    float totalFat;
    float sodium;
    float totalCarb;
    float fiber;
    float sugar;
    float protein;

    public void setName(String name) {
        this.name = name;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public void setTotalFat(float totalFat) {
        this.totalFat = totalFat;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }

    public void setTotalCarb(float totalCarb) {
        this.totalCarb = totalCarb;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }
}
