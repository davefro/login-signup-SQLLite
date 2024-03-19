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

public class RegisterPage extends AppCompatActivity {

    TextInputEditText email, password, cPassword;
    TextInputLayout emailInputLayout, passwordInputLayout, cPasswordInputLayout;
    Button signUp;
    TextView signIn;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Log.d("RegisterPage", "onCreate: Activity started.");

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cPassword = findViewById(R.id.cPassword);
        signUp = findViewById(R.id.sign_up);
        signIn = findViewById(R.id.sign_in);
        emailInputLayout = findViewById(R.id.emailInputLayout);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        cPasswordInputLayout = findViewById(R.id.cPasswordInputLayout);
        DB = new DBHelper(this);

        Log.d("RegisterPage", "onCreate: Views initialized.");

        // navigate to the sign in activity
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RegisterPage", "Navigating to MainActivity for sign-in.");
                Intent intent = new Intent(RegisterPage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // signup method
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String cPass = cPassword.getText().toString().trim();
                Log.d("RegisterPage", "Attempting to sign up with Email: " + user);

                // reset errors
                emailInputLayout.setError(null);
                passwordInputLayout.setError(null);
                cPasswordInputLayout.setError(null);

                // validation
                if (TextUtils.isEmpty(user)) {
                    emailInputLayout.setError("Email cannot be empty");
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
                    emailInputLayout.setError("Invalid email address");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    passwordInputLayout.setError("Password cannot be empty");
                    return;
                }

                // password validation pattern
                String passwordPattern = "^(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";

                if (!pass.matches(passwordPattern)) {
                    passwordInputLayout.setError("Password needs at least 8 characters, including a capital letter and a special character");
                    return;
                }
                if (!pass.equals(cPass)) {
                    cPasswordInputLayout.setError("Passwords do not match");
                    return;
                }

                // check if user exists
                Boolean checkUser = DB.checkUserEmail(user);
                if (!checkUser) {
                    Log.d("RegisterPage", "User does not exist, proceeding with registration.");
                    // insert user data into the database
                    Boolean insert = DB.insertUserData(user, pass);
                    if (insert) {
                        Toast.makeText(RegisterPage.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterPage.this, "Registration failed, please try again", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("RegisterPage", "User already exists. Registration failed.");
                    emailInputLayout.setError("User already exists");
                }
            }
        });
    }
}