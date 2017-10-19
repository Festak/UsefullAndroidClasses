package com.euclid.uptiiq.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by e.fetskovich on 3/15/17.
 */

public class BitmapFactoryOptionsSettings {

    public BitmapFactoryOptionsSettings() {
    }

    public static BitmapFactory.Options createBitmapFactoryOptions() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inMutable = true;
        options.inScaled = true;
        options.inDensity = 1;
        options.inTargetDensity = 1;
        return options;
    }
}
