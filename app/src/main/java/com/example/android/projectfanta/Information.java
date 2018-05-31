package com.example.android.projectfanta;

import android.content.Context;
import android.util.Log;

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

public class Information implements Serializable {

    public static Information information;
    private static double ONE_DAY = 8.64*java.lang.Math.pow(10,7);
    private static double ONE_WEEK = 7*ONE_DAY;
    private static double ONE_MONTH = 4*ONE_WEEK;
    private static double ONE_YEAR = 12*ONE_MONTH;


    private HashMap<String, Food> myFoods;
    private ArrayList<Intake> myIntakes;
    private UserInfo myInfo;
    // String here is the username
    private HashMap<String, User> myFollowers;
    private HashMap<String, User> imFollowing;
    DatabaseReference mData;

    public static final String FOOD_FILE = "myFoods";
    public static final String INTAKE_FILE = "myIntakes";
    public static final String MYFOLLOWERS_FILE = "myFollowers";
    public static final String IMFOLLOWING_FILE = "imFollowing";
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
    public void setFollowers(HashMap<String, User> myFollowers) { this.myFollowers = myFollowers; }
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

            FileInputStream imfollowingFileIn = context.getApplicationContext().openFileInput(Information.IMFOLLOWING_FILE);
            ObjectInputStream imfollowingIn = new ObjectInputStream(imfollowingFileIn);
            Information.information.setFollowing((HashMap<String, User>) imfollowingIn.readObject());
            imfollowingFileIn.close();
            imfollowingIn.close();

            FileInputStream myfollowersFileIn = context.getApplicationContext().openFileInput(Information.MYFOLLOWERS_FILE);
            ObjectInputStream myfollowersIn = new ObjectInputStream(myfollowersFileIn);
            Information.information.setFollowers((HashMap<String, User>) myfollowersIn.readObject());
            myfollowersFileIn.close();
            myfollowersIn.close();

            FileInputStream userFileIn = context.getApplicationContext().openFileInput(Information.USERINFO_FILE);
            ObjectInputStream userIn = new ObjectInputStream(userFileIn);
            Information.information.setInfo((UserInfo) userIn.readObject());
            userFileIn.close();
            userIn.close();

            if(Information.information.getMyFoods() == null || Information.information.getMyIntakes() == null ||
                    Information.information.getImFollowing() == null || Information.information.getMyFollowers() == null
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

            FileOutputStream followersFileOut =
                    context.getApplicationContext().openFileOutput(Information.MYFOLLOWERS_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream followersOut = new ObjectOutputStream(followersFileOut);
            followersOut.writeObject(Information.information.getMyFollowers());
            followersOut.close();
            followersFileOut.close();

            FileOutputStream followingFileOut =
                    context.getApplicationContext().openFileOutput(Information.IMFOLLOWING_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream followingOut = new ObjectOutputStream(followingFileOut);
            followingOut.writeObject(Information.information.getImFollowing());
            followingOut.close();
            followingFileOut.close();

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
                    context.getApplicationContext().openFileOutput(Information.MYFOLLOWERS_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream followersOut = new ObjectOutputStream(followersFileOut);
            followersOut.writeObject(Information.information.getMyFollowers());
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

    /**
     * Binary Search for time boundaries
     * @param time time we are looking for (start/end)
     * @return index of intake at time boundary
     */
    public int searchForIntervalBoundary(long time) {

        int intakeIndex = 0;
        int upper = myIntakes.size();
        int lower = 0;
        boolean found = false;
        int m;

        while (found == false) {

            if (upper < lower) return 0;

            m = lower + (upper - lower)/2;

            if (myIntakes.get(m).getCreationTime() < time) lower = m + 1;
            else if (myIntakes.get(m).getCreationTime() > time) upper = m - 1;
            else if (myIntakes.get(m).getCreationTime() == time) {
                intakeIndex = m;
                found = true;
            }

        }

        return intakeIndex;

    }

    /**
     * Sum of intakes over interval of time
     * @param start start of interval
     * @param end end of interval
     * @return array of doubles the size of numDays with the sum of intakes for each day.
     *         The array that is returned can then be used for the graph.
     */
    public double[] intakeInterval(long start, long end) {

        // Check the time interval we are looking in and set the time to
        // a day or a month
        int days_months_year = 0;
        double time = 0;
        if (end - start == ONE_WEEK) {
            time = ONE_DAY;
            days_months_year = 7;
        }

        else if (end - start == ONE_MONTH) {
            time = ONE_DAY;
            days_months_year = 31;
        }

        else if (end - start == ONE_YEAR) {
            time = ONE_MONTH;
            days_months_year = 12;
        }

        // Create an array the size of the number of days in a week or month, or the number of
        // months in a year
        double[] nutrientIntake = new double[days_months_year];
        int startTime = searchForIntervalBoundary(start);
        int endTime = searchForIntervalBoundary(end);
        double intakeSum = 0;
        long beginningOfDay = myIntakes.get(startTime).getCreationTime();
        int unit_time = 0;

        // loop through piece returned by search
        for (int i = startTime; i <= endTime; i++) {

            // if current intake time exceeds a day or month from the start
            if (myIntakes.get(i).getCreationTime() -  beginningOfDay > time) {

                // put current sum into array
                nutrientIntake[unit_time] = intakeSum;
                unit_time++;

                // set sum to 0
                intakeSum = myIntakes.get(i).getServings();

                // set current intake creation time as start of new day
                beginningOfDay = myIntakes.get(i).getCreationTime();

            }

            if (unit_time == days_months_year) break; // check if we have intakes for the whole week

            intakeSum += myIntakes.get(i).getServings(); // sum over intake for each time unit

        }

        return nutrientIntake;

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