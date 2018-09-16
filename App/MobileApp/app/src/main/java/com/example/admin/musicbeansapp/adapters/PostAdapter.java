package com.example.admin.musicbeansapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.musicbeansapp.R;
import com.example.admin.musicbeansapp.ui.posts.fragments.PostFragment;

import java.util.List;

import musicbeans.entities.Event;
import musicbeans.entities.NewsItem;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "PostAdapter";
    private Context context;
    private List<Object> posts;

    public PostAdapter(Context context, List<Object> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final RecyclerView.ViewHolder holder;
        View view;
        switch (viewType){
            case R.layout.layout_postitem_new:
                view = LayoutInflater
                        .from(context).inflate(R.layout.layout_postitem_new, parent, false);
                holder = new PostNewHolder(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: Open a News Article
                    }
                });
                break;
            default:
                view = LayoutInflater
                        .from(context).inflate(R.layout.layout_postitem_event, parent, false);
                holder = new PostEventHolder(view);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO: Open a Event
                    }
                });
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position){
        return posts.get(position) instanceof NewsItem? R.layout.layout_postitem_new : R.layout.layout_postitem_event;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof PostNewHolder){
            final NewsItem newsItem = (NewsItem) posts.get(position);
            ((PostNewHolder)holder).title.setText(newsItem.getTitle());
            ((PostNewHolder)holder).body.setText(newsItem.getBody());
            ((PostNewHolder)holder).date.setText(newsItem.getDate().toString());
        }else{
            final Event event = (Event) posts.get(position);
            ((PostEventHolder)holder).event.setText(event.getTitle());
            ((PostEventHolder)holder).info.setText(event.getLocation()+","+event.getDate().toString());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PostNewHolder extends RecyclerView.ViewHolder{

        public ImageView thumbnail;
        public TextView title;
        public TextView body;
        public TextView date;

        public PostNewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.post_newsitem_thumbnail);
            title = itemView.findViewById(R.id.post_newsitem_title);
            body = itemView.findViewById(R.id.post_newsitem_body);
        }
    }


    public class PostEventHolder extends RecyclerView.ViewHolder{

        public TextView event;
        public TextView band;
        public TextView info;


        public PostEventHolder(View itemView) {
            super(itemView);
            event = itemView.findViewById(R.id.post_item_title);
            band = itemView.findViewById(R.id.post_item_band);
            info = itemView.findViewById(R.id.post_item_body);
        }
    }

}
