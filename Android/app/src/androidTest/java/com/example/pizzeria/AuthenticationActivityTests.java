package com.example.pizzeria;

import android.content.Context;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import com.example.pizzeria.ui.authentication.AuthenticationActivity;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthenticationActivityTests {
    @Rule
    public ActivityScenarioRule<AuthenticationActivity> activityRule =
            new ActivityScenarioRule<>(AuthenticationActivity.class);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.pizzeria", appContext.getPackageName());
    }

    @Test
    public void AregistrationTestFail() throws UiObjectNotFoundException, InterruptedException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiSelector selector = new UiSelector();

        device.findObject(selector.text("REGISTER")).click();
        Thread.sleep(1000);
        onView(ViewMatchers.withId(R.id.signup_username))
                .perform(typeText(Configuration.username), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.signup_email))
                .perform(typeText("invalid_email"), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.signup_password))
                .perform(typeText(Configuration.password), closeSoftKeyboard());
        Thread.sleep(1000);
        onView(ViewMatchers.withId(R.id.register_button))
                .perform(click());

        Thread.sleep(4000);
    }

    @Test
    public void CregistrationTestPass() throws UiObjectNotFoundException, InterruptedException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiSelector selector = new UiSelector();

        device.findObject(selector.text("REGISTER")).click();
        Thread.sleep(1000);
        onView(ViewMatchers.withId(R.id.signup_username))
                .perform(typeText(Configuration.username), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.signup_email))
                .perform(typeText(Configuration.email), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.signup_password))
                .perform(typeText(Configuration.password), closeSoftKeyboard());
        Thread.sleep(1000);
        onView(ViewMatchers.withId(R.id.register_button))
                .perform(click());

        Thread.sleep(4000);
    }

    @Test
    public void BloginTestFail() throws UiObjectNotFoundException, InterruptedException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiSelector selector = new UiSelector();

        device.findObject(selector.text("LOGIN")).click();

        onView(ViewMatchers.withId(R.id.login_email))
                .perform(typeText("invalid_mail"));
        onView(ViewMatchers.withId(R.id.login_password))
                .perform(typeText("invalid_password"), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.login))
                .perform(click());

        Thread.sleep(4000);
    }
}