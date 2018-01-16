package wat.seth.dev.capstoneproject.handlers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import wat.seth.dev.capstoneproject.R;

/**
 * Created by seth-wat on 1/1/2018.
 */

public class SearchOrderBy {
    /*
    Class updates search_order_by shared preference, it's related view,
    and provides access to a click listener.

    search_order_by holds one of two String values.
    */
    public static final String DATE_VALUE = "time";
    public static final String MAG_VALUE = "magnitude";

    private String current;
    private TextView displayView;
    private Activity activity;
    private View.OnClickListener clickListener;

    public SearchOrderBy(TextView displayView, Activity activity) {
        this.displayView = displayView;
        this.activity = activity;
        init();
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
                update();
            }
        };

    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    private void init() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        current = sharedPref.getString(activity.getString(R.string.search_order_by),
                activity.getString(R.string.search_order_by_default_value));
        display();
    }

    private void toggle() {
        if (current.equals(DATE_VALUE)) {
            current = MAG_VALUE;
        } else {
            current = DATE_VALUE;
        }
        display();
    }

    private void update() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(R.string.search_order_by), current);
        editor.commit();
    }

    private void display() {
        if (current.equals(DATE_VALUE)) {
            displayView.setText(activity.getResources().getString(R.string.date_display));
        } else {
            displayView.setText(MAG_VALUE);
        }
    }


}
