package com.example.loginsignupsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageConfirmation extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_confirmation);
        Log.d("MessageConfirmation", "onCreate: Activity started.");

        imageView = findViewById(R.id.check);
        textView = findViewById(R.id.text_check);
        button = findViewById(R.id.button);

        Log.d("MessageConfirmation", "Views initialized.");

        // navigate to the home page
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MessageConfirmation", "Navigating to HomePage.");
                Intent intent = new Intent(MessageConfirmation.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}