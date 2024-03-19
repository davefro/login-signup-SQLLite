package com.example.loginsignupsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ResetPassword extends AppCompatActivity {

    TextInputEditText email, password2, password3;
    TextInputLayout emailInputLayout, password2InputLayout, password3InputLayout;
    Button confirm_btn;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Log.d("ResetPassword", "onCreate: Activity started.");

        email = findViewById(R.id.email);
        password2 = findViewById(R.id.password2);
        password3 = findViewById(R.id.password3);
        confirm_btn = findViewById(R.id.confirm_btn);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        password2InputLayout = findViewById(R.id.password2InputLayout);
        password3InputLayout = findViewById(R.id.password3InputLayout);
        DB = new DBHelper(this);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = email.getText().toString();
                String pass2 = password2.getText().toString();
                String pass3 = password3.getText().toString();
                Log.d("ResetPassword", "Attempting to reset password for Email: " + user);


                String passwordPattern = "^(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";

                // validations for empty fields
                if (user.isEmpty()) {
                    emailInputLayout.setError("Email cannot be empty");
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                    emailInputLayout.setError("Invalid email address");
                    return;
                }
                if (!pass2.matches(passwordPattern)) {
                    password2InputLayout.setError("Password must be at least 8 characters, including a capital letter and a special character");
                    return;
                }
                if (!pass2.equals(pass3)) {
                    password3InputLayout.setError("Passwords do not match");
                    return;
                }

                resetUserPassword(user, pass2);
            }
        });
    }

    // method to reset user password
    private void resetUserPassword(String user, String newPass){
        Log.d("ResetPassword", "resetUserPassword: Starting password reset process.");
        boolean result = DB.resetUserPassword(user, newPass);
        if(result){
            Log.d("ResetPassword", "Password reset successfully for Email: " + user);
            Toast.makeText(ResetPassword.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Log.e("ResetPassword", "Failed to reset password for Email: " + user + ". User not found.");
            Toast.makeText(ResetPassword.this, "Failed to reset password. User not found.", Toast.LENGTH_SHORT).show();
        }
    }
}