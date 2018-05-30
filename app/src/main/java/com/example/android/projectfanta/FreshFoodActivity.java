package com.example.android.projectfanta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FreshFoodActivity extends AppCompatActivity {

    List<FreshFood> listFresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_fav_food);

        listFresh = new ArrayList<>();
        listFresh.add(new FreshFood("Banana", R.drawable.banana, "1.3g", "27g", "14g", "0.4g", 105, "3.1g"));
        listFresh.add(new FreshFood("Strawberry", R.drawable.strawberry, "1.3g", "27g", "14g", "0.4g", 105, "3.1g"));
        listFresh.add(new FreshFood("Watermelon", R.drawable.watermelon, "1.3g", "27g", "14g", "0.4g", 105, "3.1g"));
        listFresh.add(new FreshFood("Avocado", R.drawable.avocado, "1.3g", "27g", "14g", "0.4g", 105, "3.1g"));
        listFresh.add(new FreshFood("Cherry", R.drawable.cherry, "1.3g", "27g", "14g", "0.4g", 105, "3.1g"));
        listFresh.add(new FreshFood("Pineapple", R.drawable.pineapple, "1.3g", "27g", "14g", "0.4g", 105, "3.1g"));

        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.freshFood_recycler);
        RecycleViewAdapter myAdapter = new RecycleViewAdapter(this, listFresh);
        myRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        myRecyclerView.setAdapter(myAdapter);

    }



}
