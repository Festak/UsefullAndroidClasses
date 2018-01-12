package com.pullmedia.eyesellV2.screens;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.pullmedia.eyesellV2.R;

/**
 * @author e.fetskovich on 11/29/17.
 */

public abstract class Router {

    protected Activity activity;

    protected Router(Activity activity) {
        this.activity = activity;
    }

    protected void openActivity(Intent intent) {
        activity.startActivity(intent);
    }

    protected void openActivityForResult(Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }

    public void openFragment(Fragment fragment, Bundle bundle, boolean addToBackStack, boolean popBackStack) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        openFragment(fragment, addToBackStack, popBackStack);
    }

    protected void openFragment(Fragment fragment, boolean addToBackStack, boolean popBackStack) {
        if (popBackStack) {
            activity.getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.animator.fragment_slide_left_enter,
                R.animator.fragment_slide_left_exit,
                R.animator.fragment_slide_right_enter,
                R.animator.fragment_slide_right_exit);
        transaction.replace(R.id.main_container, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}
