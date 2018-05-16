package com.example.android.projectfanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MyAccountActivity extends AppCompatActivity {

    Button exitBtnAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        exitBtnAcc = (Button) findViewById(R.id.exit);

        exitBtnAcc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent exit_intent_acc = new Intent(MyAccountActivity.this,Settings.class);
                startActivity(exit_intent_acc);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    public void saveClick(View v){
        // SAVE THE USER INFO
    }


}
