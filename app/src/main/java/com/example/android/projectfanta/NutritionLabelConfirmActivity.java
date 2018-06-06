package com.example.android.projectfanta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class NutritionLabelConfirmActivity extends AppCompatActivity {

    private Food food;
    private Intake intake;
    private TextView name;
    private TextView serve;
    private TextView dataCals;
    private TextView dataFat;
    private TextView dataChol;
    private TextView dataSod;
    private TextView dataPot;
    private TextView dataCarb;
    private TextView dataFib;
    private TextView dataSug;
    private TextView dataProt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_label_confirm);
        Intent intent = getIntent();
        String[] detections = intent.getStringArrayExtra("Detections");
        HashMap<Integer, String> parsedData;

        dataCals = (TextView)findViewById(R.id.calories_field);
        dataFat = (TextView)findViewById(R.id.totalFat_field);
        dataChol = (TextView)findViewById(R.id.chol_input);
        dataSod = (TextView)findViewById(R.id.sodium_input);
        dataPot = (TextView)findViewById(R.id.potass_input);
        dataCarb = (TextView)findViewById(R.id.totCarb_input);
        dataFib = (TextView)findViewById(R.id.fiber_input);
        dataSug = (TextView)findViewById(R.id.sugar_input);
        dataProt = (TextView)findViewById(R.id.prote_input);
        name = (TextView)findViewById(R.id.name);
        serve = (TextView)findViewById(R.id.servings);

        if (!intent.getBooleanExtra("MAN", false)) {
            parsedData = parseData(detections);

            dataChol.setText(parsedData.get(8));
            dataCals.setText(parsedData.get(3));
            dataFat.setText(parsedData.get(1));
            dataSod.setText(parsedData.get(6));
            dataPot.setText(parsedData.get(7));
            dataCarb.setText(parsedData.get(2));
            dataSug.setText(parsedData.get(4));
            dataProt.setText(parsedData.get(5));
            dataFib.setText(parsedData.get(9));
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nutlabel_nav_items, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        food = new Food();
        intake = new Intake();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    public HashMap<Integer, String> parseData(String[] detects) {

        // Different possibilities of spellings the OCR catches
        String[] keys = {
                "total", // 0
                "fat lat fal", // 1
                "carbohydrates carb. carbs.", // 2
                "calories calri ceries caries caories caores caleries", // 3
                "sugars", // 4
                "protein", // 5
                "sodium", // 6
                "potassium", // 7
                "cholesterol", // 8
                "fiber", // 9
        };

        // turn detections into a big string
        String string = "";
        for(int i = 0; i<detects.length; ++i) {
            string += detects[i] + " ";
        }

        // gets rid of redundant spaces
        string = string.replaceAll("' '+", " ");
        string = string.replace('\n', ' ');

        HashMap<Integer, String> map = new HashMap<Integer, String>();

        String[] stringArray = string.split(" ");
        for(int i = 0; i < stringArray.length-1; ++i) {

            if(stringArray[i].length() <= 2) continue; // vitamin C -> calories issue

            // Check for over 2000 calorie value
            if(stringArray[i].equals("2,000") ||
                    stringArray[i].equals("2000") ||
                    stringArray[i].equals("3,000") ||
                    stringArray[i].equals("3000")) continue;


            if (keys[0].contains(stringArray[i].toLowerCase())) {
                for (int j = 1; j <= 2; ++j) {

                    if (keys[j].contains(stringArray[i + 1].toLowerCase())) {
                        String data = stringArray[i + 2];
                        data = data.replaceAll("[' 'mgq]+", "");
                        data = data.replaceAll("[OoDQ]", "0");
                        if (isDouble(data)) {
                            System.out.println("Added " + data + " " + keys[j]);
                            map.put(j, data);
                            stringArray[i] = "";
                            stringArray[i + 1] = "";
                            stringArray[i + 2] = "";
                            //remove from stringArray
                        }
                    }
                }
            } else {
                for (int j = 3; j < keys.length; ++j) {

                    if (keys[j].contains(stringArray[i].toLowerCase())) {
                        String data = stringArray[i + 1];
                        data = data.replaceAll("[' 'mgq]+", "");
                        data = data.replaceAll("[OoDQ]", "0");

                        if (isDouble(data)) {
                            System.out.println("Added " + data + " " + keys[j]);

                            map.put(j, data);
                            stringArray[i] = "";
                            stringArray[i + 1] = "";
                            //remove from stringArray
                        }
                    }
                }
            }
        }

        return map;
    }

    public void onConfirm(View view) {

        if(name.getText().toString().contains(".") || name.getText().toString().contains("/") ||
                name.getText().toString().contains("[") || name.getText().toString().contains("]") ||
                name.getText().toString().contains("#") || name.getText().toString().contains("$")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Names cannot contain . / [ ] # or $", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if(Information.information.hasFood(name.getText().toString())) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Enter a unique name!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (!TextUtils.isEmpty(serve.getText().toString()) && !TextUtils.isEmpty(name.getText().toString())) {


            if (!TextUtils.isEmpty(dataCals.getText().toString())) food.add(Food.CALORIES, Double.parseDouble(dataCals.getText().toString()));
            else food.add(Food.CALORIES, 0.0);

            if (!TextUtils.isEmpty(dataFat.getText().toString())) food.add(Food.FAT, Double.parseDouble(dataFat.getText().toString()));
            else food.add(Food.FAT, 0.0);

            if (!TextUtils.isEmpty(dataChol.getText().toString())) food.add(Food.CHOLESTEROL, Double.parseDouble(dataChol.getText().toString()));
            else food.add(Food.CHOLESTEROL, 0.0);

            if (!TextUtils.isEmpty(dataSod.getText().toString())) food.add(Food.SODIUM, Double.parseDouble(dataSod.getText().toString()));
            else food.add(Food.SODIUM, 0.0);

            if (!TextUtils.isEmpty(dataPot.getText().toString())) food.add(Food.POTASSIUM, Double.parseDouble(dataPot.getText().toString()));
            else food.add(Food.POTASSIUM, 0.0);

            if (!TextUtils.isEmpty(dataCarb.getText().toString())) food.add(Food.CARBS, Double.parseDouble(dataCarb.getText().toString()));
            else food.add(Food.CARBS, 0.0);

            if (!TextUtils.isEmpty(dataFib.getText().toString())) food.add(Food.FIBER, Double.parseDouble(dataFib.getText().toString()));
            else food.add(Food.FIBER, 0.0);

            if (!TextUtils.isEmpty(dataSug.getText().toString())) food.add(Food.SUGAR, Double.parseDouble(dataSug.getText().toString()));
            else food.add(Food.SUGAR, 0.0);

            if (!TextUtils.isEmpty(dataProt.getText().toString())) food.add(Food.PROTEIN, Double.parseDouble(dataProt.getText().toString()));
            else food.add(Food.PROTEIN, 0.0);


            food.setName(Food.getValidName(name.getText().toString()));
            intake.setFood(food.getName());
            intake.setCreationTime(System.currentTimeMillis());
            intake.setServings(Double.parseDouble(serve.getText().toString()));

            Information.information.addFood(getApplicationContext(), food);
            Information.information.addIntake(getApplicationContext(), intake);
            Intent homeIntent = new Intent(this, HomeActivity.class);

            startActivity(homeIntent);
            finish();


        } else {

            // POP up saying to enter servings and name
            if (TextUtils.isEmpty(serve.getText().toString())) {
                Context context = getApplicationContext();
                CharSequence text = "Please enter number of servings";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            if (TextUtils.isEmpty(name.getText().toString()) || name.getText().toString().equals("Name")) {
                Context context = getApplicationContext();
                CharSequence text = "Please enter a name";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        }
    }

    public void exit(View view){
        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    public boolean isDouble(String data) {

        boolean valid = false;
        try {

            // check
            Double.parseDouble(data);
            valid = true;

        }
        catch (NumberFormatException e){}
        return valid;
    }

    public void onClick(View view) {
        EditText editText = (EditText) findViewById(R.id.name);
        editText.setText("");
    }

    // LIFECYCLE
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

}
