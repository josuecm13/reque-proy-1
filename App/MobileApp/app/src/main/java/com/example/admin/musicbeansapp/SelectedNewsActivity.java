package com.example.admin.musicbeansapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import musicbeans.entities.NewsItem;

public class SelectedNewsActivity extends AppCompatActivity {

    NewsItem newsItem;
    TextView title;
    TextView body;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_news);



    }

}
