package com.example.loginsignupsqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;



public class AddItems extends AppCompatActivity {

    EditText author_input, title_input, isbn_input;
    Button addButton;

    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        author_input = findViewById(R.id.author_input);
        title_input = findViewById(R.id.title_input);
        isbn_input = findViewById(R.id.isbn_input);
        addButton = findViewById(R.id.addButton);
        ratingBar = findViewById(R.id.bookRatingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });

        // check version, permission and request permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(AddItems.this,
                    android.Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(AddItems.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper DB = new DBHelper(AddItems.this);
                float rating = ratingBar.getRating();
                DB.addBook(title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        isbn_input.getText().toString().trim(),
                        rating);

                makeNotification();
            }
        });
    }

    // create a notification method
    public void makeNotification(){
        String chanelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(),chanelID );
        builder.setSmallIcon(R.drawable.notifications_icon)
                .setContentTitle("Book Notifications")
                .setContentText("New Book has been added")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(), HomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("navigateTo", "notifications");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    notificationManager.getNotificationChannel(chanelID);
            if (notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(chanelID,
                        "Some description", importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        incrementNotificationCount();
        notificationManager.notify(0,builder.build());
    }

    // method to track the number of notifications incrementing the count each time it is invoked
    private void incrementNotificationCount() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        int currentCount = sharedPreferences.getInt("notificationCount", 0);
        currentCount++;
        myEdit.putInt("notificationCount", currentCount);
        myEdit.apply();
    }
}