package wat.seth.dev.capstoneproject.handlers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.widget.TextView;

import wat.seth.dev.capstoneproject.R;

/**
 * Created by seth-wat on 1/1/2018.
 */

public class SearchMaxMag extends EachLoop {
    /*
    This class updates search_max_mag preferences, it's related view,
    and provides behavior to be executed on touch events.
     */
    private double mag;
    private double incrementStep;
    private double decrementStep;
    private TextView displayView;
    private Activity activity;

    public SearchMaxMag(Activity activity, TextView textView, double incrementStep, double decrementStep) {
        displayView = textView;
        this.activity = activity;
        init();
        this.incrementStep = incrementStep;
        this.decrementStep = decrementStep;
    }

    @Override
    public void eachLoop() {
        if (getType() == TYPE_INCREMENT) {
            if (mag == 10) return;
            incrementRange(mag, incrementStep);
            displayView.setText(String.format("%.1f", mag));
        } else {
            if (mag == 0) return;
            decrementRange(mag, decrementStep);
            displayView.setText(String.format("%.1f", mag));
        }
    }

    @Override
    public void finish() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(R.string.search_max_mag), String.format("%.1f", mag));
        editor.commit();
    }

    private double incrementRange(double range, double step) {
        double value = range + step;
        if (value > 10) value = 10;
        this.mag = value;
        return value;
    }

    private double decrementRange(double range, double step) {
        double value = range - step;
        if (value < 0) value = 0;
        this.mag = value;
        return value;
    }

    private void init() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        String stringValue = sharedPref.getString(activity.getString(R.string.search_max_mag),
                activity.getString(R.string.search_max_mag_default_value));
        mag = Double.valueOf(stringValue);
        displayView.setText(String.format("%.1f", mag));
    }

}
