package com.example.android.projectfanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class NutritionLabelConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_label_confirm);
        Intent intent = getIntent();
        String[] detections = intent.getStringArrayExtra("Detections");
        HashMap<Integer, String> parsedData = parseData(detections);
        TextView dataCals = (TextView)findViewById(R.id.calories_field);
        TextView dataFat = (TextView)findViewById(R.id.totalFat_field);
        TextView dataChol = (TextView)findViewById(R.id.chol_input);
        TextView dataSod = (TextView)findViewById(R.id.sodium_input);
        TextView dataPot = (TextView)findViewById(R.id.potass_input);
        TextView dataCarb = (TextView)findViewById(R.id.totCarb_input);
        TextView dataFib = (TextView)findViewById(R.id.fiber_input);
        TextView dataSug = (TextView)findViewById(R.id.sugar_input);
        TextView dataProt = (TextView)findViewById(R.id.prote_input);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nutlabel_nav_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        TextView name = (TextView) findViewById(R.id.name);
        TextView serve = (TextView) findViewById(R.id.servings);
        TextView dataCals = (TextView)findViewById(R.id.calories_field);
        TextView dataFat = (TextView)findViewById(R.id.totalFat_field);
        TextView dataChol = (TextView)findViewById(R.id.chol_input);
        TextView dataSod = (TextView)findViewById(R.id.sodium_input);
        TextView dataPot = (TextView)findViewById(R.id.potass_input);
        TextView dataCarb = (TextView)findViewById(R.id.totCarb_input);
        TextView dataFib = (TextView)findViewById(R.id.fiber_input);
        TextView dataSug = (TextView)findViewById(R.id.sugar_input);
        TextView dataProt = (TextView)findViewById(R.id.prote_input);


        if (!TextUtils.isEmpty(serve.getText().toString())) {


            NutritionLabel new_label = new NutritionLabel(name.getText().toString());

            if (!TextUtils.isEmpty(dataCals.getText().toString())) new_label.setCalories(Integer.parseInt(dataCals.getText().toString()));
            if (!TextUtils.isEmpty(dataFat.getText().toString())) new_label.setTotalFat(Integer.parseInt(dataFat.getText().toString()));
            if (!TextUtils.isEmpty(dataChol.getText().toString())) new_label.setChol(Integer.parseInt(dataChol.getText().toString()));
            if (!TextUtils.isEmpty(dataSod.getText().toString())) new_label.setSodium(Integer.parseInt(dataSod.getText().toString()));
            if (!TextUtils.isEmpty(dataPot.getText().toString())) new_label.setPotassium(Integer.parseInt(dataPot.getText().toString()));
            if (!TextUtils.isEmpty(dataCarb.getText().toString())) new_label.setTotalCarb(Integer.parseInt(dataCarb.getText().toString()));
            if (!TextUtils.isEmpty(dataFib.getText().toString())) new_label.setFiber(Integer.parseInt(dataFib.getText().toString()));
            if (!TextUtils.isEmpty(dataSug.getText().toString())) new_label.setSugar(Integer.parseInt(dataSug.getText().toString()));
            if (!TextUtils.isEmpty(dataProt.getText().toString())) new_label.setProtein(Integer.parseInt(dataProt.getText().toString()));


            new_label.printDis();

            // Arun, push new_label to the database por favor

        }


        return super.onOptionsItemSelected(item);
    }

    public HashMap<Integer, String> parseData(String[] detects) {

        String[] keys = {
                "total", // 0
                "fat lat fal", // 1
                "carbohydrate carb. carbs.", // 2
                "calories calri ceries caries", // 3
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
        System.out.println(stringArray.length);
        for(int i = 0; i < stringArray.length-1; ++i) {

            if(stringArray[i].length() <= 2) continue; // vitamin C -> calories issue

            System.out.println(keys[0] + " == " + stringArray[i + 1].toLowerCase());
            if (keys[0].contains(stringArray[i].toLowerCase())) {
                for (int j = 1; j <= 2; ++j) {
                    System.out.println(keys[j] + " == " + stringArray[i + 1].toLowerCase());

                    if (keys[j].contains(stringArray[i + 1].toLowerCase())) {
                        String data = stringArray[i + 2];
                        data = data.replaceAll("[' 'mgq]+", "");
                        data = data.replaceAll("[OoDQ]", "0");
                        if (isInteger(data)) {
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
                    System.out.println(keys[j] + " == " + stringArray[i].toLowerCase());

                    if (keys[j].contains(stringArray[i].toLowerCase())) {
                        String data = stringArray[i + 1];
                        data = data.replaceAll("[' 'mgq]+", "");
                        data = data.replaceAll("[OoDQ]", "0");

                        if (isInteger(data)) {
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


    public boolean isInteger(String data) {

        boolean valid = false;


        try {

            // check
            Integer.parseInt(data);
            valid = true;



        }
        catch (NumberFormatException e){}

        return valid;
    }

    public void onClick(View view) {
        EditText editText = (EditText) findViewById(R.id.name);
        editText.setText("");
    }
}
