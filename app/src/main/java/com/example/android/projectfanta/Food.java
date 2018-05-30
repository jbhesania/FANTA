package com.example.android.projectfanta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Food implements Serializable{
    public static final ArrayList<String> NUTRIENTS = new ArrayList<String>(Arrays.asList("Calories",
            "Total Fat", "Saturated Fat", "Unsaturated Fat", "Polyunsaturated Fat",
            "Monounsaturated Fat", "Trans Fat", "Cholesterol", "Sodium", "Total Carbohydrate",
            "Dietary Fiber", "Soluble Fiber", "Insoluble Fiber", "Total Sugars", "Added Sugars",
            "Protein"));

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
            case "calories":
                nutrients.put("Calories", val);
                return true;
            case "total fat":
            case "tot. fat":
            case "fat":
                nutrients.put("Total Fat", val);
                return true;
            case "saturated fat":
            case "sat. fat":
                nutrients.put("Saturated Fat", val);
                return true;
            case "unsaturated fat":
            case "unsat. fat":
                nutrients.put("Unsaturated Fat", val);
                return true;
            case "polyunsaturated fat":
            case "polyunsat. fat":
                nutrients.put("Polyunsaturated Fat", val);
                return true;
            case "trans fat":
                nutrients.put("Trans Fat", val);
                return true;
            case "cholesterol":
            case "cholest.":
                nutrients.put("Cholesterol", val);
                return true;
            case "Sodium":
            case "sodium":
                nutrients.put("Sodium", val);
                return true;
            case "total carbohydrates":
            case "total carbs":
            case "total carb.":
                nutrients.put("Total Carbohydrate", val);
                return true;
            case "dietary fiber":
            case "fiber":
                nutrients.put("Dietary Fiber",val);
                return true;
            case "sugars":
            case "tot. sugars":
            case "total sugars":
            case "sugar":
                nutrients.put("Total Sugars", val);
                return true;
            case "added sugars":
            case "incl. added sugars":
            case "includes added sugars":
                nutrients.put("Added Sugars", val);
                return true;
            case "protein":
                nutrients.put("Protein", val);
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
