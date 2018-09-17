package com.example.admin.musicbeansapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.musicbeansapp.R;

import java.util.List;

import musicbeans.dataaccess.Status;
import musicbeans.entities.Band;
import musicbeans.entities.Client;
import musicbeans.entities.Sesion;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.BandHolder> {

    private List<Client> clientList;
    private View v;

    public ClientListAdapter(List<Client> clientList) {
        this.clientList = clientList;
    }


    @NonNull
    @Override
    public BandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_clientlist_item,null,false);
        this.v=view;
        return new BandHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BandHolder holder, final int position) {
        holder.name.setText(clientList.get(position).getUsername());

            holder.fav.setImageResource(R.drawable.ic_delete_black_24dp);
            holder.fav.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    changeState(holder,position);
                    return true;
                }
            });

    }

    private void changeState(BandHolder holder, int position) {

        Status status = musicbeans.dataaccess.Band.deleteBand(clientList.get(position));
        if (holder.itemView != null) {
            if (status == Status.NETWORK_ERROR)
                Toast.makeText(holder.itemView.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            if (status == Status.OK) {
                Toast.makeText(v.getContext(), "Se eliminó correctamente", Toast.LENGTH_SHORT).show();
                clientList.remove(position);
                notifyItemRemoved(position);
            }
            if(status==Status.FAILED){
                Toast.makeText(v.getContext(), "No se puedo eliminar", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class BandHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView fav;

        public BandHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.clientlist_item_name);
            fav = itemView.findViewById(R.id.clientlist_item_fav);
        }
    }

}
