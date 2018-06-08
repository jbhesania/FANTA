package com.example.android.projectfanta;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    TextView user;
    Button select;
    Dialog dialog;
    Button select_nutrient;
    RadioGroup rg;
    RadioButton nutrient;
    private SectionsPageAdapter myAdapters;
    private ViewPager myPagers;
    public UserWeekFragment wf;
    public UserMonthFragment mf;
    public UserYearFragment yf;

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
                                nutrientChoose();
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
        wf = new UserWeekFragment();
        mf = new UserMonthFragment();
        yf = new UserYearFragment();
        adapter.addFragment(wf, "Week");
        adapter.addFragment(mf, "Month");
        adapter.addFragment(yf, "Year");
        viewPager.setAdapter(adapter);
    }

    public void nutrientChoose() {
        long mill = 100;
        //Reset weekFragment
        if (mf != null && wf.isVisible()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .detach(wf)
                    .attach(wf)
                    .commit();
        }

        //Reset monthFragment
        if (mf != null && mf.isVisible()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .detach(mf)
                    .attach(mf)
                    .commit();
        }

        //Reset yearFragment
        if (yf != null && yf.isVisible()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .detach(yf)
                    .attach(yf)
                    .commit();
        }


        switch (nutrient.getText().toString()) {
            case "Calories":
                UserWeekFragment.graphUpdateWeek("calories", true);
                UserMonthFragment.graphUpdateMonth("calories",true);
                UserYearFragment.graphUpdateYear("calories", true);
                break;
            case "Total Fat":
                UserWeekFragment.graphUpdateWeek("fat", true);
                UserMonthFragment.graphUpdateMonth("fat",true);
                UserYearFragment.graphUpdateYear("fat", true);
                break;
            case "Cholesterol":
                UserWeekFragment.graphUpdateWeek("cholesterol", true);
                UserMonthFragment.graphUpdateMonth("cholesterol",true);
                UserYearFragment.graphUpdateYear("cholesterol", true);
                break;
            case "Sodium":
                UserWeekFragment.graphUpdateWeek("sodium",true);
                UserMonthFragment.graphUpdateMonth("sodium",true);
                UserYearFragment.graphUpdateYear("sodium", true);
                break;
            case "Potassium":
                UserWeekFragment.graphUpdateWeek("potassium",true);
                UserMonthFragment.graphUpdateMonth("potassium",true);
                UserYearFragment.graphUpdateYear("potassium", true);
                break;
            case "Total Carbs":
                UserWeekFragment.graphUpdateWeek("carbs", true);
                UserMonthFragment.graphUpdateMonth("carbs",true);
                UserYearFragment.graphUpdateYear("carbs", true);
                break;
            case "Dietary Fiber":
                UserWeekFragment.graphUpdateWeek("fiber",true);
                UserMonthFragment.graphUpdateMonth("fiber",true);
                UserYearFragment.graphUpdateYear("fiber", true);
                break;
            case "Sugars":
                UserWeekFragment.graphUpdateWeek("sugar",true);
                UserMonthFragment.graphUpdateMonth("sugar",true);
                UserYearFragment.graphUpdateYear("sugar", true);
                break;
            case "Protein":
                UserWeekFragment.graphUpdateWeek("protein",true);
                UserMonthFragment.graphUpdateMonth("protein",true);
                UserYearFragment.graphUpdateYear("protein", true);
                break;
            default:
                UserWeekFragment.graphUpdateWeek("calories",true);
                UserMonthFragment.graphUpdateMonth("calories",true);
                UserYearFragment.graphUpdateYear("calories", true);
                break;
        }
    }


}
