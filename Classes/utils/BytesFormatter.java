package com.euclid.uptiiq.utils;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @author e.fetskovich on 10/11/17.
 */

public class BytesFormatter {
    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;

    private BytesFormatter() {
        // do nothing
    }

    public static String formatFileSize(long size) {
        String hrSize = null;

        double b = size;
        double k = size / 1024.0;
        double m = ((size / 1024.0) / 1024.0);
        double g = (((size / 1024.0) / 1024.0) / 1024.0);
        double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

        DecimalFormat dec = new DecimalFormat("0.00");

        if (t > 1) {
            hrSize = dec.format(t).concat(" TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat(" GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat(" MB");
        } else if (k > 1) {
            hrSize = dec.format(k).concat(" KB");
        } else {
            hrSize = dec.format(b).concat(" Bytes");
        }

        return hrSize;
    }

    public static List<Long> mbUploadValues(long bytesTotal, long mbCatch) {
        List<Long> longs = new LinkedList<>();
        long totalUploadSizeMb = convertBytesToMB(bytesTotal);
        for (long i = mbCatch; i < totalUploadSizeMb; i+=mbCatch) {
            longs.add(i);
        }
        return longs;
    }

    public static long convertBytesToMB(long bytesTotal) {
        return bytesTotal / 1024 / 1024;
    }

    public static String convertToStringRepresentation(final long value) {
        if (value > 0) {
            final long[] dividers = new long[]{T, G, M, K, 1};
            final String[] units = new String[]{"TB", "GB", "MB", "KB", "B"};
            String result = null;
            for (int i = 0; i < dividers.length; i++) {
                final long divider = dividers[i];
                if (value >= divider) {
                    result = format(value, divider, units[i]);
                    break;
                }
            }
            return result;
        } else {
            return "0";
        }
    }

    private static String format(final long value,
                                 final long divider,
                                 final String unit) {
        final double result =
                divider > 1 ? (double) value / (double) divider : (double) value;
        return new DecimalFormat("#,##0.#").format(result) + " " + unit;
    }

}
