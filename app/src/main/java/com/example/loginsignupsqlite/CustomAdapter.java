package com.example.loginsignupsqlite;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter  extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private final Activity activity;
    private final ArrayList<Book> books;  // list of book objects
    Animation translate_anim;  // animation for item

    // constructor to initialize activity context and book list
    CustomAdapter(Activity activity, ArrayList<Book> books){
        this.activity = activity;
        this.books = books;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("CustomAdapter", "onCreateViewHolder called for viewType: " + viewType);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.book_row, parent, false);
        return new MyViewHolder(view);
    }


    // bind data to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder,final int position) {
        Book currentBook = books.get(position);
        Log.d("CustomAdapter", "Binding data for position: " + position + " | Title: " + currentBook.getTitle());
        holder.book_title_txt.setText(currentBook.getTitle());
        holder.book_author_txt.setText(currentBook.getAuthor());
        holder.book_isbn_txt.setText(currentBook.getIsbn());
        holder.bookRatingBar.setRating(currentBook.getRating());
        holder.bookImage.setImageResource(R.drawable.book_img);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UpdateBook.class); // intent to navigate to the UpdateBook activity
                intent.putExtra("id", String.valueOf(currentBook.getId()));
                intent.putExtra("title", currentBook.getTitle());
                intent.putExtra("author", currentBook.getAuthor());
                intent.putExtra("isbn", currentBook.getIsbn());
                intent.putExtra("rating", String.valueOf(currentBook.getRating()));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    // return the total numbers of item
    @Override
    public int getItemCount(){
        Log.d("CustomAdapter", "Total items count: " + books.size());
        return books.size();
    }

    // ViewHolder class to hold references to the view
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_title_txt, book_author_txt, book_isbn_txt;

        ImageView bookImage;
        LinearLayout mainLayout;
        RatingBar bookRatingBar;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("CustomAdapter", "ViewHolder created");
            bookImage = itemView.findViewById(R.id.book_image);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_isbn_txt = itemView.findViewById(R.id.book_isbn_txt);
            bookRatingBar = itemView.findViewById(R.id.bookRatingBar);


            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            translate_anim = AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
