package com.example.android.projectfanta;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class InformationDB implements Serializable {
    private HashMap<String, FoodDB> foods;
    private HashMap<String,Intake> intakes;
    private UserInfo info;
    // String here is the username
    private HashMap<String, User> followers;
    private HashMap<String, User> following;

    /**
     * Create information object and populates it
     * @param user how the information object will be populated
     */
    public InformationDB(UserInfo info, User user) {
        this.info = info;
        FoodDB food = new FoodDB(" ");
        food.add("calories", 0);
        this.foods = new HashMap<String, FoodDB>();
        this.foods.put(" ", food);
        this.intakes = new HashMap<String, Intake>();
        this.intakes.put(0+"", new Intake("", 0, 0));
        this.followers = new HashMap<String, User>();
        this.followers.put(user.getUserName(),user);
        this.following = new HashMap<String, User>();
        this.following.put(user.getUserName(),user);
    }

    public InformationDB() {};

    public User getInfo() { return info; }
    public FoodDB getFood(String name) { return foods.get(name); }
    public HashMap<String, Intake> getMyIntakes() { return intakes; }
    public HashMap<String, FoodDB> getMyFoods() { return foods; }
    public HashMap<String, User> getImFollowing() { return following; }
    public HashMap<String, User> getMyFollowers() { return followers; }

    public void setFollowers(HashMap<String, User> followers) {
        this.followers = followers;
    }

    public void setFollowing(HashMap<String, User> following) {
        this.following = following;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public void setFoods(HashMap<String, FoodDB> foods) {
        this.foods = foods;
    }

    public void setIntakes(HashMap<String, Intake> intakes) {
        this.intakes = intakes;
    }

    /**
     * Whether or not I follows the given user
     * @param user the user in question
     * @return true if i follow user, false if not
     */
    public boolean follows(User user) {
        return this.following.containsKey(user.getUserName());
    }

    public void addFood(FoodDB food) {
        this.foods.put(food.getName(), food);
    }

    public void addIntake(int index,Intake in) {
        this.intakes.put(index+"",in);
    }

    public boolean hasFood(String name) {
        return foods.containsKey(name);
    }

    static void read(String uid, final InformationDB toSet){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                InformationDB info = dataSnapshot.getValue(InformationDB.class);
                toSet.followers = info.followers;
                toSet.foods = info.foods;
                toSet.intakes = info.intakes;
                toSet.info = info.info;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("sucks", "loadUid:onCancelled", databaseError.toException());
                // ...
            }
        };

        FirebaseDatabase.getInstance().getReference().child(uid).addValueEventListener(postListener);
    }

    public static Information convertToNormal(InformationDB db) {
        Information info = new Information(db.info, db.following.get(db.info.getUserName()));

        ArrayList<Intake> intakes = new ArrayList<Intake>();
        for(int i =0; i < db.getMyIntakes().size() ; i++) {
            intakes.add(db.intakes.get(""+i));
        }
        db.setFollowers(db.followers);
        db.setFollowing(db.following);
        db.setInfo(info.getInfo());
        for(String f : db.foods.keySet()) {
            info.addFood(FoodDB.convertToNormal(db.foods.get(f)));
        }
        return info;
    }

}
