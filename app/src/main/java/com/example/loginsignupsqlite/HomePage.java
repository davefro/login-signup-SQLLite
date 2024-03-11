package com.example.loginsignupsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

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
                return true;
            }
        });
    }
}