package com.example.android.projectfanta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (getIntent().getBooleanExtra("EXIT", false)) finish();

        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationBarHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager frag = getSupportFragmentManager();
        FragmentTransaction transaction = frag.beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            moveTaskToBack(true);
            finish(); // onPause, onStop, onDestroy
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

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