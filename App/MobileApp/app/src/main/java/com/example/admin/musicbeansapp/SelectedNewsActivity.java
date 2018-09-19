package com.example.admin.musicbeansapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.musicbeansapp.adapters.CommentAdapter;

import java.sql.Date;
import java.util.List;

import musicbeans.dataaccess.ImageManager;
import musicbeans.dataaccess.Status;
import musicbeans.entities.Comment;
import musicbeans.entities.NewsItem;
import musicbeans.entities.Sesion;

public class SelectedNewsActivity extends AppCompatActivity {


    String title;
    String body;
    Date date;
    String author;

    TextView tvTitle;
    TextView tvbody;
    ImageView imageView;
    EditText commentArea;
    Button submitButton;
    NewsItem newsItem;

    List<Comment> commentList;
    RecyclerView recyclerView;

    CommentAdapter adapter;

    private boolean commentempty(String string){
        for (int i = 0; i < string.length() ; i++) {
            if (string.charAt(i) != ' '){
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_news);

        tvTitle = findViewById(R.id.selected_news_title);
        tvbody = findViewById(R.id.selected_news_body);
        imageView = findViewById(R.id.selected_news_image);

        commentArea = findViewById(R.id.text_section);
        submitButton = findViewById(R.id.comment_submit);
        //commentArea.setFocusedByDefault(false);
        commentArea.setFocusableInTouchMode(true);

        if (Sesion.getInstance().getAccounType() != Status.CLIENT){
            commentArea.setClickable(false);
            commentArea.setFocusable(false);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = commentArea.getText().toString();
                if (!commentempty(text))
                    if (musicbeans.dataaccess.Comment.insertComment(text, Sesion.getInstance().getUsername(),newsItem) == Status.OK){
                        commentArea.setText("");
                        adapter.update();
                    }
            }
        });


        title = getIntent().getExtras().getString("Title");
        body =  getIntent().getExtras().getString("Body");
        date = new Date(getIntent().getExtras().getLong("Date"));
        author = getIntent().getExtras().getString("Author");

        newsItem = new NewsItem(title,body,null,author,date);

        ImageManager manager = new ImageManager("news/"+newsItem.getImageID(),imageView);
        manager.execute();

        tvTitle.setText(title);
        tvbody.setText(body);

        recyclerView =  findViewById(R.id.comment_section);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        commentList = musicbeans.dataaccess.Comment.getComments(newsItem);

        adapter = new CommentAdapter(commentList,newsItem);
        recyclerView.setAdapter(adapter);


    }

}
