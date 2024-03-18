package com.example.loginsignupsqlite;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValidationUtilsTest {

    //test that a valid email address passes the email validation
    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(ValidationUtils.isValidEmail("test@example.com"));
    }

    //tests that an incorrectly formatted email address fails the email validation
    @Test
    public void emailValidator_IncorrectEmail_ReturnsFalse() {
        assertFalse(ValidationUtils.isValidEmail("testexample.com"));
    }

    //tests that a valid password passes the password validation.
    @Test
    public void passwordValidator_CorrectPassword_ReturnsTrue() {
        assertTrue(ValidationUtils.isValidPassword("Password1@"));
    }

    //tests that password is too short and lacks complexity
    @Test
    public void passwordValidator_IncorrectPassword_ReturnsFalse() {
        assertFalse(ValidationUtils.isValidPassword("pass"));
    }

    //insufficient length, even if it includes special char, uppercase and num
    @Test
    public void passwordValidator_WeakPasswordDueToLength_ReturnsFalse() {
        assertFalse(ValidationUtils.isValidPassword("Pass1@"));

    }
}
