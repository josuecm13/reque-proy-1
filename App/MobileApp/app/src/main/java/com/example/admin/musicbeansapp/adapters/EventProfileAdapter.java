package com.example.admin.musicbeansapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.admin.musicbeansapp.R;


import java.util.List;

import musicbeans.entities.Event;

public class EventProfileAdapter extends RecyclerView.Adapter<EventProfileAdapter.MyViewHolder>{
    private Context mContext;
    private List<Event> mData;

    public EventProfileAdapter(Context mContext, List<Event> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.layout_event_profile_item,parent,false);
        return new MyViewHolder(view);

    }
    @Override
    public void onBindViewHolder(MyViewHolder holder,final int postition)
    {
        holder.title.setText(mData.get(postition).getTitle());
        holder.date.setText("Fecha: "+mData.get(postition).getDate().toString());
        holder.time.setText("Hora: "+mData.get(postition).getTitle());
        holder.location.setText("Lugar: "+mData.get(postition).getLocation());
    }
    @Override
    public int getItemCount(){
        return mData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView date;
        TextView time;
        TextView location;
        CardView cardView;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            location = (TextView)itemView.findViewById(R.id.location);
            cardView = (CardView)itemView.findViewById(R.id.card_view_id);
        }
    }
}
