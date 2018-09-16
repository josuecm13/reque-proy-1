package com.example.admin.musicbeansapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.musicbeansapp.R;

import java.util.List;

import musicbeans.entities.Band;

public class BandListAdapter extends RecyclerView.Adapter<BandListAdapter.BandHolder> {

    private List<Band> bandList;
    private boolean admin=false;

    public BandListAdapter(List<Band> bandList) {
        this.bandList = bandList;
    }

    public BandListAdapter(List<Band> bandList,boolean admin){this.bandList=bandList;this.admin=admin;}

    @NonNull
    @Override
    public BandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_bandlist_item,null,false);
        return new BandHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BandHolder holder, int position) {
        holder.name.setText(bandList.get(position).getName());
        if(!admin)
            holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        else
            holder.fav.setImageResource(R.drawable.ic_delete_black_24dp);
    }

    @Override
    public int getItemCount() {
        return bandList.size();
    }

    public class BandHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView fav;

        public BandHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.bandlist_item_name);
            fav = itemView.findViewById(R.id.bandlist_item_fav);
        }
    }

}
