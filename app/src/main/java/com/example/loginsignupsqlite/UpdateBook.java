package com.example.loginsignupsqlite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class UpdateBook extends AppCompatActivity {

    EditText author_input, title_input, isbn_input;
    Button updateButton, deleteButton;
    RatingBar ratingBar;

    // variables to hold book details
    String id, author, title, isbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        author_input = findViewById(R.id.author_input2);
        title_input = findViewById(R.id.title_input2);
        isbn_input = findViewById(R.id.isbn_input2);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        ratingBar = findViewById(R.id.bookRatingBar2);

        // retrieve and set data from Intent
        getAndSetIntentData();

        // set actionbar title
        ActionBar ab = getSupportActionBar();
        if (ab!= null){
            ab.setTitle(title);
        }

        // update book method
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = title_input.getText().toString();
                author = author_input.getText().toString();
                isbn = isbn_input.getText().toString();
                float rating = ratingBar.getRating();

                //update book details in the db
                DBHelper DB = new DBHelper(UpdateBook.this);
                DB.updateData(id, title, author, isbn, rating);

            }
        });

        // delete book method
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

    }

    // method to retrieve and set book data from intent
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("author") &&
                getIntent().hasExtra("title") && getIntent().hasExtra("isbn") &&
                getIntent().hasExtra("rating")){
            // Get data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            isbn = getIntent().getStringExtra("isbn");
            float rating = getIntent().getFloatExtra("rating", 0);

            // Set Intent data
            title_input.setText(title);
            author_input.setText(author);
            isbn_input.setText(isbn);
            ratingBar.setRating(rating);

        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }


    // method to show a confirmation dialog before deleting a book
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Do you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper DB = new DBHelper(UpdateBook.this);
                DB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}