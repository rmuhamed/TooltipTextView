package com.rmuhamed.tooltiptextview.helper;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by rmuhamed on 11/12/16.
 */

public final class ConversionHelper {
    private static ConversionHelper instance = null;
    private final Context context;

    private ConversionHelper(Context context) {
        this.context = context;
    }

    public static ConversionHelper with(Context context) {
        if (instance == null) {
            instance = new ConversionHelper(context);
        }

        return instance;
    }

    /**
     * Takes a size measured by pixel unit to convert it into DP unit
     * @param size - measure using pixel unit
     * @return dp - converted size measure
     */
    public int toDP(float size) {
        int dp = (int) (size / (this.context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));

        return dp;
    }
}
