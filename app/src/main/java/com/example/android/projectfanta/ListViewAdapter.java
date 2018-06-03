package com.example.android.projectfanta;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by User on 5/18/2018.
 */

public class ListViewAdapter extends ArrayAdapter<User> implements Filterable {

    Context context;
    int resId;
    List<User> data = null;

//    private NotificationCompat.Builder notification;
//    private RemoteViews remoteView;
//    private int notification_id;
//    private NotificationManager notificationManager;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resId = resource;
        this.data = objects;
    }

    static class DataHolder{
        TextView contactName;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        DataHolder holder = null;
        if(convertView == null){
            //inflate xml file to be a programmable object
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();

            convertView = inflater.inflate(resId,null);

            holder = new DataHolder();
            holder.contactName = (TextView)convertView.findViewById(R.id.contactName);
            // holder.button = (ImageView)convertView.findViewById(R.id.followButton);

            convertView.setTag(holder);
        }
        else{
            holder = (DataHolder)convertView.getTag();
        }

        User dataFriends = data.get(position);
        holder.contactName.setText(dataFriends.getUserName());
        // holder.profImage.setImageResource(dataFriends.thumbnail);

        //TODO: Notification appear if the plus button is clicked to send follow request

//        notificationManager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
//        remoteView = new RemoteViews(getContext().getPackageName(), R.layout.custom_notif);
//
//        notification_id = (int) System.currentTimeMillis();
//        Intent button_intent = new Intent("button clicked");
//        button_intent.putExtra("id",notification_id);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),123,button_intent,0);
//        remoteView.setOnClickPendingIntent(R.id.followButton, pendingIntent);
//
//        holder.button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(),AcceptRejectFollow.class);
//                PendingIntent pendingIntent1 = PendingIntent.getActivity(getContext(),0, intent,0);
//
//                notification = new NotificationCompat.Builder(getContext());
//                notification.setSmallIcon(R.drawable.fantalogo)
//                        .setAutoCancel(true)
//                        .setCustomContentView(remoteView)
//                        .setContentIntent(pendingIntent1);
//
//                notificationManager.notify(notification_id,notification.build());
//
//            }
//        });

        return convertView;
    }
}