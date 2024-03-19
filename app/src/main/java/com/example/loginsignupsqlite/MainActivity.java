package com.example.loginsignupsqlite;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    // declare ui components and db
    TextInputEditText email, password;
    TextInputLayout emailInputLayout, passwordInputLayout;
    Button signIn;
    TextView signUp, forgotPassword;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate: Activity created.");

        // initialize ui components and db
        forgotPassword = findViewById(R.id.forgotPassword);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        signIn = findViewById(R.id.sign_in);
        signUp = findViewById(R.id.sign_up);
        DB = new DBHelper(this);

        // navigate to the RegisterPage
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Navigating to RegisterPage.");
                Intent intent = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(intent);
            }
        });

        // navigate to the forgotPassword
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Navigating to ResetPassword.");
                Intent intent = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(intent);
            }
        });

        //  sign in button
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                // reset errors at the beginning
                emailInputLayout.setError(null);
                passwordInputLayout.setError(null);

                Log.d("MainActivity", "Attempting to log in for user: " + user);

                // check for empty or invalid inputs and show specific errors
                if (TextUtils.isEmpty(user)) {
                    emailInputLayout.setError("Email cannot be empty");
                    Log.d("MainActivity", "Login failed: Email empty.");
                    return;
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                    emailInputLayout.setError("Invalid email address");
                    Log.d("MainActivity", "Login failed: Invalid email.");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    passwordInputLayout.setError("Password cannot be empty");
                    return;
                }


                // If inputs are valid, proceed to check credentials
                Boolean checkUserPass = DB.checkUserPassword(user, pass);
                if (checkUserPass) {
                    Log.d("MainActivity", "Login successful for user: " + user);
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(intent);
                } else {
                    Log.d("MainActivity", "Login failed: User does not exist or password incorrect.");
                    Toast.makeText(MainActivity.this, "Login Failed, User does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}