package com.example.android.projectfanta;

import java.util.HashMap;
import java.util.Map;

public class FoodDB {

    private String name;
    private HashMap<String, String> nutrients;
    /**
     * Creates a food, ready for nutrients to be added.
     * @param name The name of the food, used as an identifier
     */
    public FoodDB(String name) {
        this.name = name;
        nutrients = new HashMap<String, String>();
    }

    public FoodDB() { };

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
                nutrients.put("Calories", ""+val);
                return true;
            case "total fat":
            case "tot. fat":
                nutrients.put("Total Fat", ""+val);
                return true;
            case "saturated fat":
            case "sat. fat":
                nutrients.put("Saturated Fat", ""+val);
                return true;
            case "unsaturated fat":
            case "unsat. fat":
                nutrients.put("Unsaturated Fat", ""+val);
                return true;
            case "polyunsaturated fat":
            case "polyunsat. fat":
                nutrients.put("Polyunsaturated Fat", ""+val);
                return true;
            case "trans fat":
                nutrients.put("Trans Fat", ""+val);
                return true;
            case "cholesterol":
            case "cholest.":
                nutrients.put("Cholesterol", ""+val);
                return true;
            case "Sodium":
                nutrients.put("Sodium", ""+val);
                return true;
            case "total carbohydrates":
            case "total carbs":
            case "total carb.":
                nutrients.put("Total Carbohydrate", ""+val);
                return true;
            case "dietary fiber":
                nutrients.put("Dietary Fiber",""+val);
                return true;
            case "sugars":
            case "tot. sugars":
            case "total sugars":
                nutrients.put("Total Sugars", ""+val);
                return true;
            case "added sugars":
            case "incl. added sugars":
            case "includes added sugars":
                nutrients.put("Added Sugars", ""+val);
                return true;
            case "protein":
                nutrients.put("Protein", ""+val);
                return true;
            default:
                return false;
        }
    }

    public Double getNutrient(String key) {return new Double(nutrients.get(key));  }

    public Map<String, String> getNutrients() { return nutrients;  }

    public String getName() { return name; }

    public static Food convertToNormal(FoodDB db){
        Food food = new Food(db.name);
        for (String key : db.getNutrients().keySet()) {
            food.add(key, db.getNutrient(key));
        }
        return food;
    }
}
