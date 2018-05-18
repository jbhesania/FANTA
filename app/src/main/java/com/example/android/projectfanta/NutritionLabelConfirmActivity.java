package com.example.android.projectfanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class NutritionLabelConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_label_confirm);
        Intent intent = getIntent();
        String[] detections = intent.getStringArrayExtra("Detections");

        for(int i = 0; i < detections.length; ++i) {
            System.out.println(detections[i]);
        }

    }

    public void onClick(View view) {
        EditText editText = (EditText) findViewById(R.id.name);
        editText.setText("");
    }
}
