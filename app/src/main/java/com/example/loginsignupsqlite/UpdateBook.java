package com.example.loginsignupsqlite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
        Log.d("UpdateBook", "onCreate: Activity started.");

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
                Log.d("UpdateBook", "Update button clicked.");
                title = title_input.getText().toString();
                author = author_input.getText().toString();
                isbn = isbn_input.getText().toString();
                float rating = ratingBar.getRating();

                // validate inputs for a minimum of 3 characters
                if (title.isEmpty() || title.length() < 3) {
                    title_input.setError("Title must be at least 3 characters long");
                    Log.d("UpdateBook", "Invalid title: " + title);
                    return;
                }
                if (author.isEmpty() || author.length() < 3) {
                    author_input.setError("Author must be at least 3 characters long");
                    Log.d("UpdateBook", "Invalid author: " + author);
                    return;
                }
                if (isbn.isEmpty() || isbn.length() < 5) {
                    isbn_input.setError("Review must be at least 5 characters");
                    Log.d("UpdateBook", "Invalid ISBN: " + isbn);
                    return;
                }

                //update book details in the db
                Log.d("UpdateBook", "Attempting to update book with ID: " + id);
                DBHelper DB = new DBHelper(UpdateBook.this);
                DB.updateData(id, title, author, isbn, rating);
                Log.d("UpdateBook", "Book updated successfully.");
            }
        });

        // delete book method
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UpdateBook", "Delete button clicked.");
                confirmDialog();
            }
        });

    }

    // method to retrieve and set book data from intent
    void getAndSetIntentData(){
        Log.d("UpdateBook", "getAndSetIntentData: Retrieving book details from intent.");
        if(getIntent().hasExtra("id") && getIntent().hasExtra("author") &&
                getIntent().hasExtra("title") && getIntent().hasExtra("isbn") &&
                getIntent().hasExtra("rating")){
            // get data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            isbn = getIntent().getStringExtra("isbn");
            float rating = getIntent().getFloatExtra("rating", 0);

            // set intent data
            title_input.setText(title);
            author_input.setText(author);
            isbn_input.setText(isbn);
            ratingBar.setRating(rating);
            Log.d("UpdateBook", "Book details retrieved successfully.");

        }else{
            Log.e("UpdateBook", "No data to display.");
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }


    // method to show a confirmation dialog before deleting a book
    void confirmDialog(){
        Log.d("UpdateBook", "confirmDialog: Showing delete confirmation dialog.");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Do you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("UpdateBook", "Confirm deletion: Yes clicked.");
                DBHelper DB = new DBHelper(UpdateBook.this);
                DB.deleteOneRow(id);
                Log.d("UpdateBook", "Book with ID: " + id + " deleted successfully.");
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