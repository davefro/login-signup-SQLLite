package com.example.loginsignupsqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileFragment extends Fragment {

    EditText profileEmail, profilePassword;
    Button updateProfileBtn, deleteProfileBtn;
    TextView logoutBtn;
    DBHelper DB;
    ImageView imageView;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("ProfileFragment", "onCreateView: Starting to create view.");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileEmail = view.findViewById(R.id.profile_email);
        profilePassword = view.findViewById(R.id.profile_password);
        updateProfileBtn = view.findViewById(R.id.updateProfile);
        deleteProfileBtn = view.findViewById(R.id.deleteProfile);
        logoutBtn = view.findViewById(R.id.logout_btn);
        imageView = view.findViewById(R.id.profilePict);
        DB = new DBHelper(getContext());

        Log.d("ProfileFragment", "onCreateView: Views initialized.");

        // update profile
        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ProfileFragment", "updateProfile: Clicked.");
                updateProfile();
            }
        });

        // delete profile
        deleteProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ProfileFragment", "deleteProfile: Clicked.");
                deleteProfile();
            }
        });

        // logout
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ProfileFragment", "logoutUser: Clicked.");
                logoutUser();
            }
        });

        return view;
    }

    // update profile method
    private void updateProfile() {
        Log.d("ProfileFragment", "updateProfile: Starting profile update.");
        String newUserEmail = profileEmail.getText().toString().trim();
        String newUserPassword = profilePassword.getText().toString().trim();

        // patterns for validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern = "^(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";

        if (newUserEmail.isEmpty() || newUserPassword.isEmpty()) {
            Toast.makeText(getContext(), "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!newUserEmail.matches(emailPattern)) {
            Toast.makeText(getContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        } else if (!newUserPassword.matches(passwordPattern)) {
            Toast.makeText(getContext(), "Password needs at least 8 characters, including a capital letter and a special character", Toast.LENGTH_LONG).show();
        } else {
            String currentUserEmail = DB.getCurrentUserEmail();
            if (currentUserEmail.equals(newUserEmail) || !DB.checkUserEmail(newUserEmail)) {
                if (DB.updateUserDetails(currentUserEmail, newUserEmail, newUserPassword)) {
                    Toast.makeText(getContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Profile Update Failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "This email is already used by another account", Toast.LENGTH_SHORT).show();
            }
        }
        Log.d("ProfileFragment", "updateProfile: Profile update attempted.");
    }

    // delete profile method with alert dialog
    private void deleteProfile() {
        Log.d("ProfileFragment", "deleteProfile: Deletion dialog displayed.");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete your profile? This action cannot be undone.");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String currentUserEmail = DB.getCurrentUserEmail();
                if (DB.deleteUserByEmail(currentUserEmail)) {
                    Toast.makeText(getContext(), "Profile Deleted Successfully", Toast.LENGTH_SHORT).show();
                    logoutUser();
                } else {
                    Toast.makeText(getContext(), "Profile Deletion Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // logout user
    private void logoutUser() {
        Log.d("ProfileFragment", "logoutUser: Logging out.");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
        Log.d("ProfileFragment", "logoutUser: User logged out.");
    }
}



