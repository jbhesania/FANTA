package com.example.android.projectfanta;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by User on 5/18/2018.
 */

public class ContactsFragment extends Fragment {
    List<User> dataFriends;
    ListViewAdapter lvAdapter;
    EditText search;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment,container,false);
        search= (EditText) view.findViewById(R.id.searchBar);
        dataFriends = new ArrayList<>();

        ListView list = (ListView)view.findViewById(R.id.listContact);
        lvAdapter = new ListViewAdapter(getContext(), R.layout.itemrow, dataFriends);

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
                ContactsFragment.this.lvAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return view;

    }
}
