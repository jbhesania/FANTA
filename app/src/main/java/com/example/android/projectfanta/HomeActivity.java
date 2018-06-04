package com.example.android.projectfanta;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    Dialog dialog;
    EditText numberOfServing;
    Button button;
    String value;

    public void homeAddIntake(View view){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_pop_up);

        TextView foodText = findViewById(view.getId());
        final String foodName = foodText.getText().toString();
        if(foodName == null || foodName.equals("")) {
            return;
        }

        TextView text = (TextView) dialog.findViewById(R.id.foodServings);
        text.setText(foodName);

        dialog.show();

        numberOfServing = (EditText) dialog.findViewById(R.id.edit);
        button = (Button)dialog.findViewById(R.id.saving);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                value = numberOfServing.getText().toString();
                if(value == null || value.equals("")) {
                    dialog.dismiss();
                    return;
                }
                Double servings = new Double(value);
                Intake newIntake = new Intake(foodName, servings);
                Information.information.addIntake(getApplicationContext(), newIntake);

                dialog.dismiss();

                //TODO the keyboard still doesnt actually go away
            }
        });
    }




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