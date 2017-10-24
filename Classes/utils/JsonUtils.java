package com.transfer.pay.utils.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.transfer.pay.utils.FileUtils;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by i.statkevich on 04.11.2016.
 */

public class JsonUtils {

    private static Gson gson;

    private JsonUtils() {
        // Do nothing
    }

    public static <T> T fromJson(byte[] data, Class<T> type) throws JsonSyntaxException {
        String json = new String(data);
        return fromJson(json, type);
    }

    public static <T> T fromJson(String json, Class<T> type) throws JsonSyntaxException {
        return getGson().fromJson(json, type);
    }

    public static byte[] toJsonBytes(Object data) throws Exception {
        String json = toJson(data);
        return json.getBytes();
    }

    public static String toJson(Object data) throws Exception {
        return getGson().toJson(data);
    }

    public static void listToFile(List<?> models, File file) {
        Gson gson = GsonFactory.create();
        String json = gson.toJson(models);

        FileUtils.saveData(
                json.getBytes(),
                file,
                false
        );
    }

    public static void appendObjectToFile(List<?> models, File file) {
        Gson gson = GsonFactory.create();
        String json = gson.toJson(models);

        FileUtils.saveData(
                json.getBytes(),
                file,
                true
        );
    }

    public static <T> List<T> fileToListByClass(File file, Type listType, Class<T> clazz) {
        byte[] data = FileUtils.loadData(file);
        List<T> emptyList = new ArrayList<>();

        if (data == null) {
            return emptyList;
        }

        String json = new String(data);

        try {
            Gson gson = GsonFactory.create();
            return gson.fromJson(json, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emptyList;
    }

    public static List<?> fileToList(File file, Type listType) {
        byte[] data = FileUtils.loadData(file);
        List<?> emptyList = new ArrayList<>();

        if (data == null) {
            return emptyList;
        }

        String json = new String(data);

        try {
            Gson gson = GsonFactory.create();
            return gson.fromJson(json, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emptyList;
    }

    public static <T> List<T> loadCollectionFromJson(String json, Type listType, Class<T> clazz) {
        Gson gson = GsonFactory.create();
        return gson.fromJson(json, listType);
    }

    private static Gson getGson() {
        if (gson == null) {
            gson = GsonFactory.create();
        }

        return gson;
    }
}
