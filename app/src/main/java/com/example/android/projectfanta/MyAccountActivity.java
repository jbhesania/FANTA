package com.example.android.projectfanta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.android.projectfanta.UserInfo;
import static com.example.android.projectfanta.Information.information;



public class MyAccountActivity extends AppCompatActivity {

    private String gender;
    private TextView age;
    private TextView weight;
    private TextView height;
    private int pa;

    private UserInfo user = information.getInfo();

    RadioGroup genderGroup;
    RadioGroup paGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        //gender = (Switch) findViewById(R.id.genderSwitch);
        age = (TextView)findViewById(R.id.ageText);
        weight = (TextView)findViewById(R.id.weightText);
        height = (TextView)findViewById(R.id.heightText);

        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        paGroup = (RadioGroup) findViewById(R.id.paGroup);


        if(user != null ) {
            if (user.getAge() != 0) age.setText(Integer.toString(user.getAge()));
            if (user.getWeight() != 0) weight.setText(Integer.toString(user.getWeight()));
            if (user.getHeight() != 0) height.setText(Integer.toString(user.getHeight()));
            if (user.getGender() == "f") genderGroup.check(R.id.female);
            else if (user.getGender() == "m") genderGroup.check(R.id.male);

            if (user.getPa() == 1) paGroup.check(R.id.sedentary);
            else if (user.getPa() == 2) paGroup.check(R.id.lowactive);
            else if (user.getPa() == 3) paGroup.check(R.id.active);
            else if (user.getPa() == 4) paGroup.check(R.id.veryactive);
        }


    }

    @Override
    public void onStart(){
        super.onStart();
    }



    public void genderGroupClick(View v) {
        int radioId = genderGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) findViewById(radioId);
        if (rb.getText().equals("Female")) gender = "f";
        else gender = "m";
    }

    public void paGroupClick(View v) {
        int radioId = paGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) findViewById(radioId);
        if(rb.getText().equals("Sedentary")) pa = 1;
        else if(rb.getText().equals("Low Active")) pa = 2;
        else if(rb.getText().equals("Active")) pa = 3;
        else if(rb.getText().equals("Very Active")) pa = 4;
    }

    public void saveClick(View v){
        if (gender == "f" || gender == "m") user.setGender(gender);
        if (!TextUtils.isEmpty(age.getText().toString())) user.setAge(Integer.parseInt(age.getText().toString()));
        if (!TextUtils.isEmpty(weight.getText().toString())) user.setWeight(Integer.parseInt(weight.getText().toString()));
        if (!TextUtils.isEmpty(height.getText().toString())) user.setHeight(Integer.parseInt(height.getText().toString()));
        if(pa >= 1 && pa <= 4) { user.setPa(pa); }

        Toast.makeText(MyAccountActivity.this, "Saved", Toast.LENGTH_SHORT).show();

        if(user.getRecCalories() == 0 && user.getRecCarbs() == 0 && user.getRecCholesterol() == 0 && user.getRecFiber() == 0 && user.getRecPotassium() == 0 &&
                user.getRecProtein() == 0 && user.getRecSugars() == 0 && user.getRecSodium() == 0) {
            double recCal;
            double recCarbs;
            double recFat;
            double recProt;
            double recSod;
            double recSug;
            double recChol;
            double recPot;
            double recFiber;

            // Calories
            int age = user.getAge();
            double pa = user.getPa();
            double height = 0.0254 * user.getHeight();
            double weight = 0.4535923 * user.getWeight();
            String gender = user.getGender();
            if( age <= 8) {
                if(gender == "m") {
                    recCal = 88.5 - (61.9*age) + pa * (26.7*weight + 903*height)+20;
                }
                else {
                    recCal = 135.3 - (30.8*age)+ pa * (10*weight + 934*height)+20;
                }
            }
            else if( age <= 18) {
                if(gender == "m") {
                    recCal = 88.5 - (61.9*age) + pa * (26.7*weight + 903*height)+25;
                }
                else {
                    recCal = 135.3 - (30.8*age)+ pa * (10*weight + 934*height)+25;
                }
            }
            else {
                if(gender == "m") {
                    recCal = 662 - (9.53*age) + pa * (15.9*weight+539.6*height);
                }
                else {
                    recCal = 354 - (6.91*age) + pa * (9.36*weight+726*height);
                }
            }

            // Carbs
            user.setRecCalories(recCal);
            user.setRecFat(0.030555 * recCal);
            user.setRecProtein(0.8 *weight);
            user.setRecSodium(1.5);
            if(gender == "f") { user.setRecSugars(24); }
            else { user.setRecSugars(36); }
            user.setRecCholesterol(0.2);
            user.setRecPotassium(4.7);
            user.setRecFiber(0.014 * recCal);
            user.setRecCarbs(0.1375 * recCal);

        }
        Information.information.setInfoToDB();
        Intent homeIntent = new Intent(MyAccountActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }
}