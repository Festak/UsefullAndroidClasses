package com.lykkex.LykkeWallet.test;

import android.support.test.espresso.Espresso;
import android.support.test.filters.LargeTest;
import android.support.test.filters.Suppress;
import android.support.test.rule.ActivityTestRule;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.authentication.SignInActivity_;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by ibatica on 11/8/17.
 */

@Suppress
@LargeTest
public class RegistrationTest {

    @Rule
    public ActivityTestRule<SignInActivity_> mActivityRule = new ActivityTestRule<>(
            SignInActivity_.class);

    @Before
    public void before() {
        // Specify a valid string.
    }

    /**
     * Test registering user use case
     * If you want to test please replace emailStr since this user might exist
     */
    @Test
    public void registerUser() {
        String emailStr = "ivanstest23@is.com";
        String pinCode = "0000";
        String password = "123456";
        String secret = "qwerty";
        String fullName = "IvanSTest23";
        String phone = "123456";
        onView(withId(R.id.btnChangeServer)).perform(click());
        onView(withId(R.id.radioTest)).perform(click());
        onView(withId(R.id.saveBtn)).perform(click());
        onView(withId(R.id.emailEditText)).perform(typeText(emailStr));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.emailEditText)).check(matches(withText(emailStr)));
        onView(withId(R.id.confirmEmailButton)).perform(click());
        onView(withId(R.id.codeEditText)).perform(typeText(pinCode));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.tvNext)).perform(click());
        sleepThread();

        onView(withId(R.id.hintPassword)).perform(typeText(secret));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.passwordEditText)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.confirmPasswordEditText)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.tvNext)).perform(click());
        sleepThread(5000);

        onView(withId(R.id.editPhone)).perform(typeText(phone));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fullNameEditText)).perform(typeText(fullName));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.tvNext)).perform(click());
        sleepThread(3000);

        onView(withId(R.id.codeEditText)).perform(typeText(pinCode));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.tvNext)).perform(click());
        sleepThread();

        onView(withId(R.id.btnOne)).perform(click());
        onView(withId(R.id.btnTwo)).perform(click());
        onView(withId(R.id.btnThree)).perform(click());
        onView(withId(R.id.btnFour)).perform(click());

        onView(withId(R.id.btnOne)).perform(click());
        onView(withId(R.id.btnTwo)).perform(click());
        onView(withId(R.id.btnThree)).perform(click());
        onView(withId(R.id.btnFour)).perform(click());


    }

    private void sleepThread() {
        sleepThread(1000);
    }

    private void sleepThread(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
