package com.example.android.projectfanta;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SearchView;
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
import java.util.Locale;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by User on 5/18/2018.
 */

public class SearchFragment extends Fragment {
    List<User> dataFriends;
    ListViewAdapter lvAdapter;
    EditText search;
    Button searchBtn;
    private HashMap<String, User> users;
    private HashMap<String, Food> theirFoods;
    private ArrayList<Intake> theirIntakes;

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
                        readUserInfo(userid);
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

    /**
     * loads the foods and intakes of a given userid
     * asynchronous need null checks on the theirFoods and theirIntakes even after call
     * @param uid the uid of the user to load their intakes and foods
     */
    public void readUserInfo(final String uid){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child(uid);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Food>> hash = new GenericTypeIndicator<HashMap<String, Food>>() {};
                GenericTypeIndicator<ArrayList<Intake>> list = new GenericTypeIndicator<ArrayList<Intake>>() {};

                theirFoods = dataSnapshot.child("myFoods").getValue(hash);
                theirIntakes = dataSnapshot.child("myIntakes").getValue(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}