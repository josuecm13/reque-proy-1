package com.example.admin.musicbeansapp.ui.posts.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.musicbeansapp.R;
import com.example.admin.musicbeansapp.adapters.PostAdapter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import musicbeans.entities.Account;
import musicbeans.entities.Event;
import musicbeans.entities.NewsItem;

import static com.example.admin.musicbeansapp.R.id.recycler_view;

public class PostFragment extends Fragment {


    private RecyclerView recyclerView;
    PostAdapter adapter;
    List<NewsItem> newsItemList;
    List<Event> eventList;
    List<Object> posts;


    public PostFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts,container,false);
        newsItemList = new ArrayList<>();
        eventList = new ArrayList<>();
        posts = new ArrayList<>();
        recyclerView =  view.findViewById(recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fillNewItems();
        fillEventItems();
        fillposts();
        adapter = new PostAdapter(getContext(), posts);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void fillNewItems(){
        newsItemList.add(new NewsItem("Hola Mundo", "string body", new byte[45], null,null));
        newsItemList.add(new NewsItem("Hola Mundo2", "string body", new byte[45], null,null));
        newsItemList.add(new NewsItem("Hola Mundo3", "string body", new byte[45], null,null));
    }

    private void fillEventItems(){
        eventList.add(new Event(null,"lool","Band","something"));
        eventList.add(new Event(null,"lool1","Band","something"));
        eventList.add(new Event(null,"lool2","Band","something"));
    }

    private void fillposts(){
        posts.addAll(newsItemList);
        posts.addAll(eventList);
        Collections.shuffle(posts);
    }

}
