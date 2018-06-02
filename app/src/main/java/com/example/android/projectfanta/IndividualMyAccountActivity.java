package com.example.android.projectfanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 59881 on 6/2/2018.
 */

public class IndividualMyAccountActivity extends AppCompatActivity{
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
        EditText age = (EditText)findViewById(R.id.Age1);
        EditText weight = (EditText)findViewById(R.id.Weight1);
        EditText height = (EditText)findViewById(R.id.Height1);

        //SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();

        UserInfo userInfo = new UserInfo(Information.information.getInfo());
        userInfo.setAge(Integer.valueOf(age.getText().toString()));
        //System.out.println("Age is " + userInfo.getAge()+ "!!!!!!!!!!!!!!");
        userInfo.setHeight(Integer.valueOf(height.getText().toString()));
        userInfo.setWeight(Integer.valueOf(weight.getText().toString()));
        Information.information.setInfo(userInfo);
        /*
        editor.putInt("Age", Integer.valueOf(age.getText().toString()));
        editor.putInt("Weight", Integer.valueOf(weight.getText().toString()));
        editor.putInt("Height", Integer.valueOf(height.getText().toString()));
        editor.apply();*/
        Toast.makeText(IndividualMyAccountActivity.this, "Saved", Toast.LENGTH_SHORT).show();

        Intent homeIntent = new Intent(IndividualMyAccountActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }
}
