package com.example.loginsignupsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItems extends AppCompatActivity {

    EditText author_input, title_input, isbn_input;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        author_input = findViewById(R.id.author_input);
        title_input = findViewById(R.id.title_input);
        isbn_input = findViewById(R.id.isbn_input);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper DB = new DBHelper(AddItems.this);
                DB.addBook(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        isbn_input.getText().toString().trim());
            }
        });

    }
}