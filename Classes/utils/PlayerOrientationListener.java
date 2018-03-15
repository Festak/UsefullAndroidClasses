package com.wepassed.android.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.OrientationEventListener;

/**
 * @author e.fetskovich on 2/9/18.
 */

public class PlayerOrientationListener extends OrientationEventListener {

    private Activity activity;

    private boolean orientationLockLandscape = false;
    private boolean isOrientationLockPortrait = false;

    public PlayerOrientationListener(Activity context) {
        super(context);
        this.activity = context;
    }

    public void lockLandscape() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        orientationLockLandscape = true;
        isOrientationLockPortrait = false;
    }

    public void lockPortrait() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        isOrientationLockPortrait = true;
        orientationLockLandscape = false;
    }


    @Override
    public void onOrientationChanged(int orientation) {
        int rudeOrientation = getRudeOrientationValue(orientation);
        switchOrientationAccordingToDegrees(rudeOrientation);
    }

    private int getRudeOrientationValue(int orientation) {
        int rudeOrientation = orientation;
        int threshold = 5;
        if (Math.abs(orientation) < threshold) {
            rudeOrientation = 0;
        } else if (Math.abs(orientation - 90) < threshold) {
            rudeOrientation = 90;
        } else if (Math.abs(orientation - 180) < threshold) {
            rudeOrientation = 180;
        } else if (Math.abs(orientation - 270) < threshold) {
            rudeOrientation = 270;
        }
        return rudeOrientation;
    }

    private void switchOrientationAccordingToDegrees(int orientation) {
        switch (orientation) {
            case 0:
                if (!orientationLockLandscape) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    isOrientationLockPortrait = false;
                }
                break;
            case 90:
                if (!isOrientationLockPortrait) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    orientationLockLandscape = false;
                }
                break;
            case 180:
                if (!orientationLockLandscape) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
                    isOrientationLockPortrait = false;
                }
                break;
            case 270:
                if (!isOrientationLockPortrait) {
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    orientationLockLandscape = false;
                }
                break;
        }
    }

}
