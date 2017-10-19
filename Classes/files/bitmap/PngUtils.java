package com.euclid.uptiiq.utils.bitmap;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by e.fetskovich on 3/20/17.
 */

public class PngUtils {

    public static byte[] bitmapToPng(Bitmap bitmap){
        return bitmapToByte(bitmap);
    }

    private static byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}
