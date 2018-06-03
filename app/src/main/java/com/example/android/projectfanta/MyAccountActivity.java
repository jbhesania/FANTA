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

    private TextView name;
    private String gender;
    private TextView email;
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

        name = (TextView)findViewById(R.id.nameText);
        //gender = (Switch) findViewById(R.id.genderSwitch);
        email = (TextView)findViewById(R.id.emailText);
        age = (TextView)findViewById(R.id.ageText);
        weight = (TextView)findViewById(R.id.weightText);
        height = (TextView)findViewById(R.id.heightText);

        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        paGroup = (RadioGroup) findViewById(R.id.paGroup);


        if(user != null ) {
            //if (user.getName() != null) name.setText(user.getName());
            /*if (!TextUtils.isEmpty(user.getUserName().toString()))
                email.setText(user.getUserName());*/
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

        // Email is uneditable
        email.setEnabled(false);


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
        if (!TextUtils.isEmpty(name.getText().toString())) user.setName(name.getText().toString());
        if (gender == "f" || gender == "m") user.setGender(gender);
        if (!TextUtils.isEmpty(age.getText().toString())) user.setAge(Integer.parseInt(age.getText().toString()));
        if (!TextUtils.isEmpty(weight.getText().toString())) user.setWeight(Integer.parseInt(weight.getText().toString()));
        if (!TextUtils.isEmpty(height.getText().toString())) user.setHeight(Integer.parseInt(height.getText().toString()));
        if(pa >= 1 && pa <= 4) { user.setPa(pa); }
        information.setInfoToDB(user);
        Toast.makeText(MyAccountActivity.this, "Saved", Toast.LENGTH_SHORT).show();

        Intent homeIntent = new Intent(MyAccountActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }
}
