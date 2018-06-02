package com.example.android.projectfanta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

    }

    @Override
    public void onStart(){
        super.onStart();
    }

    public void saveClick(View v){
        EditText age = (EditText)findViewById(R.id.Age);
        EditText weight = (EditText)findViewById(R.id.Weight);
        EditText height = (EditText)findViewById(R.id.Height);

        //SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();

        Information.information.getInfo().setAge(Integer.valueOf(age.getText().toString()));
        //System.out.println("Age is " + userInfo.getAge()+ "!!!!!!!!!!!!!!");
        Information.information.getInfo().setHeight(Integer.valueOf(height.getText().toString()));
        Information.information.getInfo().setWeight(Integer.valueOf(weight.getText().toString()));
        Information.information.setInfoToDB(Information.information.getInfo());
        /*
        editor.putInt("Age", Integer.valueOf(age.getText().toString()));
        editor.putInt("Weight", Integer.valueOf(weight.getText().toString()));
        editor.putInt("Height", Integer.valueOf(height.getText().toString()));
        editor.apply();*/
        Toast.makeText(MyAccountActivity.this, "Saved", Toast.LENGTH_SHORT).show();

        Intent homeIntent = new Intent(MyAccountActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }


}
