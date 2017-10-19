package com.euclid.uptiiq.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.euclid.uptiiq.utils.bitmap.BitmapFactoryOptionsSettings;
import com.euclid.uptiiq.utils.bitmap.PngUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by e.fetskovich on 6/16/17.
 */

public class FileUtils {

    public static final String WATERMARK_IMAGE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/";

    private FileUtils() {
        // do nothing
    }

    public static boolean isFileExists(String filePath, String filename) {
        File file = new File(filePath + "/" + filename);
        return file.exists();
    }

    public static File[] getAllFilesFromCurrentPath(String filePath) {
        File file = new File(filePath);
        return getAllFilesFromPath(file);
    }

    public static File[] getAllFilesFromPath(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory.listFiles();
    }

    public static Bitmap loadBitmapFromFile(String pathToFile, String fileName) {
        String filePath = pathToFile.concat(fileName);
        return BitmapFactory.decodeFile(filePath, BitmapFactoryOptionsSettings.createBitmapFactoryOptions());
    }

    public static Bitmap loadBitmapFromFile(File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath(), BitmapFactoryOptionsSettings.createBitmapFactoryOptions());
    }

    public static Bitmap loadBitmapFromFile(String pathToFile, String filename, float maxImageSize) {
        Bitmap bitmap = loadBitmapFromFile(pathToFile, filename);
        return resizeImage(bitmap, maxImageSize, true);
    }

    public static File writeBitmapToFile(String filePath, String fileName, Bitmap bitmap) {

        byte[] data = PngUtils.bitmapToPng(bitmap);

        File file = new File(filePath);

        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(filePath.concat(fileName));
        Log.v("MyTagPath", file.getAbsolutePath());

        writeDataToFile(file, data);
        return file;
    }

    public static void writeDataToFile(File file, byte[] data) {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(data);
            bufferedOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getJsonFile(String filePath, String filename) {
        final File path = new File(filePath + "/");

        if (!path.exists()) {
            path.mkdirs();
        }

        final File file = new File(path, filename);
        return file;
    }

    public static void saveData(byte[] data, File file, boolean append) {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file, append);
            fileOutputStream.write(data);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] loadData(File file) {
        FileInputStream fileInputStream = null;
        byte[] data = new byte[(int) file.length()];

        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static Bitmap resizeImage(Bitmap realImage, float maxImageSize,
                                      boolean filter) {
        float ratio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());

        return Bitmap.createScaledBitmap(
                realImage,
                width,
                height,
                filter
        );
    }


}
