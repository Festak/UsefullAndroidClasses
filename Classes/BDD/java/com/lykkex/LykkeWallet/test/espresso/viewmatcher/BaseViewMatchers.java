package com.lykkex.LykkeWallet.test.espresso.viewmatcher;

import android.support.design.widget.TextInputLayout;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author e.fetskovich on 3/13/18.
 */

public class BaseViewMatchers {
    public Matcher<View> hasTextInputLayoutErrorText(final int stringResourceId) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextInputLayout)) {
                    return false;
                }

                CharSequence error = ((TextInputLayout) view).getError();

                if (error == null) {
                    return false;
                }

                String errorText = view.getContext().getString(stringResourceId);

                return error.equals(errorText);
            }

            @Override
            public void describeTo(Description description) {
                // do nothing
            }
        };
    }
}
