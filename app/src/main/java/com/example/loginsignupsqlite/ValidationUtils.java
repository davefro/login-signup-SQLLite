package com.example.loginsignupsqlite;

import android.util.Patterns;

public class ValidationUtils {

    // regular expression for validating email
    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // validates email using the EMAIL_PATTERN
    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_PATTERN);
    }

    // validates password
    public static boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return password != null && password.matches(passwordPattern);
    }
}
