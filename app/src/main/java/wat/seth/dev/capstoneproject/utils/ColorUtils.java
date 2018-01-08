package wat.seth.dev.capstoneproject.utils;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.content.ContextCompat;

import wat.seth.dev.capstoneproject.R;

/**
 * Created by seth-wat on 1/7/2018.
 */

public class ColorUtils {

    public static ColorFilter setMagColor(double mag, Context mContext) {
        if (mag <= 1) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_0), PorterDuff.Mode.SRC);
        } else if (mag <= 2) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_1), PorterDuff.Mode.SRC);
        } else if (mag <= 3) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_2), PorterDuff.Mode.SRC);
        } else if (mag <= 4) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_3), PorterDuff.Mode.SRC);
        } else if (mag <= 5) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_4), PorterDuff.Mode.SRC);
        } else if (mag <= 6) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_5), PorterDuff.Mode.SRC);
        } else if (mag <= 7) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_6), PorterDuff.Mode.SRC);
        } else if (mag <= 8) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_7), PorterDuff.Mode.SRC);
        } else if (mag <= 9) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_8), PorterDuff.Mode.SRC);
        } else if (mag <= 10) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_9), PorterDuff.Mode.SRC);
        }
        return null;
    }

    public static ColorFilter setDepthColor(double depth, Context mContext) {
        if (depth <= 0) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_0), PorterDuff.Mode.SRC);
        } else if (depth <= 25) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_1), PorterDuff.Mode.SRC);
        } else if (depth <= 50) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_2), PorterDuff.Mode.SRC);
        } else if (depth <= 75) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_3), PorterDuff.Mode.SRC);
        } else if (depth <= 100) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_4), PorterDuff.Mode.SRC);
        } else if (depth <= 125) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_5), PorterDuff.Mode.SRC);
        } else if (depth <= 175) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_6), PorterDuff.Mode.SRC);
        } else if (depth <= 200) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_7), PorterDuff.Mode.SRC);
        } else if (depth <= 250) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_8), PorterDuff.Mode.SRC);
        } else if (depth <= 300) {
            return new PorterDuffColorFilter(ContextCompat.getColor(mContext, R.color.mag_9), PorterDuff.Mode.SRC);
        }
        return null;
    }

    public static float getMarkerColor(double mag) {
        if (mag <= 1) {
            return 40f;
        } else if (mag <= 2) {
            return 35f;
        } else if (mag <= 3) {
            return 30f;
        } else if (mag <= 4) {
            return 25f;
        } else if (mag <= 5) {
            return 20f;
        } else if (mag <= 6) {
            return 15f;
        } else if (mag <= 7) {
            return 10f;
        } else if (mag <= 8) {
            return 5f;
        } else if (mag <= 9) {
            return 2.5f;
        } else if (mag <= 10) {
            return 0f;
        }
        return 0f;
    }

}
