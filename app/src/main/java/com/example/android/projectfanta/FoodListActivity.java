package com.example.android.projectfanta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class FoodListActivity extends AppCompatActivity {
    List<Food> listFresh;
    EditText searchBar;
    RecycleViewAdapterFood myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_food_list);

        searchBar = findViewById(R.id.searchFood);
        searchBar.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        listFresh = new ArrayList<>();
        listFresh.add(new Food("Banana"));
        listFresh.add(new Food("Strawberry"));
        listFresh.add(new Food("Apple"));
        listFresh.add(new Food("Avocado"));
        listFresh.add(new Food("Watermelon"));

        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.freshFood_recycler);
        myAdapter = new RecycleViewAdapterFood(this,listFresh);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myRecyclerView.setAdapter(myAdapter);

    }

    private void filter(String text){
        List<Food> filteredList = new ArrayList<>();

        for(Food food: listFresh){
            if(food.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(food);
            }
        }

        myAdapter.filterList(filteredList);
    }
}
