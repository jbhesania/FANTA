package com.example.android.projectfanta;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Information implements Serializable {
    private HashMap<String, Food> myFoods;
    private ArrayList<Intake> myIntakes;
    private UserInfo myInfo;
    // String here is the username
    private HashMap<String, User> myFollowers;
    private HashMap<String, User> imFollowing;

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
        this.myFollowers = new HashMap<String, User>();
        this.myFollowers.put(user.getUserName(),user);
        this.imFollowing = new HashMap<String, User>();
        this.imFollowing.put(user.getUserName(),user);
    }

    public Information() {};

    public UserInfo getInfo() { return myInfo; }
    public Food getFood(String name) { return myFoods.get(name); }
    public ArrayList<Intake> getMyIntakes() { return myIntakes; }
    public HashMap<String, Food> getMyFoods() { return myFoods; }
    public HashMap<String, User> getImFollowing() { return imFollowing; }
    public HashMap<String, User> getMyFollowers() { return myFollowers; }

    public void setFollowers(HashMap<String, User> myFollowers) {
        this.myFollowers = myFollowers;
    }

    public void setFollowing(HashMap<String, User> imFollowing) {
        this.imFollowing = imFollowing;
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

    /**
     * Whether or not I follows the given user
     * @param user the user in question
     * @return true if i follow user, false if not
     */
    public boolean follows(User user) {
        return this.imFollowing.containsKey(user.getUserName());
    }

    public void addFood(Food food) {
        this.myFoods.put(food.getName(), food);
    }

    public void addIntake(int index,Intake in) {
        this.myIntakes.add(in);
    }

    public boolean hasFood(String name) {
        return myFoods.containsKey(name);
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

//    public static InformationDB convertToDB(Information myInfo) {
//        InformationDB db = new InformationDB(myInfo.getInfo(), myInfo.imFollowing.get(myInfo.getInfo().getUserName()));
//        int index = 0;
//        HashMap<String, Intake> myIntakes = db.getMyIntakes();
//        for(Intake i : myInfo.myIntakes) {
//            myIntakes.put(index+"", i);
//            index++;
//        }
//        db.setFollowers(myInfo.myFollowers);
//        db.setFollowing(myInfo.imFollowing);
//        db.setInfo(myInfo.getInfo());
//        for(String f : myInfo.myFoods.keySet()) {
//            db.addFood(Food.convertToDB(myInfo.myFoods.get(f)));
//        }
//        return db;
//    }
}

/*
    Memory Writing code
    IN INFORMATION :

    this.myUser = user;
    this.myFoods = new HashMap<String, Food>();
    Food one = new Food("food1", this);
    System.out.println("HELLO ading calories " + one.add("Calories", 120));
    this.myFoods.put("food1", one);
    this.myIntakes = new ArrayList<Intake>();
    Intake intake1 = new Intake(one, 2);
    this.addIntake(intake1);
    this.myFollowers = new HashMap<String, User>();
    this.imFollowing = new HashMap<String, User>();

    IN login acitivity:
        User testUser = new User("fakeuid", "fakeusername");
        Information information = new Information(testUser);

        try {
            FileOutputStream fileOut =
                    openFileOutput("JoyaanTestFile", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(information);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.getClass().getCanonicalName();
            i.printStackTrace();
        }

        Information myInfo;
        try {
            FileInputStream fileIn = openFileInput("JoyaanTestFile");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            myInfo = (Information) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (Exception c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }

 */