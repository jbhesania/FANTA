package com.example.android.projectfanta;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by User on 5/17/2018.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private Context foodContext;
    private List<FreshFood> foodData;


    public RecycleViewAdapter(Context foodContext, List<FreshFood> foodData) {
        this.foodContext = foodContext;
        this.foodData = foodData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View foodView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cardview_item, parent, false);
        return new MyViewHolder(foodView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.fresh_food_title.setText(foodData.get(position).getFreshFoodName());
        holder.fresh_food_img.setImageResource(foodData.get(position).getThumbnail());
        holder.overflow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(foodContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_fresh, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */

    @Override
    public int getItemCount() {
        return foodData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fresh_food_title;
        ImageView fresh_food_img;
        ImageView overflow;

        public MyViewHolder(View itemView) {
            super(itemView);

            fresh_food_title = (TextView) itemView.findViewById(R.id.freshName);
            fresh_food_img = (ImageView) itemView.findViewById(R.id.freshImage);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);

        }
    }

    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.overflow:
                    Toast.makeText(foodContext, "Added to list", Toast.LENGTH_SHORT).show();
                    return true;

                default:
            }
            return false;
        }
    }
}
