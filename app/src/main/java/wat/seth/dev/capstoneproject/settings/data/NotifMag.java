package wat.seth.dev.capstoneproject.settings.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import wat.seth.dev.capstoneproject.R;

/**
 * Created by seth-wat on 12/31/2017.
 */

public class NotifMag extends EachLoop {
    //See parent class for documentation.
    private double mag;
    private double incrementStep;
    private double decrementStep;
    //View to finish the value with
    private TextView displayView;
    //Activity to get SharedPreferences and Resources.
    private Activity activity;

    public NotifMag(Activity activity, TextView textView, double incrementStep, double decrementStep) {
        displayView = textView;
        this.activity = activity;
        init();
        this.incrementStep = incrementStep;
        this.decrementStep = decrementStep;
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

    public void init() {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String stringValue = sharedPref.getString(activity.getString(R.string.notif_min_mag), "0");
        mag = Double.valueOf(stringValue);
        displayView.setText(String.format("%.1f", mag));
    }

    @Override
    public void eachLoop() {
        if (getType() == TYPE_INCREMENT) {
            if (mag == 10) return;
            incrementRange(mag, incrementStep);
            displayView.playSoundEffect(android.view.SoundEffectConstants.CLICK);
            displayView.setText(String.format("%.1f", mag));
        } else {
            if (mag == 0) return;
            decrementRange(mag, decrementStep);
            displayView.playSoundEffect(android.view.SoundEffectConstants.CLICK);
            displayView.setText(String.format("%.1f", mag));
        }
    }

    @Override
    public void finish() {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(R.string.notif_min_mag), String.valueOf(mag));
        editor.commit();
    }
}
