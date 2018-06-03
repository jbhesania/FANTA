package com.example.android.projectfanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.android.projectfanta.Information.information;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChangeNutritionIntake extends AppCompatActivity {

    private TextView calories;
    private TextView carbs;
    private TextView fat;
    private TextView protein;
    private TextView sodium;
    private TextView sugars;
    private TextView cholesterol;
    private TextView potassium;
    private TextView fiber;

    private UserInfo user = information.getInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nutrition_intake);

        calories = findViewById(R.id.calText);
        carbs = findViewById(R.id.carbsText);
        fat = findViewById(R.id.fatText);
        protein = findViewById(R.id.protText);
        sodium = findViewById(R.id.sodText);
        sugars = findViewById(R.id.sugText);
        cholesterol = findViewById(R.id.cholesterolText);
        potassium = findViewById(R.id.potassiumText);
        fiber = findViewById(R.id.fiberText);

        if (user.getRecCalories() != 0) calories.setText(Double.toString(round(user.getRecCalories(), 1)));
        if (user.getRecCarbs() != 0) carbs.setText(Double.toString(round(user.getRecCarbs(), 1)));
        if (user.getRecFat() != 0) fat.setText(Double.toString(round(user.getRecFat(), 1)));
        if (user.getRecProtein() != 0) protein.setText(Double.toString(round(user.getRecProtein(), 1)));
        if (user.getRecSodium() != 0) sodium.setText(Double.toString(round(user.getRecSodium(), 1)));
        if (user.getRecSugars() != 0) sugars.setText(Double.toString(round(user.getRecSugars(), 1)));
        if (user.getRecCholesterol() != 0) cholesterol.setText(Double.toString(round(user.getRecCholesterol(), 1)));
        if (user.getRecPotassium() != 0) potassium.setText(Double.toString(round(user.getRecPotassium(),1)));
        if (user.getRecFiber() != 0) fiber.setText(Double.toString(round(user.getRecFiber(), 1)));
    }

    protected void saveNutritionIntakeButton(View v){
        if (!TextUtils.isEmpty(calories.getText().toString())) user.setRecCalories(Double.parseDouble(calories.getText().toString()));
        if (!TextUtils.isEmpty(carbs.getText().toString())) user.setRecCarbs(Double.parseDouble(carbs.getText().toString()));
        if (!TextUtils.isEmpty(fat.getText().toString())) user.setRecFat(Double.parseDouble(fat.getText().toString()));
        if (!TextUtils.isEmpty(protein.getText().toString())) user.setRecProtein(Double.parseDouble(protein.getText().toString()));
        if (!TextUtils.isEmpty(sodium.getText().toString())) user.setRecSodium(Double.parseDouble(sodium.getText().toString()));
        if (!TextUtils.isEmpty(sugars.getText().toString())) user.setRecSugars(Double.parseDouble(sugars.getText().toString()));
        if (!TextUtils.isEmpty(cholesterol.getText().toString())) user.setRecCholesterol(Double.parseDouble(cholesterol.getText().toString()));
        if (!TextUtils.isEmpty(potassium.getText().toString())) user.setRecPotassium(Double.parseDouble(potassium.getText().toString()));
        if (!TextUtils.isEmpty(fiber.getText().toString())) user.setRecFiber(Double.parseDouble(fiber.getText().toString()));
        Toast.makeText(ChangeNutritionIntake.this, "Saved", Toast.LENGTH_SHORT).show();
        Information.information.setInfoToDB();
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
    }

    protected void recommendedButton(View v) {
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
        double pa = user.getPa(); // Physical activity level (1, 2, 3, or 4)
        double height = 0.0254 * user.getHeight();
        double weight = 0.4535923 * user.getWeight();
        String gender = user.getGender();

        // Physical Activity Multiplier
        double paMultiplier = 1.0;
        if(pa == 2) {
            if(age <= 18) {
                if(gender.equals("m")) { paMultiplier = 1.13; }
                else { paMultiplier = 1.16; }
            }
            else {
                if(gender.equals("m")) { paMultiplier = 1.11; }
                else { paMultiplier = 1.12; }
            }
        }
        else if(pa == 3) {
            if(age <= 18) {
                if(gender.equals("m")) { paMultiplier = 1.26; }
                else { paMultiplier = 1.31; }
            }
            else {
                if(gender.equals("m")) { paMultiplier = 1.25; }
                else { paMultiplier = 1.27; }
            }
        }
        else if(pa == 4) {
            if(age <= 18) {
                if(gender.equals("m")) { paMultiplier = 1.42; }
                else { paMultiplier = 1.56; }
            }
            else {
                if(gender.equals("m")) { paMultiplier = 1.48; }
                else { paMultiplier = 1.45; }
            }
        }

        // Calories
        if( age <= 8) {
            if(gender.equals("m")) {
                recCal = 88.5 - (61.9*age) + paMultiplier * (26.7*weight + 903*height)+20;
            }
            else {
                recCal = 135.3 - (30.8*age)+ paMultiplier * (10*weight + 934*height)+20;
            }
        }
        else if( age <= 18) {
            if(gender.equals("m")) {
                recCal = 88.5 - (61.9*age) + paMultiplier * (26.7*weight + 903*height)+25;
            }
            else {
                recCal = 135.3 - (30.8*age)+ paMultiplier * (10*weight + 934*height)+25;
            }
        }
        else {
            if(gender.equals("m")) {
                recCal = 662 - (9.53*age) + paMultiplier * (15.9*weight+539.6*height);
            }
            else {
                recCal = 354 - (6.91*age) + paMultiplier * (9.36*weight+726*height);
            }
        }

        // Carbs
        recCarbs = 0.1375 * recCal;
        recFat = 0.030555 * recCal;
        recProt = 0.8 *weight;
        recSod = 1.5;
        if(gender.equals("m")) { recSug = 36; }
        else { recSug = 24; }
        recChol = 0.2;
        recPot = 4.7;
        recFiber = 0.014 * recCal;

        calories.setText(Double.toString(round(recCal, 1)));
        carbs.setText(Double.toString(round(recCarbs, 1)));
        fat.setText(Double.toString(round(recFat, 1)));
        protein.setText(Double.toString(round(recProt,1)));
        sodium.setText(Double.toString(round(recSod, 1)));
        sugars.setText(Double.toString(round(recSug, 1)));
        cholesterol.setText(Double.toString(round(recChol, 1)));
        potassium.setText(Double.toString(round(recPot, 1)));
        fiber.setText(Double.toString(round(recFiber, 1)));
    }

    protected double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
