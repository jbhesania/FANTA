package com.example.android.projectfanta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Food implements Serializable{
    private String name;
    private HashMap<String, Double> nutrients;
    public static final ArrayList<String> NUTRIENTS = new ArrayList<String>(Arrays.asList("Calories",
            "Total Fat", "Saturated Fat", "Unsaturated Fat", "Polyunsaturated Fat",
            "Monounsaturated Fat", "Trans Fat", "Cholesterol", "Sodium", "Total Carbohydrate",
            "Dietary Fiber", "Soluble Fiber", "Insoluble Fiber", "Total Sugars", "Added Sugars",
            "Protein"));

    /**
     * Creates a food, ready for nutrients to be added.
     * @param name The name of the food, used as an identifier
     * @param info The Information object this food is being created for
     */
    public Food(String name, Information info) {
        if(!info.hasFood(name)) this.name = name;
        else this.name = Food.getValidName(name, info);

        nutrients = new HashMap<String, Double> ();
    }

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
        switch (key.toLowerCase().trim()) {
            case "calories":
                nutrients.put("Calories", new Double(val));
                return true;
            case "total fat":
            case "tot. fat":
                nutrients.put("Total Fat", new Double(val));
                return true;
            case "saturated fat":
            case "sat. fat":
                nutrients.put("Saturated Fat", new Double(val));
                return true;
            case "unsaturated fat":
            case "unsat. fat":
                nutrients.put("Unsaturated Fat", new Double(val));
                return true;
            case "polyunsaturated fat":
            case "polyunsat. fat":
                nutrients.put("Polyunsaturated Fat", new Double(val));
                return true;
            case "trans fat":
                nutrients.put("Trans Fat", new Double(val));
                return true;
            case "cholesterol":
            case "cholest.":
                nutrients.put("Cholesterol", new Double(val));
                return true;
            case "Sodium":
                nutrients.put("Sodium", new Double(val));
                return true;
            case "total carbohydrates":
            case "total carbs":
            case "total carb.":
                nutrients.put("Total Carbohydrate", new Double(val));
                return true;
            case "dietary fiber":
                nutrients.put("Dietary Fiber", new Double(val));
                return true;
            case "sugars":
            case "tot. sugars":
            case "total sugars":
                nutrients.put("Total Sugars", new Double(val));
                return true;
            case "added sugars":
            case "incl. added sugars":
            case "includes added sugars":
                nutrients.put("Added Sugars", new Double(val));
                return true;
            case "protein":
                nutrients.put("Protein", new Double(val));
                return true;
            default:
                return false;
        }
    }

    public Double getNutrient(String key) {return nutrients.get(key);  }

    public String getName() { return name; }
}
