package com.rmuhamed.tooltiptextview.helper;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by rmuhamed on 11/12/16.
 */

public final class ConverstionHelper {

    /**
     * Takes a size measured by pixel unit to convert it into DP unit
     * @param size - measure using pixel unit
     * @param aContext - to retrieve display metrics
     * @return dp - converted size measure
     */
    public static int toDP(float size, Context aContext) {
        int dp = (int) (size / (aContext.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));

        return dp;
    }
}
