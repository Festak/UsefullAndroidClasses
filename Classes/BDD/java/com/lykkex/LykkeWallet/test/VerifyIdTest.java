package com.lykkex.LykkeWallet.test;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.lykkex.LykkeWallet.gui.activity.MainActivity_;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ibatica on 11/8/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class VerifyIdTest {

    @Rule
    public ActivityTestRule<MainActivity_> mActivityRule = new ActivityTestRule<>(
            MainActivity_.class);

    @Before
    public void before() {
        // Specify a valid string.
    }

    @Test
    public void passportTestCase() {

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
