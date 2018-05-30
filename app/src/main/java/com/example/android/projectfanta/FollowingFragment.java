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

public class FollowingFragment extends Fragment {

    List<friendsData> dataFollowing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.following_fragment,container,false);

        EditText search = (EditText)view.findViewById(R.id.searchBar);

        dataFollowing = new ArrayList<>();

        dataFollowing.add(new friendsData( "markz_"));

        ListView list = (ListView)view.findViewById(R.id.listFollowing);
        ListViewAdapter lvAdapter = new ListViewAdapter(getContext(), R.layout.itemrow, dataFollowing);

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
