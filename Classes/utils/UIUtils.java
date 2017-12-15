package edu.msstate.nsparc.mdcpsabusereporting.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import edu.msstate.nsparc.mdcpsabusereporting.R;
import edu.msstate.nsparc.mdcpsabusereporting.listeners.DateOfBirthListener;
import edu.msstate.nsparc.mdcpsabusereporting.listeners.OnSpinnerFocusChangeListener;
import edu.msstate.nsparc.mdcpsabusereporting.utils.validation.Validator;

public class UIUtils {

    private static boolean isFragmentLoaded = false;
    private static boolean isReporterLoaded = false;

    private UIUtils() {
        // do nothing
    }

    public static void setStatusBarColor(Window window, int color) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(window.getContext(), color));
        }
    }

 

 
    public static void expandTouchArea(final View smallView, final int extraPadding) {
        final View parent = (View) smallView.getParent();
        Rect rect = new Rect();
        smallView.getHitRect(rect);
        rect.top -= extraPadding;
        rect.left -= extraPadding;
        rect.right += extraPadding;
        rect.bottom += extraPadding;
        parent.setTouchDelegate(new TouchDelegate(rect, smallView));
    }

    public static void setBackgroundDrawable(View view, int drawableId) {
        view.setBackground(view.getContext().getResources().getDrawable(drawableId));
    }

    public static void setDrawableEditTextIcon(EditText editText, int drawableId) {
        editText.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0);
    }

    public static void setSpinnerFocusable(Spinner spinner, boolean value) {
        spinner.setFocusable(value);
        spinner.setFocusableInTouchMode(value);
        spinner.setOnFocusChangeListener(new OnSpinnerFocusChangeListener());
    }

    public static void hideKeyboard(MotionEvent ev, Activity activity) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = activity.getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        v.clearFocus();
                        hideSoftKeyboard(v);
                    }
                }
            }
        }
    }

    public static void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void showKeyboard(Dialog dialog) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) dialog.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                dialog.getWindow().getDecorView().getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }

 

    public static void scrollUp(View root, ScrollView scroll) {
        root.clearFocus();
        scroll.scrollTo(0, root.getTop());
    }




}
