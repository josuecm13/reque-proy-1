package com.example.admin.musicbeansapp.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.musicbeansapp.R;
import com.example.admin.musicbeansapp.SelectedNewsActivity;
import com.example.admin.musicbeansapp.UserLoginActivity;

import java.util.List;

import musicbeans.entities.Event;
import musicbeans.entities.NewsItem;
import musicbeans.entities.Posts;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = "PostAdapter";
    private List<Posts> posts;

    public PostAdapter(List<Posts> posts) {
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
                        .from(parent.getContext()).inflate(R.layout.layout_postitem_new, parent, false);
                holder = new PostNewHolder(view);
                break;
            default:
                view = LayoutInflater
                        .from(parent.getContext()).inflate(R.layout.layout_postitem_event,parent, false);
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

    private void openNews(View view, NewsItem newsItem){
        Bundle bundle = new Bundle();
        bundle.putString("Title",newsItem.getTitle());
        bundle.putString("Body",newsItem.getBody());
        bundle.putString("Author",newsItem.getAuthor());
        bundle.putLong("Date",newsItem.getDate().getTime());
        Intent intent = new Intent(view.getContext(),
                SelectedNewsActivity.class).putExtras(bundle);
        view.getContext().startActivity(intent);
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
            String date = newsItem.getDate() != null ? newsItem.getDate().toString(): "12/21/2121";
            ((PostNewHolder)holder).date.setText(date);
            ((PostNewHolder)holder).delete.setVisibility(View.INVISIBLE);
            //((PostNewHolder)holder).thumbnail.setImageResource(R.drawable.logo);
            ((PostNewHolder)holder).setNewsListener(newsItem);
        }else{
            final Event event = (Event) posts.get(position);
            ((PostEventHolder)holder).event.setText(event.getTitle());
            ((PostEventHolder)holder).band.setText(event.getBanda());
            ((PostEventHolder)holder).info.setText((event.getLocation() != null? event.getLocation() : "N/D")+","+ (event.getDate() != null? event.getDate().toString() : "N/D"));
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostNewHolder extends RecyclerView.ViewHolder{

        public ImageView thumbnail;
        public TextView title;
        public TextView body;
        public TextView date;
        public  ImageView delete;
        public PostNewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.post_newsitem_thumbnail);
            title = itemView.findViewById(R.id.post_newsitem_title);
            body = itemView.findViewById(R.id.post_newsitem_body);
            date = itemView.findViewById(R.id.post_newsitem_time);
            delete = itemView.findViewById(R.id.deleteNews);
        }


        public void setNewsListener(final NewsItem news){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openNews(view,news);
                }
            });
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
