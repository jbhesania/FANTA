package com.example.android.projectfanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

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


/*
        for(int i = 0; i < detections.length; ++i) {
            System.out.println("DETECTIONS " + i);
            System.out.println(detections[i]);

            data = (TextView)findViewById(R.id.calories_field);
            data.setText(detections[i]);

            System.out.println("DETECTIONS " + i);
        }*/
        //parseData(detections);
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

        System.out.println("STRING");

        System.out.println(string);

        HashMap<Integer, String> map = new HashMap<Integer, String>();

        System.out.println("ENTER");
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
        System.out.println("EXIT");
        System.out.println(map.size());
        for(Map.Entry<Integer, String> item : map.entrySet()) {
            System.out.print(keys[item.getKey()] + " | ");
            System.out.println(item.getValue());
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
