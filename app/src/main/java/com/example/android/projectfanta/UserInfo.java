package com.example.android.projectfanta;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfo extends User {

    private int weight;
    private int height;
    private int age;
    private int calories;
    private int carbs;
    private int protein;
    private int sugar;
    private int sodium;
    private int fiber;
    private int potassium;
    private int cholesterol;
    private int fat;


    public UserInfo(){
        super();
    }

    public UserInfo(String id, String uname, int w, int h, int a) {
        super(id, uname);
        weight = w;
        height = h;
        age = a;
        // we should add the logic for set nutrional values to defulats resetNutrionalValues
    }

    public UserInfo( UserInfo copy) {
        this(copy.getId(), copy.getUserName(), copy.getWeight(), copy.getHeight(), copy.getAge());
    }

    /**
     *
     * @return
     */
    public void resetNutrionalValues(){
        // perform the reset here
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public int getCalories() {
        return calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getFat() {
        return fat;
    }

    public int getFiber() {
        return fiber;
    }

    public int getSodium() {
        return sodium;
    }

    public int getPotassium() {
        return potassium;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public int getProtein() {
        return protein;
    }

    public int getSugar() {
        return sugar;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


}
