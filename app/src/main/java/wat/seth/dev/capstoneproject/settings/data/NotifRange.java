package wat.seth.dev.capstoneproject.settings.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import wat.seth.dev.capstoneproject.R;

/**
 * Created by seth-wat on 12/29/2017.
 */

public class NotifRange extends EachLoop {
    /*
    Class controls updating notif_range shared preference, it's related view,
    and provides behavior to execute on touch events.
    */
    private int range;
    private int incrementStep;
    private int decrementStep;
    private TextView displayView;
    private Activity activity;

    public NotifRange(Activity activity, TextView textView, int incrementStep, int decrementStep) {
        displayView = textView;
        this.activity = activity;
        init();
        this.incrementStep = incrementStep;
        this.decrementStep = decrementStep;
    }

    @Override
    public void eachLoop() {
        if (getType() == TYPE_INCREMENT) {
            if (range == 500) return;
            incrementRange(range, incrementStep);
            displayView.playSoundEffect(android.view.SoundEffectConstants.CLICK);
            displayView.setText(String.valueOf(range));
        } else {
            if (range == 0) return;
            decrementRange(range, decrementStep);
            displayView.playSoundEffect(android.view.SoundEffectConstants.CLICK);
            displayView.setText(String.valueOf(range));
        }
    }

    @Override
    public void finish() {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(R.string.notif_range), String.valueOf(range));
        editor.commit();
    }

    private int incrementRange(int range, int step) {
        int value = range + step;
        if (value > 500) value = 500;
        this.range = value;
        return value;
    }

    private int decrementRange(int range, int step) {
        int value = range - step;
        if (value < 0) value = 0;
        this.range = value;
        return value;
    }

    private void init() {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String stringValue = sharedPref.getString(activity.getString(R.string.notif_range),
                activity.getString(R.string.notif_range_default_value));
        range = Integer.valueOf(stringValue);
        displayView.setText(String.valueOf(range));
    }


}
