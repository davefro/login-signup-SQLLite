package com.example.loginsignupsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomePage extends AppCompatActivity {

    // variables for ui components and fragments
    BottomNavigationView bottomNavigationView;
    ImageButton searchButton;

    // fragments for different section
    ProfileFragment profileFragment;
    LibraryFragment libraryFragment;
    NotificationsFragment notificationsFragment;
    MessageFragment messageFragment;
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Log.d("HomePage", "onCreate: Activity created.");

        // initialize fragments
        profileFragment = new ProfileFragment();
        libraryFragment = new LibraryFragment();
        notificationsFragment = new NotificationsFragment();
        messageFragment = new MessageFragment();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        searchButton = findViewById(R.id.main_search_btn);
        textView = findViewById(R.id.Welcome);

        // get user email from db and display it
        DBHelper DB = new DBHelper(this);
        String userEmail = DB.getCurrentUserEmail();
        TextView userEmailTextView = findViewById(R.id.userEmailTextView);
        userEmailTextView.setText(userEmail);

        // navigate to the search book activity
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, SearchBook.class));
            }
        });

        // item navigation between fragments
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("HomePage", "onNavigationItemSelected: " + item.getTitle());
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

        // intent from notification click
        Intent intent = getIntent();
        if (intent != null && "notifications".equals(intent.getStringExtra("navigateTo"))){
            Log.d("HomePage", "onCreate: Navigating to NotificationsFragment due to intent.");
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new NotificationsFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.menu_notifications);
        }
        Log.d("HomePage", "onCreate: Fragments initialized and listeners set.");
    }

    // update notification badge count
    @Override
    protected void onResume() {
        super.onResume();
        updateBadgeCount();
    }

    // update the badge count for notifications based on stored sharedPreferences
    private void updateBadgeCount(){
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        int currentCount = sharedPreferences.getInt("notificationCount", 0);
        Log.d("HomePage", "updateBadgeCount: Current count is " + currentCount);

        if(currentCount > 0){
            BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.menu_notifications);
            badgeDrawable.setNumber(currentCount);
            badgeDrawable.setVisible(true);
        }else{
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            bottomNavigationView.removeBadge(R.id.menu_notifications);
        }
    }

    // reset the notification count to 0 and remove the badge
    private void resetNotificationCount(){
        Log.d("HomePage", "resetNotificationCount: Resetting notification count to 0.");
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putInt("notificationCount", 0);
        myEdit.apply();
        bottomNavigationView.removeBadge(R.id.menu_notifications);
    }

}