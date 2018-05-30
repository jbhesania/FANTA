package com.example.android.projectfanta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Food implements Serializable{

    public static final String CALORIES = "calories";
    public static final String FAT = "fat";
    public static final String PROTIEN = "protein";
    public static final String FIBER = "fiber";
    public static final String SUGAR = "sugar";
    public static final String SODIUM = "sodium";
    public static final String CHOLESTEROL = "cholesterol";
    public static final String CARBS = "carbs";
    public static final String POTASSIUM = "potassium";


    private String name;
    private HashMap<String, Double> nutrients;


    /**
     * Creates a food, ready for nutrients to be added.
     * @param name The name of the food, used as an identifier
     */
    public Food(String name) {
        this.name = name;
        nutrients = new HashMap<String, Double> ();
    }

    public Food() { };

    /**
     * Method to rename foods with already taken name.
     * @param name The name the user wanted
     * @param info The info object that the food belongs to
     * @return
     */
    public static String getValidName(String name, Information info) {
        while(info.hasFood(name)) {
            name = name.concat("I");
        }
        return name;
        // TODO : char lastChar = name.charAt(name.length()); if(Character.isDigit(lastChar))
    }

    /**
     * Adds the nutrient and its amount to the food.
     * @param key The name of the nutrient
     * @param val The amount of the nutrient in grams per serving
     * @return Whether or not the nutrient was sucessfully added. If false, this means the key was not recognized.
     */
    public boolean add(String key, double val) {
        if (nutrients == null) nutrients = new HashMap<String, Double>();
        switch (key.toLowerCase().trim()) {
            case Food.CALORIES:
                nutrients.put(Food.CALORIES, val);
                return true;
            case Food.FAT:
                nutrients.put(Food.FAT, val);
                return true;
            case Food.CHOLESTEROL:
                nutrients.put(Food.CHOLESTEROL, val);
                return true;
            case Food.SODIUM:
                nutrients.put(Food.SODIUM, val);
                return true;
            case Food.CARBS:
                nutrients.put(Food.CARBS, val);
                return true;
            case Food.FIBER:
                nutrients.put(Food.FIBER,val);
                return true;
            case Food.SUGAR:
                nutrients.put(Food.SUGAR, val);
                return true;
            case Food.PROTIEN:
                nutrients.put(Food.PROTIEN, val);
                return true;
            case Food.POTASSIUM:
                nutrients.put(Food.POTASSIUM, val);
                return true;
            default:
                return false;
        }
    }

    public Double getNutrient(String key) {return new Double(nutrients.get(key));  }

    public Map<String, Double> getNutrients() { return nutrients;  }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    //    public static FoodDB convertToDB(Food food){
//        FoodDB db = new FoodDB(food.name);
//        for (String key : food.getNutrients().keySet()) {
//            db.add(key, food.getNutrient(key));
//        }
//        return db;
//    }
}
