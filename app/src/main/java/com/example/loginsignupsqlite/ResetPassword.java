package com.example.loginsignupsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class ResetPassword extends AppCompatActivity {

    TextInputEditText email, password2, password3;
    Button confirm_btn;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.email);
        password2 = findViewById(R.id.password2);
        password3 = findViewById(R.id.password3);
        confirm_btn = findViewById(R.id.confirm_btn);
        DB = new DBHelper(this);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = email.getText().toString();
                String pass2 = password2.getText().toString();
                String pass3 = password3.getText().toString();

                // check valid email
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                // password validation
                String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

                // validations for empty fields
                if (user.isEmpty() || pass2.isEmpty() || pass3.isEmpty()) {
                    Toast.makeText(ResetPassword.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else if (!user.matches(emailPattern)) {
                    Toast.makeText(ResetPassword.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else if (!pass2.matches(passwordPattern)) {
                    Toast.makeText(ResetPassword.this, "Password needs 8 chars, including a number, upper & lower case letters, and a special char", Toast.LENGTH_LONG).show();
                } else if (!pass2.equals(pass3)) {
                    Toast.makeText(ResetPassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    resetUserPassword(user, pass2);
                }
            }
        });
    }

    // method to reset user password
    private void resetUserPassword(String user, String newPass){
        boolean result = DB.resetUserPassword(user, newPass);
        if(result){
            Toast.makeText(ResetPassword.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(ResetPassword.this, "Failed to reset password. User not found.", Toast.LENGTH_SHORT).show();
        }
    }
}