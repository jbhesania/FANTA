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
    private HashMap<String, FoodDB> myFoods;
    private HashMap<String,Intake> myIntakes;
    private UserInfo myInfo;
    // String here is the username
    private HashMap<String, User> myFollowers;
    private HashMap<String, User> imFollowing;

    /**
     * Create information object and populates it
     * @param user how the information object will be populated
     */
    public InformationDB(UserInfo myInfo, User user) {
        this.myInfo = myInfo;
        FoodDB food = new FoodDB(" ");
        food.add("calories", 0);
        this.myFoods = new HashMap<String, FoodDB>();
        this.myFoods.put(" ", food);
        this.myIntakes = new HashMap<String, Intake>();
        this.myIntakes.put(0+"", new Intake("", 0, 0));
        this.myFollowers = new HashMap<String, User>();
        this.myFollowers.put(user.getUserName(),user);
        this.imFollowing = new HashMap<String, User>();
        this.imFollowing.put(user.getUserName(),user);
    }

    public InformationDB() {};

    public User getInfo() { return myInfo; }
    public FoodDB getFood(String name) { return myFoods.get(name); }
    public HashMap<String, Intake> getMyIntakes() { return myIntakes; }
    public HashMap<String, FoodDB> getMymyFoods() { return myFoods; }
    public HashMap<String, User> getImFollowing() { return imFollowing; }
    public HashMap<String, User> getMyFollowers() { return myFollowers; }

    public void setFollowers(HashMap<String, User> myFollowers) {
        this.myFollowers = myFollowers;
    }

    public void setFollowing(HashMap<String, User> imFolling) {
        this.imFollowing = imFolling;
    }

    public void setInfo(UserInfo myInfo) {
        this.myInfo = myInfo;
    }

    public void setmyFoods(HashMap<String, FoodDB> myFoods) {
        this.myFoods = myFoods;
    }

    public void setIntakes(HashMap<String, Intake> myIntakes) {
        this.myIntakes = myIntakes;
    }

    /**
     * Whether or not I follows the given user
     * @param user the user in question
     * @return true if i follow user, false if not
     */
    public boolean follows(User user) {
        return this.imFollowing.containsKey(user.getUserName());
    }

    public void addFood(FoodDB food) {
        this.myFoods.put(food.getName(), food);
    }

    public void addIntake(int index,Intake in) {
        this.myIntakes.put(index+"",in);
    }

    public boolean hasFood(String name) {
        return myFoods.containsKey(name);
    }

    static void read(String uid, final InformationDB toSet, final Callback call){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                InformationDB myInfo = dataSnapshot.getValue(InformationDB.class);
//                String s = myInfo.myInfo.getId();
//                Log.v("SUCKS", s+ "    SUCKS");
//                toSet.setFollowers(new HashMap<String, User>(myInfo.myFollowers));
//                toSet.setmyFoods(new HashMap<String, FoodDB>(myInfo.myFoods));
//                toSet.setIntakes(new HashMap<String, Intake>(myInfo.myIntakes));
//                toSet.setInfo(new UserInfo(myInfo.myInfo));
//                toSet.setFollowing(new HashMap<String, User>(myInfo.imFolling));
                call.onComplete(myInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("sucks", "loadUid:onCancelled", databaseError.toException());
                // ...
            }
        };
        FirebaseDatabase.getInstance().getReference().child(uid).addListenerForSingleValueEvent(postListener);
    }

    public static Information convertToNormal(InformationDB db) {
        Information info = new Information(db.myInfo, db.imFollowing.get(db.myInfo.getUserName()) );

        ArrayList<Intake> myIntakes = new ArrayList<Intake>();
        for(int i =0; i < db.getMyIntakes().size() ; i++) {
            info.getMyIntakes().add(db.myIntakes.get(""+i));
        }
        db.setFollowers(db.myFollowers);
        db.setFollowing(db.imFollowing);
        db.setInfo(info.getInfo());
        for(String f : db.myFoods.keySet()) {
            info.addFood(FoodDB.convertToNormal(db.myFoods.get(f)));
        }
        return info;
    }

}