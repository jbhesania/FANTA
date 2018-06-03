package com.example.android.projectfanta;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Information implements Serializable {

    public static Information information;

    private HashMap<String, Food> myFoods;
    private ArrayList<Intake> myIntakes;
    private UserInfo myInfo;
    // String here is the username
    DatabaseReference mData;

    public static final String FOOD_FILE = "myFoods";
    public static final String INTAKE_FILE = "myIntakes";
    public static final String USERINFO_FILE = "userInfo";

    /**
     * Create information object and populates it
     * @param user how the information object will be populated
     */
    public Information(UserInfo myInfo, User user) {
        this.myInfo = myInfo;
        Food food = new Food(" ");
        food.add("calories", 0);
        this.myFoods = new HashMap<String, Food>();
        this.myFoods.put(" ", food);
        this.myIntakes = new ArrayList<Intake>();
        this.myIntakes.add(new Intake("", 0, 0));
    }

    public Information() {};

    public UserInfo getInfo() { return myInfo; }
    public Food getFood(String name) { return myFoods.get(name); }
    public ArrayList<Intake> getMyIntakes() { return myIntakes; }
    public HashMap<String, Food> getMyFoods() { return myFoods; }

    public void setInfoToDB() {
        if (mData == null) mData = FirebaseDatabase.getInstance().getReference();
        mData.child(myInfo.getId()).child("info").setValue(myInfo);
    }

    public void setInfo(UserInfo myInfo) {
        this.myInfo = myInfo;
    }

    public void setFoods(HashMap<String, Food> myFoods) {
        this.myFoods = myFoods;
    }
    public void setIntakes(ArrayList<Intake> myIntakes) {
        this.myIntakes = myIntakes;
    }

    public boolean hasFood(String name)    {
        Set<String> keys = myFoods.keySet();
        for(String key: keys) {
            if(key.toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    static void read(String uid, final Callback call){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Information myInfo = dataSnapshot.getValue(Information.class);
                call.onComplete(myInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("sucks", "loadUid:onCancelled", databaseError.toException());
            }
        };
        FirebaseDatabase.getInstance().getReference().child(uid).addListenerForSingleValueEvent(postListener);
    }

    public boolean createInfoFromMemory(Context context) {
        try {

            FileInputStream foodFileIn = context.getApplicationContext().openFileInput(Information.FOOD_FILE);
            ObjectInputStream foodIn = new ObjectInputStream(foodFileIn);
            Information.information.setFoods((HashMap<String, Food>) foodIn.readObject());
            foodFileIn.close();
            foodIn.close();

            FileInputStream intakeFileIn = context.getApplicationContext().openFileInput(Information.INTAKE_FILE);
            ObjectInputStream intakeIn = new ObjectInputStream(intakeFileIn);
            Information.information.setIntakes( (ArrayList<Intake>) intakeIn.readObject());
            intakeFileIn.close();
            foodIn.close();

            FileInputStream userFileIn = context.getApplicationContext().openFileInput(Information.USERINFO_FILE);
            ObjectInputStream userIn = new ObjectInputStream(userFileIn);
            Information.information.setInfo((UserInfo) userIn.readObject());
            userFileIn.close();
            userIn.close();

            if(Information.information.getMyFoods() == null || Information.information.getMyIntakes() == null
                    || Information.information.getInfo() == null) {
                throw new NullPointerException("Did not instantaie properly from files/files didnt exist");
            }

            return true;
        } catch (Exception c) {
            c.printStackTrace();
            return false;
        }
    }

    public boolean writeInfoToMemory(Context context) {
        try {
            this.deleteFiles(context);

            FileOutputStream foodFileOut =
                    context.getApplicationContext().openFileOutput(Information.FOOD_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream foodOut = new ObjectOutputStream(foodFileOut);
            foodOut.writeObject(Information.information.getMyFoods());
            foodFileOut.close();
            foodOut.close();

            FileOutputStream intakeFileOut =
                    context.getApplicationContext().openFileOutput(Information.INTAKE_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream intakeOut = new ObjectOutputStream(intakeFileOut);
            intakeOut.writeObject(Information.information.getMyIntakes());
            intakeFileOut.close();
            intakeOut.close();

            FileOutputStream userFileOut =
                    context.getApplicationContext().openFileOutput(Information.USERINFO_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream userOut = new ObjectOutputStream(userFileOut);
            userOut.writeObject(Information.information.getInfo());
            userFileOut.close();
            userFileOut.close();

            return true;
        } catch (Exception c) {
            c.printStackTrace();
            return false;
        }
    }

    public void deleteFiles(Context context) {
        context.getApplicationContext().deleteFile(Information.INTAKE_FILE);
        context.getApplicationContext().deleteFile(Information.FOOD_FILE);
        context.getApplicationContext().deleteFile(Information.USERINFO_FILE);
    }

    public void addIntake(Context context, Intake intake){
        addIntakeToDB(intake);
        myIntakes.add(intake);
        addIntakeToMemory(context);
    }
    private void addIntakeToDB(Intake intake) {
        if (mData == null) mData = FirebaseDatabase.getInstance().getReference();
        mData.child(myInfo.getId()).child("myIntakes").child(myIntakes.size()+"").setValue(intake);
    }
    private void addIntakeToMemory(Context context) {
        context.deleteFile(Information.FOOD_FILE);
        try {
            FileOutputStream followersFileOut =
                    context.getApplicationContext().openFileOutput(Information.INTAKE_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream followersOut = new ObjectOutputStream(followersFileOut);
            followersOut.writeObject(Information.information.getMyIntakes());
            followersOut.close();
            followersFileOut.close();
        }
        catch(Exception e) {

        }
    }

    public void addFood(Context context, Food food) {
        addFoodToDB(food);
        myFoods.put(food.getName(),food);
        addFoodToMemory(context);
    }
    private void addFoodToDB(Food food) {
        if (mData == null) mData = FirebaseDatabase.getInstance().getReference();
        mData.child(myInfo.getId()).child("myFoods").child(food.getName()).setValue(food);
    }
    private void addFoodToMemory(Context context) {
        context.deleteFile(Information.FOOD_FILE);
        try {
            FileOutputStream foodFileOut =
                    context.getApplicationContext().openFileOutput(Information.FOOD_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream foodOut = new ObjectOutputStream(foodFileOut);
            foodOut.writeObject(Information.information.getMyFoods());
            foodFileOut.close();
            foodOut.close();
        }
        catch(Exception e) {

        }
    }

}