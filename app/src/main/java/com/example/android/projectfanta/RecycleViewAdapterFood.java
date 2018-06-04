package com.example.android.projectfanta;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 5/17/2018.
 */
public class RecycleViewAdapterFood extends RecyclerView.Adapter<RecycleViewAdapterFood.MyViewHolder>{

    private List<Food> foodData = new ArrayList<>();
    private final Context context;
    private final Activity activity;
    Dialog dialog;
    EditText numberOfServing;
    Button button;
    String value;


    public RecycleViewAdapterFood(Activity activity, Context context, List<Food> foodData) {
        this.context = context;
        this.foodData = foodData;
        this.activity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View foodView = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list_layout,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(foodView);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_pop_up);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.food_list_title.setText(foodData.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_pop_up);

                TextView text = (TextView) dialog.findViewById(R.id.foodServings);
                text.setText(foodData.get(position).getName());

                //TODO: putting the number of servings to value

                dialog.show();

                numberOfServing = (EditText) dialog.findViewById(R.id.edit);
                button = (Button)dialog.findViewById(R.id.saving);
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){

                        value = numberOfServing.getText().toString();
                        if(value == null) {
                            return;
                        }
                        Double servings = new Double(value);
                        Intake newIntake = new Intake(foodData.get(position).getName(), servings);
                        Information.information.addIntake(context, newIntake);

                        InputMethodManager inputMethodManager =
                                (InputMethodManager) activity.getSystemService(
                                        Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(
                                activity.getCurrentFocus().getWindowToken(), 0);

                        dialog.dismiss();

                        //TODO the keyboard still doesnt actually go away
                    }
                });
            }
        });
    }
//    String value = numberOfServing.getText().toString();


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
