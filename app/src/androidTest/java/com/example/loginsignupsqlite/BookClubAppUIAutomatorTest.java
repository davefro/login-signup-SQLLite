package com.example.loginsignupsqlite;

import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class BookClubAppUIAutomatorTest {
    private static final String PACKAGE_NAME = "com.example.loginsignupsqlite";
    private UiDevice device;

    @Before
    public void setUp() {
        // initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        // start from the home screen
        device.pressHome();
    }

    @Test
    public void completeBookClubAppTest() throws IOException {
        // launch the app
        device.executeShellCommand("am start -n " + PACKAGE_NAME + "/.MainActivity");
        assertTrue("App did not launch.", device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), 5000));

        // navigate to the RegisterPage
        device.findObject(By.res(PACKAGE_NAME, "sign_up")).click();
        assertTrue("Register page did not load correctly.", device.wait(Until.hasObject(By.res(PACKAGE_NAME, "email")), 5000));

        // fill in registration details
        device.findObject(By.res(PACKAGE_NAME, "email")).setText("test@example.com");
        device.findObject(By.res(PACKAGE_NAME, "password")).setText("Password1@");
        device.findObject(By.res(PACKAGE_NAME, "cPassword")).setText("Password1@");
        device.findObject(By.res(PACKAGE_NAME, "sign_up")).click();

        // test for negative scenario, uncomment to check
        // verify that the app displays an error message for the email
//        device.findObject(By.res(PACKAGE_NAME, "email")).setText("invalidemail");
//        device.findObject(By.res(PACKAGE_NAME, "password")).setText("Password1@");
//        device.findObject(By.res(PACKAGE_NAME, "cPassword")).setText("Password1@");
//        device.findObject(By.res(PACKAGE_NAME, "sign_up")).click();
//        assertTrue("Invalid email error message did not appear.", device.wait(Until.hasObject(By.res(PACKAGE_NAME, "emailError")), 3000));

        // launch MainActivity
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "sign_in")), 5000);

        // log in with the same details
        device.findObject(By.res(PACKAGE_NAME, "email")).setText("test@example.com");
        device.findObject(By.res(PACKAGE_NAME, "password")).setText("Password1@");
        device.findObject(By.res(PACKAGE_NAME, "sign_in")).click();

        // test for negative scenario, uncomment to check
        // incorrect login credentials
//        device.findObject(By.res(PACKAGE_NAME, "email")).setText("user@example.com");
//        device.findObject(By.res(PACKAGE_NAME, "password")).setText("wrongPassword");
//        device.findObject(By.res(PACKAGE_NAME, "sign_in")).click();
//        assertTrue("Login error message did not appear.", device.wait(Until.hasObject(By.res(PACKAGE_NAME, "loginError")), 3000));


        // successful login
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "loggedInState")), 5000);

        // verify user email displayed correctly
        assertTrue(device.findObject(By.res(PACKAGE_NAME, "userEmailTextView")).getText().contains("test@example.com"));

        // bottom navigation menu, interact with items
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "bottom_navigation")), 5000);

        // navigate to the Profile section
        device.findObject(By.res(PACKAGE_NAME, "menu_profile")).click();

        //  check for a unique element (profile image)
        boolean isProfileLoaded = device.wait(Until.hasObject(By.res(PACKAGE_NAME, "profilePict")), 5000);
        // assert that the profile section loaded correctly
        assertTrue("Profile section did not load correctly.", isProfileLoaded);

        // interacting with buttons
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "updateButton")), 3000);
        device.wait(Until.hasObject(By.res(PACKAGE_NAME, "deleteButton")), 3000);

        // navigate to the library section
        device.findObject(By.res(PACKAGE_NAME, "menu_library")).click();
        assertTrue("Library section did not load correctly.", device.wait(Until.hasObject(By.res(PACKAGE_NAME, "addButton")), 3000));

        // navigate to the notifications section
        device.findObject(By.res(PACKAGE_NAME, "menu_notifications")).click();
        assertTrue("Notifications section did not load correctly.", device.wait(Until.hasObject(By.res(PACKAGE_NAME, "bookImg")), 3000));

        // navigate to the messages section
        device.findObject(By.res(PACKAGE_NAME, "menu_message")).click();
        assertTrue("Messages section did not load correctly.", device.wait(Until.hasObject(By.res(PACKAGE_NAME, "search_bookName_input")), 3000));

        //  navigate to search book button
        device.findObject(By.res(PACKAGE_NAME, "main_search_btn")).click();
        assertTrue("Search book input did not load correctly.", device.wait(Until.hasObject(By.res(PACKAGE_NAME, "search_bookName_input")), 5000));
    }
}
