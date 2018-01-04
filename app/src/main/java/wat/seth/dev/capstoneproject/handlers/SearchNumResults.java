package wat.seth.dev.capstoneproject.handlers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.widget.TextView;

import wat.seth.dev.capstoneproject.R;

/**
 * Created by seth-wat on 1/1/2018.
 */

public class SearchNumResults extends EachLoop {
    /*
    Class controls updating search_max_results shared preference, it's related view,
    and provides behavior to execute on touch events.
    */
    private int results;
    private int incrementStep;
    private int decrementStep;
    private TextView displayView;
    private Activity activity;

    public SearchNumResults(Activity activity, TextView textView, int incrementStep, int decrementStep) {
        displayView = textView;
        this.activity = activity;
        init();
        this.incrementStep = incrementStep;
        this.decrementStep = decrementStep;
    }

    @Override
    public void eachLoop() {
        if (getType() == TYPE_INCREMENT) {
            if (results == 500) return;
            incrementRange(results, incrementStep);
            displayView.playSoundEffect(android.view.SoundEffectConstants.CLICK);
            displayView.setText(String.valueOf(results));
        } else {
            if (results == 0) return;
            decrementRange(results, decrementStep);
            displayView.playSoundEffect(android.view.SoundEffectConstants.CLICK);
            displayView.setText(String.valueOf(results));
        }
    }

    @Override
    public void finish() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(R.string.search_max_results), String.valueOf(results));
        editor.commit();
    }

    private int incrementRange(int results, int step) {
        int value = results + step;
        if (value > 150) value = 150;
        this.results = value;
        return value;
    }

    private int decrementRange(int results, int step) {
        int value = results - step;
        if (value < 0) value = 0;
        this.results = value;
        return value;
    }

    private void init() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        String stringValue = sharedPref.getString(activity.getString(R.string.search_max_results),
                activity.getString(R.string.search_max_results_default_value));
        results = Integer.valueOf(stringValue);
        displayView.setText(String.valueOf(results));
    }

}
