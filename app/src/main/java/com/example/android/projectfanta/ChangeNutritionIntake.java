package com.example.android.projectfanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ChangeNutritionIntake extends AppCompatActivity {

    Button exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_nutrition_intake);

        exitBtn = (Button)findViewById(R.id.exit);

        exitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent exit_intent = new Intent(ChangeNutritionIntake.this,Settings.class);
                startActivity(exit_intent);
            }
        });

    }

}
