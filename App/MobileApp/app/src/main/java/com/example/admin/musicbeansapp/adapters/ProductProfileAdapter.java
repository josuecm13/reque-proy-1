package com.example.admin.musicbeansapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.musicbeansapp.R;

import java.util.List;

import musicbeans.dataaccess.ImageManager;
import musicbeans.dataaccess.Status;
import musicbeans.entities.Event;
import musicbeans.entities.Product;

public class ProductProfileAdapter extends RecyclerView.Adapter<ProductProfileAdapter.MyViewHolder>{
    private Context mContext;
    private List<Product> mData;
    private View v;
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
        this.v=view;
        return new MyViewHolder(view);

    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder,final int postition)
    {
        holder.name.setText(mData.get(postition).getName());
        holder.price.setText("$"+mData.get(postition).getPrice());
        ImageManager manager = new ImageManager("img/"+mData.get(postition).getID(),holder.photo);
        manager.execute();
        holder.delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                delete(holder,postition);
                return true;
            }
        });

    }
    public void delete(MyViewHolder holder,int position)
    {
        Status status = musicbeans.dataaccess.Product.deleteProduct(mData.get(position));
        if (holder.itemView != null) {
            if (status == Status.NETWORK_ERROR)
                Toast.makeText(holder.itemView.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            if (status == Status.OK) {
                Toast.makeText(v.getContext(), "Se eliminó correctamente", Toast.LENGTH_SHORT).show();
                mData.remove(position);
                notifyItemRemoved(position);
            }
            if(status==Status.FAILED){
                Toast.makeText(v.getContext(), "No se puedo eliminar", Toast.LENGTH_SHORT).show();
            }
        }
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
        ImageView delete;
        ImageView edit;
        CardView cardView;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.imgPhoto);
            name = (TextView)itemView.findViewById(R.id.name);
            price = (TextView)itemView.findViewById(R.id.price);
            delete =(ImageView)itemView.findViewById(R.id.removeProduct);
            edit = (ImageView)itemView.findViewById(R.id.editProduct);
            cardView = (CardView)itemView.findViewById(R.id.card_view_id);
        }
    }
}
