package com.example.android.projectfanta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 5/18/2018.
 */

public class FollowersFragment extends Fragment {

    List<User> dataFollowers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.followers_fragment,container,false);

        EditText search = (EditText)view.findViewById(R.id.searchBar);

        dataFollowers = new ArrayList<>();

        dataFollowers.add(new User("101","liam1990"));
        dataFollowers.add(new User("102", "wbuffet"));

        ListView list = (ListView)view.findViewById(R.id.listFollowers);
        ListViewAdapter lvAdapter = new ListViewAdapter(getContext(), R.layout.itemrow, dataFollowers);

        list.setAdapter(lvAdapter);

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
