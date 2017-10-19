package com.euclid.uptiiq.utils;

import java.text.DecimalFormat;

/**
 * @author e.fetskovich on 10/11/17.
 */

public class BytesFormatter {
    private static final long K = 1024;
    private static final long M = K * K;
    private static final long G = M * K;
    private static final long T = G * K;

    private BytesFormatter(){
        // do nothing
    }

    public static String convertToStringRepresentation(final long value){
        if(value > 0) {
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
                                 final String unit){
        final double result =
                divider > 1 ? (double) value / (double) divider : (double) value;
        return new DecimalFormat("#,##0.#").format(result) + " " + unit;
    }

}
