package com.example.android.projectfanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    // UI components for navbar
    private Button homeButton;
    private Button friendsButton;
    private Button addButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        homeButton = (Button) findViewById(R.id.Home);
//        friendsButton = (Button) findViewById(R.id.Friends);
//        addButton = (Button) findViewById(R.id.Add);
//        settingsButton = (Button) findViewById(R.id.Settings);

        final ImageView homeMenu = new ImageView(this);
        homeMenu.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plus));

    }

    // Start Friends Activity
    public void friends(View view) {

        Intent friends_intent = new Intent(this, Friends.class);
        startActivity(friends_intent);

    }

    // Start NutritionInfo Activity
    public void addNutritionInfo(View view) {

        Intent addIntent = new Intent(this, NutritionInfo.class);
        startActivity(addIntent);
    }

    // Start Settings Activity
    public void settings(View view) {

        Intent intentSettings = new Intent(this, Settings.class);
        startActivity(intentSettings);
    }

    // Start Home Activity (remains here)
    public void goHome(View view) {

        Intent intentHome = new Intent(this, HomeActivity.class);
        startActivity(intentHome);
    }

    // Start History Activity
    public void history(View view) {

        Intent intentHistory = new Intent(this, History.class);
        startActivity(intentHistory);

    }

}
