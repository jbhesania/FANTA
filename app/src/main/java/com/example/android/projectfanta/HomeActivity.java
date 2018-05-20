package com.example.android.projectfanta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationBarHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager frag = getSupportFragmentManager();
        FragmentTransaction transaction = frag.beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment()).commit();

    }

<<<<<<< HEAD
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

    public void manual(View v) {
        Intent intentManual = new Intent(this, ChangeNutritionIntake.class);
        startActivity(intentManual);
    }
=======
>>>>>>> stephanie

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager frag = getSupportFragmentManager();
            FragmentTransaction transaction = frag.beginTransaction();
            switch (item.getItemId()) {
                case R.id.home:
                    transaction.replace(R.id.fragment_container, new HomeFragment()).commit();
                    return true;
                case R.id.history:
                    transaction.replace(R.id.fragment_container, new HistoryFragment()).commit();
                    return true;
                case R.id.friends:
                    transaction.replace(R.id.fragment_container, new FriendsFragment()).commit();
                    return true;
                case R.id.setting:
                    transaction.replace(R.id.fragment_container, new SettingsFragment()).commit();
                    return true;
            }
            return false;
        }
    };
}
