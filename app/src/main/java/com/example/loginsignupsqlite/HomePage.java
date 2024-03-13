package com.example.loginsignupsqlite;

import androidx.annotation.NonNull;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageButton searchButton;

    ProfileFragment profileFragment;
    LibraryFragment libraryFragment;
    NotificationsFragment notificationsFragment;
    MessageFragment messageFragment;
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        profileFragment = new ProfileFragment();
        libraryFragment = new LibraryFragment();
        notificationsFragment = new NotificationsFragment();
        messageFragment = new MessageFragment();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        searchButton = findViewById(R.id.main_search_btn);
        textView = findViewById(R.id.Welcome);

        DBHelper DB = new DBHelper(this);
        String userEmail = DB.getCurrentUserEmail();

        TextView userEmailTextView = findViewById(R.id.userEmailTextView);
        userEmailTextView.setText(userEmail);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, SearchBook.class));
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.menu_profile){
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, profileFragment).commit();
                    }
                    if(item.getItemId() == R.id.menu_library){
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, libraryFragment).commit();
                    }
                    if(item.getItemId() == R.id.menu_notifications){
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, notificationsFragment).commit();
                    }
                    if(item.getItemId() == R.id.menu_message){
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, messageFragment).commit();
                    }
                    if(item.getItemId() == R.id.menu_notifications){
                        resetNotificationCount();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, notificationsFragment).commit();
                    }
                return true;
            }
        });

        Intent intent = getIntent();
        if (intent != null && "notifications".equals(intent.getStringExtra("navigateTo"))){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new NotificationsFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.menu_notifications);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBadgeCount();
    }

    private void updateBadgeCount(){
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int currentCount = sharedPreferences.getInt("notificationCount", 0);

        if(currentCount > 0){
            BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.menu_notifications);
            badgeDrawable.setNumber(currentCount);
            badgeDrawable.setVisible(true);
        }else{
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.removeBadge(R.id.menu_notifications);
        }
    }

    private void resetNotificationCount(){
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("notificationCount", 0);
        myEdit.apply();
        bottomNavigationView.removeBadge(R.id.menu_notifications);
    }

}