package com.example.android.projectfanta;

/* A nutrition label refers to a specific named food */
public class NutritionLabel {
    String name = "";
    float calories = 0;
    float totalFat = 0;
    float sodium = 0;
    float totalCarb = 0;
    float fiber = 0;
    float sugar = 0;
    float protein = 0;
    float chol = 0;


    float potassium = 0;

    public NutritionLabel(String n) {
        this.name = n;
    }

    public void printDis() {
        System.out.println("Name: " + name);
        System.out.println("Calories " + calories);
        System.out.println("Fat: " + totalFat);
        System.out.println("Sodium: " + sodium);
        System.out.println("Carbs: " + totalCarb);
        System.out.println("Fiber: " + fiber);
        System.out.println("Sugar: " + sugar);
        System.out.println("Protein: " + protein);
        System.out.println("Cholesterol: " + chol);
        System.out.println("Potassium: " + potassium);




    }
    public void setPotassium(float potassium) { this.potassium = potassium; }
    public void setName(String name) {
        this.name = name;
    }
    public void setChol(float chol) { this.chol = chol; }

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
