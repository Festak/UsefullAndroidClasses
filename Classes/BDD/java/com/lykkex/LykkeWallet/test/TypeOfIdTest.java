package com.lykkex.LykkeWallet.test;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.selfie.TypeOfIdActivity_;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ibatica on 11/8/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TypeOfIdTest {

    @Rule
    public ActivityTestRule<TypeOfIdActivity_> mActivityRule = new ActivityTestRule<>(
            TypeOfIdActivity_.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
    }

    @Test
    public void checkPassportIdDrivingLicenseExist() {
        onView(withId(R.id.typeOfIdIdLL)).check(matches(isDisplayed()));
        onView(withId(R.id.typeOfIdIdTxt)).check(matches(isDisplayed()));

        onView(withId(R.id.typeOfIdDrivingLicenseLL)).check(matches(isDisplayed()));
        onView(withId(R.id.typeOfIdDrivingLicenseTxt)).check(matches(isDisplayed()));

        onView(withId(R.id.typeOfIdPassportLL)).check(matches(isDisplayed()));
        onView(withId(R.id.typeOfIdPassportTxt)).check(matches(isDisplayed()));

    }

    @Test
    public void checkForCrossIcon() {
//        sleepThread(1000);
        onView(withContentDescription(R.string.abc_action_bar_up_description)).check(matches(isDisplayed()));
        ;
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click());

    }

    private void sleepThread(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
