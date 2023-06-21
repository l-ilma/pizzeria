package com.example.pizzeria;

import android.view.View;
import android.widget.ImageButton;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;


import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;



@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainActivityTests {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    public void login() throws InterruptedException {
        onView(ViewMatchers.withId(R.id.login_email))
                .perform(typeText(Configuration.email));
        onView(ViewMatchers.withId(R.id.login_password))
                .perform(typeText(Configuration.password), closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.login))
                .perform(click());

        Thread.sleep(4000);
    }

    @Test
    public void CperformLogout() throws InterruptedException, UiObjectNotFoundException {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiSelector selector = new UiSelector();

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());

        Thread.sleep(1000);

        device.findObject(selector.text("Logout")).click();

        Thread.sleep(3000);
    }

    @Test
    public void BbasketTest() throws InterruptedException {
        onView(ViewMatchers.withId(R.id.imageView2))
                .perform(click());
        Thread.sleep(2000);
    }

    @Test
    public void AswitchFragments() throws UiObjectNotFoundException, InterruptedException {
        login();
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        UiSelector selector = new UiSelector();

        device.findObject(selector.text("DRINKS")).click();
        Thread.sleep(2000);

        device.findObject(selector.text("PIZZAS")).click();
        Thread.sleep(2000);

    }


    /**
     * Test LogOut from Basket
     * */
    @Test
    public void GLogOutFromBasketTest() throws InterruptedException, UiObjectNotFoundException {


        login();

        onView(ViewMatchers.withId(R.id.imageView2)).perform(click());

        Thread.sleep(2000);

        CperformLogout(); //fails currently bcs the logout button is not present
    }



}
