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

        listFresh = new ArrayList<>(Information.information.getMyFoods().values());

        RecyclerView myRecyclerView = (RecyclerView) findViewById(R.id.freshFood_recycler);
        myAdapter = new RecycleViewAdapterFood(this,this,listFresh);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myRecyclerView.setAdapter(myAdapter);

    }

    private void filter(String text){
        List<Food> filteredList = new ArrayList<>();

        for(Food food: listFresh){
            String[] foodArray = food.getName().toLowerCase().split(" ");
            for (String foodName : foodArray) {
                if (foodName.startsWith((text.toLowerCase()))) {
                    filteredList.add(food);
                }
            }
        }

        myAdapter.filterList(filteredList);
    }
}
