package com.example.android.projectfanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 5/18/2018.
 */

public class SearchFragment extends Fragment {
    List<User> dataFriends;
    ListViewAdapter lvAdapter;
    EditText search;
    Button searchBtn;
    private HashMap<String, User> users;
    private static HashMap<String, Food> theirFoods;
    private static ArrayList<Intake> theirIntakes;
    private static UserInfo info;
    private static long ONE_DAY = 864 * (long)java.lang.Math.pow(10,5);
    private static long ONE_WEEK = 7*ONE_DAY;
    private static long ONE_MONTH = 31*ONE_DAY;
    private static long ONE_YEAR = 365*ONE_DAY;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment,container,false);
        search= (EditText) view.findViewById(R.id.searchBar);
        searchBtn = (Button) view.findViewById(R.id.searchUser);
        readUserMap();
        //TODO: stores the username in a string called username
        final String username = search.getText().toString();
        dataFriends = new ArrayList<>();

        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //TODO: send the username to the database to search for a certain
                String username = search.getText().toString();
                Log.v("SUCKS", username);
                String userid = "";
                if (users != null) {
                    for (String id : users.keySet()) {
                        if (users.get(id).getUserName().equals(username)){
                            userid = id;
                            Log.v("SUCKS", users.get(id).getUserName());
                            break;
                        }
                    }
                    if (userid.equals("")){
                        Toast.makeText(getContext(), "No Such User", Toast.LENGTH_LONG).show();
                    } else {
                        readUserInfo(userid, username);
                        Toast.makeText(getContext(), "Loading User", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Loading User List", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ListView list = (ListView)view.findViewById(R.id.listSearch);
        lvAdapter = new ListViewAdapter(getContext(), R.layout.itemrow, dataFriends);

        list.setAdapter(lvAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent contact_intent = new Intent(view.getContext(),UserProfile.class);
                startActivity(contact_intent);
            }
        });

        return view;

    }


    /**
     * read the list of users from the database and sets it to users
     * asynchronous need null checks on the users field even after call
     */
    public void readUserMap(){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, User>> hash = new GenericTypeIndicator<HashMap<String, User>>() {};
                users = dataSnapshot.getValue(hash);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static UserInfo getTheirInfo() { return info; }

    /**
     * loads the foods and intakes of a given userid
     * asynchronous need null checks on the theirFoods and theirIntakes even after call
     * @param uid the uid of the user to load their intakes and foods
     */
    public void readUserInfo(final String uid, final String username){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child(uid);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Food>> hash = new GenericTypeIndicator<HashMap<String, Food>>() {};
                GenericTypeIndicator<ArrayList<Intake>> list = new GenericTypeIndicator<ArrayList<Intake>>() {};

                theirFoods = dataSnapshot.child("myFoods").getValue(hash);
                theirIntakes = dataSnapshot.child("myIntakes").getValue(list);
                info = dataSnapshot.child("info").getValue(UserInfo.class);
                dataFriends.add(new User(uid, username));
                Intent user_intent = new Intent(getContext(), UserProfile.class);
                user_intent.putExtra("username", username);
                startActivity(user_intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Binary Search for time boundaries
     * @param time time we are looking for (start/end)
     * @return index of intake at time boundary
     */
    public static int searchForIntervalBoundary(long time) {

        int intakeIndex = 1;

        for (int i = theirIntakes.size() - 1; i > 0; i--) {
            if (theirIntakes.get(i).getCreationTime() < time) {
                intakeIndex = i;
                break;
            }
        }

        return intakeIndex;

    }

    /**
     * Sum of intakes over interval of time
     * @param start start of interval
     * @param end end of interval
     * @param nutrient the nutrient we are tracking over the specified interval of time
     * @return array of doubles the size of numDays with the sum of intakes for each day.
     *         The array that is returned can then be used for the graph.
     */
    public static double[] intakeInterval(long start, long end, String nutrient) {

        // Check the time interval we are looking in and set the time to
        // a day or a month
        int days_months_year = 0;
        if (end - start == ONE_WEEK) {
            days_months_year = 7;
        }

        else if (end - start == ONE_MONTH) {
            days_months_year = 31;
        }

        else if (end - start == ONE_YEAR) {
            days_months_year = 365;

        }

        // Create an array the size of the number of days in a week or month, or the number of
        // months in a year
        double[] nutrientIntake = new double[days_months_year];
        int startIndex = searchForIntervalBoundary(start);
        int endIndex = searchForIntervalBoundary(end);
        double intakeSum = 0;
        //long beginningOfDay = myIntakes.get(startIndex).getCreationTime();
        long beginningOfDay = start;
        int current_idx = 0;

        // handle no foods yet
        if( theirIntakes.size() == 1 ) return nutrientIntake;

        long curr_time;

        // loop through piece returned by search
        for (int i = startIndex; i <= endIndex; i++) {

            // Get nutrient amount and multiply by amount of servings. Add this to running total
            // of the amount of that nutrient.
            intakeSum += theirFoods.get(theirIntakes.get(i).getFood()).getNutrient(nutrient)
                    * theirIntakes.get(i).getServings();

            current_idx = 0;
            for (int j = 0; j < days_months_year; j++) {
                curr_time = start + (ONE_DAY*j);
                if (curr_time <= theirIntakes.get(i).getCreationTime()) {
                    current_idx = j;
                }
            }

            nutrientIntake[current_idx] += theirFoods.get(theirIntakes.get(i).getFood()).getNutrient(nutrient)
                    * theirIntakes.get(i).getServings();

        }

        return nutrientIntake;

    }
}