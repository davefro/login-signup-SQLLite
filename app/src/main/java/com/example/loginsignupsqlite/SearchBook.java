package com.example.loginsignupsqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class SearchBook extends AppCompatActivity {

    EditText searchInput;
    ImageButton searchButton, backButton;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        searchInput = findViewById(R.id.search_bookName_input);
        searchButton = findViewById(R.id.search_book_btn);
        backButton = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.search_book_recycler_view);

        searchInput.requestFocus();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchInput.getText().toString();
                if(searchTerm.isEmpty() || searchTerm.length() < 3){
                    searchInput.setError("Invalid Book name");
                }
                setupSearchRecyclerView(searchTerm);
            }
        });
    }

    // search method to find a book in db
    void setupSearchRecyclerView(String searchTerm){
        DBHelper dbHelper = new DBHelper(SearchBook.this);
        ArrayList<Book> books = dbHelper.searchBooks(searchTerm);

        CustomAdapter customAdapter = new CustomAdapter(SearchBook.this, books);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);
    }
}