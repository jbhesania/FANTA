package com.example.android.projectfanta;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

/**
 * Created by User on 5/18/2018.
 */

public class ListViewAdapter extends ArrayAdapter<friendsData> implements Filterable {

    Context context;
    int resId;
    List<friendsData> data = null;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<friendsData> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resId = resource;
        this.data = objects;
    }

    static class DataHolder{

        ImageView profImage;
        TextView contactName;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DataHolder holder = null;
        if(convertView == null){
            //inflate xml file to be a programmable object
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();

            convertView = inflater.inflate(resId,null);

            holder = new DataHolder();
            holder.profImage = (ImageView)convertView.findViewById(R.id.profImage);
            holder.contactName = (TextView)convertView.findViewById(R.id.contactName);

            convertView.setTag(holder);
        }
        else{
            holder = (DataHolder)convertView.getTag();
        }

        friendsData dataFriends = data.get(position);
        holder.contactName.setText(dataFriends.contactName);
        holder.profImage.setImageResource(dataFriends.thumbnail);

        return convertView;
    }
}
