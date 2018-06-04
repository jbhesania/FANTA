package com.example.android.projectfanta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*ArrayList<Food> foodsToDisplay =  new ArrayList<Food>(Information.information.getMyFoods().values());
        for (Food entry: foodsToDisplay) {
            if(entry.getCount() <= 0) {
                foodsToDisplay.remove(entry);
            }
        }
        if(foodsToDisplay.size() == 0) {
            //TODO display a meesage saying that they have not yet eaten any foods!
        }
        else {
            Collections.sort(foodsToDisplay, new Comparator<Food>(){
                // Reverse order sorting means 1 and -1 are switched
                public int compare(Food o1, Food o2) {
                    if (o1.getCount() <= o2.getCount()) {
                        return 1;
                    } else if (o1.getCount() >= o2.getCount()){
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });

            // only display 8 or fewer items
            if(foodsToDisplay.size() > 8) {
                foodsToDisplay.subList(0,8).clear();
            }

            // tODO display these foods ( foodsToDisplay ) with a label at top saying "Your Favorite Foods"
        }*/


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
                    if(NetworkStatus.getInstance(getApplication()).isOnline()){
                        transaction.replace(R.id.fragment_container, new FriendsFragment()).commit();
                        return true;
                    }else {
                        Toast.makeText(getApplicationContext(), "No Internet Connection!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                case R.id.setting:
                    transaction.replace(R.id.fragment_container, new SettingsFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    /*public void nutrientChoose(View v) {
        RadioGroup rg = (RadioGroup) findViewById(R.id.nutrientGraph);
        int id = rg.getCheckedRadioButtonId();

        RadioButton nutrient = (RadioButton) findViewById(id);
        switch (nutrient.getText().toString()) {
            case "Calories":
                WeekFragment.setNutrient("calories");
                MonthFragment.setNutrient("calories");
                YearFragment.setNutrient("calories");
            case "Total Fat":
                WeekFragment.setNutrient("fat");
                MonthFragment.setNutrient("fat");
                YearFragment.setNutrient("fat");
            case "Cholesterol":
                WeekFragment.setNutrient("cholesterol");
                MonthFragment.setNutrient("cholesterol");
                YearFragment.setNutrient("cholesterol");
            case "Sodium":
                WeekFragment.setNutrient("sodium");
                MonthFragment.setNutrient("sodium");
                YearFragment.setNutrient("sodium");
            case "Potassium":
                WeekFragment.setNutrient("potassium");
                MonthFragment.setNutrient("potassium");
                YearFragment.setNutrient("potassium");
            case "Total Carbs":
                WeekFragment.setNutrient("carbs");
                MonthFragment.setNutrient("carbs");
                YearFragment.setNutrient("carbs");
            case "Dietary Fiber":
                WeekFragment.setNutrient("fiber");
                MonthFragment.setNutrient("fiber");
                YearFragment.setNutrient("fiber");
            case "Sugars":
                WeekFragment.setNutrient("sugar");
                MonthFragment.setNutrient("sugar");
                YearFragment.setNutrient("sugar");
            case "Protein":
                WeekFragment.setNutrient("protein");
                MonthFragment.setNutrient("protein");
                YearFragment.setNutrient("protein");
            default:
                WeekFragment.setNutrient("calories");
                MonthFragment.setNutrient("calories");
                YearFragment.setNutrient("calories");


        }*/
    //}

}