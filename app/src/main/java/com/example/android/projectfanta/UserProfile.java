package com.example.android.projectfanta;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class UserProfile extends AppCompatActivity {

    TextView user;
    Button select;
    Dialog dialog;
    Button select_nutrient;
    RadioGroup rg;
    RadioButton nutrient;
    private SectionsPageAdapter myAdapters;
    private ViewPager myPagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        String username = getIntent().getStringExtra("username");
        user = (TextView)findViewById(R.id.myName);
        user.setText(username);
        select = (Button)findViewById(R.id.select);

        select.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                select.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        dialog = new Dialog(UserProfile.this);
                        dialog.setContentView(R.layout.custom_select);
                        dialog.show();
                        select_nutrient = (Button)dialog.findViewById(R.id.nut);
                        rg = (RadioGroup) dialog.findViewById(R.id.nutrientGraph);

                        select_nutrient.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int id = rg.getCheckedRadioButtonId();
                                nutrient = (RadioButton) dialog.findViewById(id);
                                System.out.println(nutrient.getText().toString());
                                dialog.dismiss();
                            }
                        });
                    }
                });

            }
        });

        myAdapters = new SectionsPageAdapter(getSupportFragmentManager());

        myPagers = (ViewPager) findViewById(R.id.historyContainer);
        setUpViewPager(myPagers);

        TabLayout tab = (TabLayout) findViewById(R.id.historyTabs);
        tab.setupWithViewPager(myPagers);
    }

    private void setUpViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserWeekFragment(), "Week");
        adapter.addFragment(new UserMonthFragment(), "Month");
        adapter.addFragment(new UserYearFragment(), "Year");
        viewPager.setAdapter(adapter);
    }

}
