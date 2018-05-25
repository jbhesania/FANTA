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
    private HashMap<String, Food> foods;
    private ArrayList<Intake> intakes;
    private UserInfo info;
    // String here is the username
    private HashMap<String, User> followers;
    private HashMap<String, User> following;

    /**
     * Create information object and populates it
     * @param user how the information object will be populated
     */
    public Information(UserInfo info, User user) {
        this.info = info;
        Food food = new Food(" ");
        food.add("calories", 0);
        this.foods = new HashMap<String, Food>();
        this.foods.put(" ", food);
        this.intakes = new ArrayList<Intake>();
        this.intakes.add(new Intake("", 0, 0));
        this.followers = new HashMap<String, User>();
        this.followers.put(user.getUserName(),user);
        this.following = new HashMap<String, User>();
        this.following.put(user.getUserName(),user);
    }

    public Information() {};

    public UserInfo getInfo() { return info; }
    public Food getFood(String name) { return foods.get(name); }
    public ArrayList<Intake> getMyIntakes() { return intakes; }
    public HashMap<String, Food> getMyFoods() { return foods; }
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

    public void setFoods(HashMap<String, Food> foods) {
        this.foods = foods;
    }

    public void setIntakes(ArrayList<Intake> intakes) {
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

    public void addFood(Food food) {
        this.foods.put(food.getName(), food);
    }

    public void addIntake(int index,Intake in) {
        this.intakes.add(in);
    }

    public boolean hasFood(String name) {
        return foods.containsKey(name);
    }

    public static InformationDB convertToDB(Information info) {
        InformationDB db = new InformationDB(info.getInfo(), info.following.get(info.getInfo().getUserName()));
        int index = 0;
        HashMap<String, Intake> intakes = db.getMyIntakes();
        for(Intake i : info.intakes) {
            intakes.put(index+"", i);
            index++;
        }
        db.setFollowers(info.followers);
        db.setFollowing(info.following);
        db.setInfo(info.getInfo());
        for(String f : info.foods.keySet()) {
            db.addFood(Food.convertToDB(info.foods.get(f)));
        }
        return db;
    }
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

        Information info;
        try {
            FileInputStream fileIn = openFileInput("JoyaanTestFile");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            info = (Information) in.readObject();
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