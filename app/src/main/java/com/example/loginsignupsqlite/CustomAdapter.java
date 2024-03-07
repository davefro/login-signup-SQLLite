package com.example.loginsignupsqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Activity activity;
    private ArrayList book_id, book_title, book_author, book_isbn, book_ratings;
    Animation translate_anim;
    CustomAdapter(Activity activity, ArrayList book_id, ArrayList book_title, ArrayList book_author,
                  ArrayList book_isbn, ArrayList book_ratings){
        this.activity = activity;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_isbn = book_isbn;
        this.book_ratings = book_ratings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.book_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder,final int position) {
        holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_isbn_txt.setText(String.valueOf(book_isbn.get(position)));
        float rating = Float.parseFloat(book_ratings.get(position).toString());

        holder.bookRatingBar.setRating(rating);
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UpdateBook.class);
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("isbn", String.valueOf(book_isbn.get(position)));
                intent.putExtra("rating", String.valueOf(book_ratings.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_id_txt, book_title_txt, book_author_txt, book_isbn_txt;
        LinearLayout mainLayout;
        RatingBar bookRatingBar;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_isbn_txt = itemView.findViewById(R.id.book_isbn_txt);
            bookRatingBar = itemView.findViewById(R.id.bookRatingBar);


            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }
}
