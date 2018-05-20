package com.example.android.projectfanta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 5/18/2018.
 */

public class ContactsFragment extends Fragment {
    private static final String TAG = "ContactsFragment";

    List<friendsData> dataFriends;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment,container,false);

        EditText search = (EditText)view.findViewById(R.id.searchBar);

        dataFriends = new ArrayList<>();

        dataFriends.add(new friendsData(R.drawable.liam, "Liam Hemsworth"));
        dataFriends.add(new friendsData(R.drawable.warren, "Warren Buffet"));
        dataFriends.add(new friendsData(R.drawable.mark, "Mark Zuckerberg"));
        dataFriends.add(new friendsData(R.drawable.liam, "Liam Hemsworth"));
        dataFriends.add(new friendsData(R.drawable.warren, "Warren Buffet"));
        dataFriends.add(new friendsData(R.drawable.mark, "Mark Zuckerberg"));
        dataFriends.add(new friendsData(R.drawable.liam, "Liam Hemsworth"));
        dataFriends.add(new friendsData(R.drawable.warren, "Warren Buffet"));
        dataFriends.add(new friendsData(R.drawable.mark, "Mark Zuckerberg"));
        dataFriends.add(new friendsData(R.drawable.liam, "Liam Hemsworth"));
        dataFriends.add(new friendsData(R.drawable.warren, "Warren Buffet"));
        dataFriends.add(new friendsData(R.drawable.mark, "Mark Zuckerberg"));
        dataFriends.add(new friendsData(R.drawable.liam, "Liam Hemsworth"));
        dataFriends.add(new friendsData(R.drawable.warren, "Warren Buffet"));
        dataFriends.add(new friendsData(R.drawable.mark, "Mark Zuckerberg"));

        ListView list = (ListView)view.findViewById(R.id.listContact);
        ListViewAdapter lvAdapter = new ListViewAdapter(getContext(), R.layout.itemrow, dataFriends);

        list.setAdapter(lvAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent contact_intent = new Intent(view.getContext(),UserProfile.class);
                startActivity(contact_intent);
            }
        });

        //TODO: get the search function to work

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return view;
    }
}
