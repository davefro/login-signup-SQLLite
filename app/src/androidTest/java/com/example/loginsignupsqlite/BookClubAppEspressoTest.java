package com.example.loginsignupsqlite;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.ext.junit.runners.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
public class BookClubAppEspressoTest {

    @Rule
    public ActivityScenarioRule<RegisterPage> activityRule =
            new ActivityScenarioRule<>(RegisterPage.class);

    @Test
    public void testUserRegistrationAndLogin() {

        // correct the data and register
        onView(withId(R.id.email)).perform(clearText());
        onView(withId(R.id.password)).perform(clearText());
        onView(withId(R.id.cPassword)).perform(clearText());

        // negative testing with invalid data and check for error messages.
        performRegistration("invalidemail", "123", "456");
        checkForTextInputLayoutError(R.id.emailInputLayout, "Invalid email address");

        // test with valid data and navigate to the login page
        performRegistration("test@example.com", "Password1@", "Password1@");

        // successful login
        performLogin("test@example.com", "Password1@");

    }

    private void performRegistration(String email, String pass, String confirmPass) {
        onView(withId(R.id.email)).perform(typeText(email), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(pass), closeSoftKeyboard());
        onView(withId(R.id.cPassword)).perform(typeText(confirmPass), closeSoftKeyboard());
        onView(withId(R.id.sign_up)).perform(click());
    }


    private void performLogin(String email, String pass) {
        onView(withId(R.id.email)).perform(typeText(email));
        onView(withId(R.id.password)).perform(typeText(pass));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.sign_in));
    }

    private void checkForTextInputLayoutError(int textInputLayoutId, String expectedErrorText) {
        onView(withId(textInputLayoutId)).check(matches(hasTextInputLayoutErrorText(expectedErrorText)));
    }


    // custom matcher to check TextInputLayout errors
    private static Matcher<View> hasTextInputLayoutErrorText(String expectedErrorText) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (!(item instanceof TextInputLayout)) {
                    return false;
                }
                CharSequence error = ((TextInputLayout) item).getError();
                if (error == null) {
                    return false;
                }
                String errorText = error.toString();
                return expectedErrorText.equals(errorText);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with error: " + expectedErrorText);
            }
        };
    }
}


