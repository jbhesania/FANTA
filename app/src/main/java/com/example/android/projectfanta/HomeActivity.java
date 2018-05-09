package com.example.android.projectfanta;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.lang.reflect.Field;

public class HomeActivity extends AppCompatActivity {

    // UI components for navigation bar
    FloatingActionButton fab_plus, fab_search, fab_camera, fab_fresh, fab_manual;
    Animation fabOpen, fabClose, fabForward, fabBackward;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fab_plus = (FloatingActionButton)findViewById(R.id.plus);
        fab_search = (FloatingActionButton)findViewById(R.id.search);
        fab_camera = (FloatingActionButton)findViewById(R.id.camera);
        fab_fresh = (FloatingActionButton)findViewById(R.id.fresh);
        fab_manual = (FloatingActionButton)findViewById(R.id.manual);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        fabForward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        fabBackward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        fab_plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isOpen){
                    fab_camera.startAnimation(fabClose);
                    fab_fresh.startAnimation(fabClose);
                    fab_manual.startAnimation(fabClose);
                    fab_search.startAnimation(fabClose);
                    fab_plus.startAnimation(fabBackward);
                    fab_camera.setClickable(false);
                    fab_fresh.setClickable(false);
                    fab_manual.setClickable(false);
                    fab_search.setClickable(false);
                    isOpen = false;
                }
                else{
                    fab_camera.startAnimation(fabOpen);
                    fab_fresh.startAnimation(fabOpen);
                    fab_manual.startAnimation(fabOpen);
                    fab_search.startAnimation(fabOpen);
                    fab_plus.startAnimation(fabForward);
                    fab_camera.setClickable(true);
                    fab_fresh.setClickable(true);
                    fab_manual.setClickable(true);
                    fab_search.setClickable(true);
                    isOpen = true;
                }
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        BottomNavigationBarHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        Intent home_intent = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(home_intent);
                        break;
                    case R.id.friends:
                        Intent friend_intent = new Intent(HomeActivity.this, Friends.class);
                        startActivity(friend_intent);
                        break;
                    case R.id.setting:
                        Intent setting_intent = new Intent(HomeActivity.this, Settings.class);
                        startActivity(setting_intent);
                        break;
                    case R.id.history:
                        Intent history_intent = new Intent(HomeActivity.this, History.class);
                        startActivity(history_intent);
                        break;
                }
                return false;
            }
        });

        fab_camera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

//        homeButton = (Button) findViewById(R.id.Home);
//        friendsButton = (Button) findViewById(R.id.Friends);
//        addButton = (Button) findViewById(R.id.Add);
//        settingsButton = (Button) findViewById(R.id.Settings);

//        final ImageView homeMenu = new ImageView(this);
//        homeMenu.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plus));

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
