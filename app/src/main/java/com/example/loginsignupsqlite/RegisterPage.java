package com.example.loginsignupsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterPage extends AppCompatActivity {

    TextInputEditText email, password, cPassword;
    Button signUp;
    TextView signIn;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cPassword = findViewById(R.id.cPassword);
        signUp = findViewById(R.id.sign_up);
        signIn = findViewById(R.id.sign_in);
        DB = new DBHelper(this);

        // navigate to the sign in activity
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                // check valid email
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                // password validation
                String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(cPass)){
                    Toast.makeText(RegisterPage.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!user.matches(emailPattern)) {
                    Toast.makeText(RegisterPage.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else if (!pass.matches(passwordPattern)) {
                    Toast.makeText(RegisterPage.this, "Password needs 8 chars, including a number, upper & lower case letters, and a special char", Toast.LENGTH_LONG).show();
                } else if (!pass.equals(cPass)) {
                    Toast.makeText(RegisterPage.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUser = DB.checkUserEmail(user);
                    if (!checkUser) {
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
                        Toast.makeText(RegisterPage.this, "User already exists, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}