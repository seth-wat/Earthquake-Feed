package wat.seth.dev.capstoneproject.handlers;

/**
 * Created by seth-wat on 1/1/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import wat.seth.dev.capstoneproject.R;

public class SearchSortBy {
    /*
    Class controls updating search_sort_by shared preference, it's related view,
    and provides access to a click listener.

    search_sort_by holds one of two String values.
     */

    public static final String ASC_VALUE = "-asc";
    public static final String DESC_VALUE = "";

    private String current;
    private TextView displayView;
    private Activity activity;
    private View.OnClickListener clickListener;

    public SearchSortBy(TextView displayView, Activity activity) {
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
        current = sharedPref.getString(activity.getString(R.string.search_sort_by),
                activity.getString(R.string.search_sort_by_default_value));
        display();
    }

    private void toggle() {
        if (current.equals(ASC_VALUE)) {
            current = DESC_VALUE;
        } else {
            current = ASC_VALUE;
        }
        display();
    }

    private void update() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(R.string.search_sort_by), current);
        editor.commit();
    }

    private void display() {
        if (current.isEmpty()) {
            displayView.setText(activity.getResources().getString(R.string.desc));
        } else {
            displayView.setText(activity.getResources().getString(R.string.asc));
        }
    }

}

