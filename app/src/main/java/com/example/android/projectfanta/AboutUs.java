package com.example.android.projectfanta;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AboutUs extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    List<AboutUsItem> items;
    AboutUsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        recyclerView = (RecyclerView)findViewById(R.id.aboutUsView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initItem();

    }

    private void initItem(){
        items = new ArrayList<>();
        items.add(new AboutUsItem("Joyaan Percy Bhesania", "Project Manager",R.drawable.fanta));
        items.add(new AboutUsItem("Bingtian Huang", "Senior System Analyst",R.drawable.fanta));
        items.add(new AboutUsItem("Linxuan Wang", "Software Architect",R.drawable.fanta));
        items.add(new AboutUsItem("Tom Toma", "Software Development Lead",R.drawable.fanta));
        items.add(new AboutUsItem("Oren G Lenchner", "Project Manager",R.drawable.fanta));
        items.add(new AboutUsItem("Arun Nathaniel Sugumar", "Database Specialist",R.drawable.fanta));
        items.add(new AboutUsItem("Jay S Chitale", "Business Analyst",R.drawable.fanta));
        items.add(new AboutUsItem("Stephanie Hartono", "User Interface Specialist",R.drawable.fanta));
        items.add(new AboutUsItem("Yixuan Wang", "Quality Assurance Lead",R.drawable.fanta));

        adapter = new AboutUsAdapter(this, items);
        recyclerView.setAdapter(adapter);


    }
}
