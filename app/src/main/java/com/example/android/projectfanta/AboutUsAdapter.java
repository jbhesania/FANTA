package com.example.android.projectfanta;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.battleent.ribbonviews.RibbonLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 5/18/2018.
 */


class CustomViewHolder extends RecyclerView.ViewHolder{

    RibbonLayout ribbonLayout;
    ImageView imageView;

    public CustomViewHolder(View itemView){
        super(itemView);

        ribbonLayout = (RibbonLayout)itemView.findViewById(R.id.ribbonAbout);
        imageView = (ImageView)itemView.findViewById(R.id.imageView);
    }

}

public class AboutUsAdapter extends RecyclerView.Adapter<CustomViewHolder>{
    Context context;
    List<AboutUsItem> itemList;

    public AboutUsAdapter(Context context, List<AboutUsItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.aboutus_item,parent,false);


        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        AboutUsItem item = itemList.get(position);
        holder.ribbonLayout.setShowBottom(true);
        holder.ribbonLayout.setShowBottom(true);

        holder.ribbonLayout.setHeaderRibbonColor(Color.parseColor("#2B323A"));
        holder.ribbonLayout.setHeaderTextColor(Color.parseColor("#FFFFFF"));


        holder.ribbonLayout.setHeaderText(item.header);
        holder.ribbonLayout.setBottomText(item.footer);
        Picasso.with(context).load(item.image)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }
}
