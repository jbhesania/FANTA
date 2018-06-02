package com.example.android.projectfanta;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 5/17/2018.
 */
public class RecycleViewAdapterFood extends RecyclerView.Adapter<RecycleViewAdapterFood.MyViewHolder>{

    private List<Food> foodData;
    private Context context;
    Dialog dialog;


    public RecycleViewAdapterFood(Context context, List<Food> foodData) {
        this.context = context;
        this.foodData = foodData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View foodView = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_layout,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(foodView);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_pop_up);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button button = (Button)dialog.findViewById(R.id.saving);

        //TODO: Intent not working for "Save Button", find out

//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent save_intent = new Intent(context, HomeActivity.class);
//                context.startActivity(save_intent);
//            }
//        });

        viewHolder.food_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TextView text = (TextView) dialog.findViewById(R.id.foodServings);
                text.setText(foodData.get(viewHolder.getAdapterPosition()).getName());
                dialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.food_list_title.setText(foodData.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_pop_up);
                dialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return foodData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout food_list;
        TextView food_list_title;
//        ImageView overflow;

        public MyViewHolder(View itemView) {
            super(itemView);
            food_list = (RelativeLayout) itemView.findViewById(R.id.foodList);
            food_list_title = (TextView) itemView.findViewById(R.id.food);
        }
    }

    public void filterList(List<Food> filtered){
        foodData = filtered;
        notifyDataSetChanged();
    }


}
