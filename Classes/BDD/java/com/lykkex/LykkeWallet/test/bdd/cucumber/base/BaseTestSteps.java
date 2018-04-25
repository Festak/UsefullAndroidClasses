package com.lykkex.LykkeWallet.test.bdd.cucumber.base;

import android.app.Activity;
import android.content.Context;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.utils.ResourceIdentifier;
import com.lykkex.LykkeWallet.test.espresso.viewaction.BaseViewActions;
import com.lykkex.LykkeWallet.test.espresso.viewmatcher.BaseViewMatchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * @author e.fetskovich on 2/21/18.
 */

public class BaseTestSteps {

    protected Context context;
    protected BaseViewActions viewActions;
    protected BaseViewMatchers viewMatchers;

    protected BaseTestSteps() {
        this.context = LykkeApplication_.getInstance().getApplicationContext();
        viewActions = new BaseViewActions();
        viewMatchers = new BaseViewMatchers();
    }

    protected void performTypeText(String id, String text) {
        int viewId = ResourceIdentifier.INSTANCE.getIdForStringViewId(context, id);
        onView(ViewMatchers.withId(viewId)).perform(typeText(text), closeSoftKeyboard());
    }

    protected void performViewClick(String id) {
        int viewId = ResourceIdentifier.INSTANCE.getIdForStringViewId(context, id);
        onView(withId(viewId)).perform(click());
    }

    protected void performViewClick(int viewId) {
        onView(withId(viewId)).perform(click());
    }

    protected void performViewClickWithText(String stringId) {
        int resourceId = ResourceIdentifier.INSTANCE.getIdForString(context, stringId);
        onView(withText(resourceId)).perform(click());
    }

    protected void performClickOnCommonDialogButton(DialogButtonType type) {
        if (type == DialogButtonType.NEGATIVE) {
            onView(withId(android.R.id.button2)).perform(click());
        } else if (type == DialogButtonType.POSITIVE) {
            onView(withId(android.R.id.button1)).perform(click());
        }
    }

    protected void isViewDisplayed(String id) {
        int viewId = ResourceIdentifier.INSTANCE.getIdForStringViewId(context, id);
        onView(withId(viewId)).check(matches(isDisplayed()));
    }

    protected void isViewDisplayedWihText(String text){
        onView(withText(text)).check(matches(isDisplayed()));
    }

    protected void isViewEnabled(String id, boolean isEnabled) {
        int viewId = ResourceIdentifier.INSTANCE.getIdForStringViewId(context, id);
        isViewEnabled(viewId, isEnabled);
    }

    protected void isViewEnabled(int viewId, boolean isEnabled) {
        ViewInteraction viewInteraction = onView(withId(viewId));
        if (isEnabled) {
            viewInteraction.check(matches(ViewMatchers.isEnabled()));
        } else {
            viewInteraction.check(matches(not(ViewMatchers.isEnabled())));
        }
    }

    protected void isViewVisible(String id, boolean isVisible) {
        int viewId = ResourceIdentifier.INSTANCE.getIdForStringViewId(context, id);
        ViewInteraction viewInteraction = onView(withId(viewId));
        if (isVisible) {
            viewInteraction.check(matches(isDisplayed()));
        } else {
            viewInteraction.check(matches(not(isDisplayed())));
        }
    }

    protected void doesViewExists(String id, boolean isExists) {
        int viewId = ResourceIdentifier.INSTANCE.getIdForStringViewId(context, id);
        ViewInteraction viewInteraction = onView(withId(viewId));
        if (isExists) {
            viewInteraction.check(matches(isDisplayed()));
        } else {
            viewInteraction.check(doesNotExist());
        }
    }

    protected void selectItemsOnPositions(String id, int[] positions) {
        int viewId = ResourceIdentifier.INSTANCE.getIdForStringViewId(context, id);
        for (int i = 0; i < positions.length; i++) {
            onView(withId(viewId)).perform(RecyclerViewActions.actionOnItemAtPosition(positions[i], click()));
        }
    }

    protected void performLongClickOnRecyclerItem(String id, int position) {
        int viewId = ResourceIdentifier.INSTANCE.getIdForStringViewId(context, id);
        onView(withId(viewId)).perform(RecyclerViewActions.actionOnItemAtPosition(position, longClick()));
    }

    protected void performCheck(String id, boolean isChecked) {
        int viewId = ResourceIdentifier.INSTANCE.getIdForStringViewId(context, id);
        onView(withId(viewId)).perform(scrollTo(), viewActions.setChecked(isChecked));
    }

    protected void isViewDisplayedWithArgsText(String id, int arg){
        int viewId = ResourceIdentifier.INSTANCE.getIdForString(context, id);
        String text = context.getString(viewId, arg);
        isViewDisplayedWihText(text);
    }

    protected void isTextInputLayoutMatchesText(boolean shouldSeeError, final String id, final String stringIdText) {
        int viewId = ResourceIdentifier.INSTANCE.getIdForStringViewId(context, id);
        int stringId = ResourceIdentifier.INSTANCE.getIdForString(context, stringIdText);

        if (shouldSeeError) {
            onView(withId(viewId)).check(matches(viewMatchers.hasTextInputLayoutErrorText(stringId)));
        } else {
            onView(withId(viewId)).check(matches(not(viewMatchers.hasTextInputLayoutErrorText(stringId))));
        }
    }

    protected Activity getCurrentActivity() {
        final Activity[] activity = new Activity[1];
        onView(isRoot()).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                activity[0] = (Activity) view.getContext();
            }
        });
        return activity[0];
    }

    protected enum DialogButtonType {
        NEGATIVE,
        POSITIVE
    }


    protected void sleepFor(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
