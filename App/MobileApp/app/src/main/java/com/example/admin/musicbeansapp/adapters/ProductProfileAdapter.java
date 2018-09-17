package com.example.admin.musicbeansapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.musicbeansapp.R;

import java.util.List;

import musicbeans.entities.Event;
import musicbeans.entities.Product;

public class ProductProfileAdapter extends RecyclerView.Adapter<ProductProfileAdapter.MyViewHolder>{
    private Context mContext;
    private List<Product> mData;

    public ProductProfileAdapter(Context mContext, List<Product> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.layout_product_profile_item,parent,false);
        return new MyViewHolder(view);

    }
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int postition)
    {
        holder.name.setText(mData.get(postition).getName());
        holder.price.setText("$"+mData.get(postition).getPrice());
    }
    @Override
    public int getItemCount(){
        return mData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView photo;
        TextView name;
        TextView price;
        CardView cardView;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.imgPhoto);
            name = (TextView)itemView.findViewById(R.id.name);
            price = (TextView)itemView.findViewById(R.id.price);
            cardView = (CardView)itemView.findViewById(R.id.card_view_id);
        }
    }
}
