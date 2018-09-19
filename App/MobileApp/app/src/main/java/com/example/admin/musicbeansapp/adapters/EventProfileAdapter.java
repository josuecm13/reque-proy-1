package com.example.admin.musicbeansapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.musicbeansapp.R;
import com.example.admin.musicbeansapp.ui.bands.DialogEvent;


import java.util.List;

import musicbeans.dataaccess.Status;
import musicbeans.entities.Event;
import musicbeans.entities.Sesion;

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
    public void onBindViewHolder(final MyViewHolder holder, final int postition)
    {
        holder.title.setText(mData.get(postition).getTitle());
        holder.date.setText("Fecha: "+mData.get(postition).getDate().toString());
        holder.time.setText("Hora: "+mData.get(postition).getTitle());
        holder.location.setText("Lugar: "+mData.get(postition).getLocation());
        if(Sesion.getInstance().getAccounType()== Status.CLIENT)
        {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogEvent dialogEvent = new DialogEvent();
                    dialogEvent.setEvent(mData.get(postition));
                    dialogEvent.show(((AppCompatActivity)mContext).getSupportFragmentManager(),"event");
                }
            });
        }
        else
        {
            holder.delete.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    delete(holder,postition);
                    return true;
                }
            });
        }
    }
    public void delete(EventProfileAdapter.MyViewHolder holder, int position)
    {
        Status status = musicbeans.dataaccess.Posts.deleteEvent(mData.get(position));
        if (holder.itemView != null) {
            if (status == Status.NETWORK_ERROR)
                Toast.makeText(holder.itemView.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            if (status == Status.OK) {
                Toast.makeText(holder.itemView.getContext(), "Se eliminó correctamente", Toast.LENGTH_SHORT).show();
                mData.remove(position);
                notifyItemRemoved(position);
            }
            if(status==Status.FAILED){
                Toast.makeText(holder.itemView.getContext(), "No se puedo eliminar", Toast.LENGTH_SHORT).show();
            }
        }
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
        ImageView delete;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.title);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            location = (TextView)itemView.findViewById(R.id.location);
            cardView = (CardView)itemView.findViewById(R.id.card_view_id);
            delete = itemView.findViewById(R.id.deleteEvent);
        }
    }
}
