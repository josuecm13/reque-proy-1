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
import musicbeans.entities.Sesion;

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
    public void onBindViewHolder(@NonNull final BandHolder holder, final int position) {
        holder.name.setText(bandList.get(position).getUsername());
        if(!admin){
            if(musicbeans.dataaccess.Band.validateFavBand(bandList.get(position).getName(), Sesion.getInstance().getUsername()))
                holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
            else
                holder.fav.setImageResource(R.drawable.ic_favorite_border_black_24dp_);
            holder.fav.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    changeState(holder,position);
                    return true;
                }
            });
        }else
            holder.fav.setImageResource(R.drawable.ic_delete_black_24dp);
    }

    private void changeState(BandHolder holder, int position) {
        if(!admin) {
            if (musicbeans.dataaccess.Band.validateFavBand(bandList.get(position).getName(), Sesion.getInstance().getUsername())) {
                musicbeans.dataaccess.Band.removeFavorite(bandList.get(position).getName(), Sesion.getInstance().getUsername());
                holder.fav.setImageResource(R.drawable.ic_favorite_border_black_24dp_);
            } else {
                musicbeans.dataaccess.Band.addFavorite(bandList.get(position).getName(), Sesion.getInstance().getUsername());
                holder.fav.setImageResource(R.drawable.ic_favorite_black_24dp);
            }
        }
        else{
            // TODO: Delete Band Account
        }
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
