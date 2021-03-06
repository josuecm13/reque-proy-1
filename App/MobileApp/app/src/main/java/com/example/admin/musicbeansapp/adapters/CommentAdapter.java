package com.example.admin.musicbeansapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.musicbeansapp.R;

import java.util.List;

import musicbeans.entities.Comment;
import musicbeans.entities.NewsItem;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder>{

    List<Comment> commentList;
    NewsItem item;

    public CommentAdapter(List<Comment> commentList){
        this.commentList =commentList;
    }

    public CommentAdapter(List<Comment> commentList, NewsItem item) {
        this.commentList = commentList;
        this.item = item;
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout,null,false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        holder.username.setText(commentList.get(position).getUsername());
        holder.comment.setText(commentList.get(position).getComment());
    }

    public void update(){
        if (item != null){
            commentList = musicbeans.dataaccess.Comment.getComments(item);
            notifyDataSetChanged();
            notifyItemInserted(getItemCount()+1);
        }
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView comment;

        public CommentHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.comment_username);
            comment = itemView.findViewById(R.id.comment_comment);
        }
    }


}
