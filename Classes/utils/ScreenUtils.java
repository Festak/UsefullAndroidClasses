package com.euclid.uptiiq.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author e.fetskovich on 3/15/18.
 */

public class ScreenUtils {

    public static final int HALF_DIVIDER = 2;
    private static final int DEGREES = 180;
    private static final int DEGREES_90 = 90;

    private ScreenUtils() {
        //do nothing as only static methods
    }

    public static int getOrientation(Context context, Uri photoUri) {
        File file = new File(photoUri.getPath());
        int orientation = -1;
        try {
            String[] orientationColumn = {MediaStore.Images.Media.ORIENTATION};
            Cursor cur = context.getContentResolver().query(photoUri,
                    orientationColumn, null, null, null);
            if (cur != null && cur.moveToFirst()) {
                orientation = cur.getInt(cur.getColumnIndex(orientationColumn[0]));
            }
        } catch (SecurityException ignored) {
            Log.e(ScreenUtils.class.getName(), ignored.getMessage());
        }
        if (orientation == -1) {
            try {
                ExifInterface exif = new ExifInterface(file.getAbsolutePath());
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
            } catch (IOException ignored) {
                Log.e(ScreenUtils.class.getName(), ignored.getMessage());
            }
        }
        return orientation;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(DEGREES);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(DEGREES);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(DEGREES_90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(DEGREES_90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-DEGREES_90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-DEGREES_90);
                break;
            default:
                return bitmap;
        }

        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap,
                    0,
                    0,
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    matrix,
                    true);
            bmRotated = cropImage(bmRotated);
            return bmRotated;
        } catch (OutOfMemoryError e) {
            Log.e(ScreenUtils.class.getName(), e.getMessage());
            return bitmap;
        }
    }

    public static Bitmap applyImage(Uri imageUri, Context activity) {

        int orientation = ScreenUtils.getOrientation(activity, imageUri);

        try {
            final InputStream imageStream = activity.getContentResolver().openInputStream(imageUri);

            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            selectedImage = ScreenUtils.rotateBitmap(selectedImage, orientation);
            return selectedImage;
        } catch (FileNotFoundException | SecurityException ignored) {
            // do nothing
        }
        return null;
    }

    private static Bitmap cropImage(Bitmap bitmap) {
        Bitmap bitmapLocal = bitmap;
        if (bitmap.getWidth() >= bitmap.getHeight()) {

            bitmapLocal = Bitmap.createBitmap(
                    bitmapLocal,
                    bitmapLocal.getWidth() / HALF_DIVIDER -
                            bitmapLocal.getHeight() / HALF_DIVIDER,
                    0,
                    bitmapLocal.getHeight(),
                    bitmapLocal.getHeight()
            );

        } else {

            bitmapLocal = Bitmap.createBitmap(
                    bitmapLocal,
                    0,
                    bitmapLocal.getHeight() / HALF_DIVIDER -
                            bitmapLocal.getWidth() / HALF_DIVIDER,
                    bitmapLocal.getWidth(),
                    bitmapLocal.getWidth()
            );
        }
        return bitmapLocal;
    }
}
