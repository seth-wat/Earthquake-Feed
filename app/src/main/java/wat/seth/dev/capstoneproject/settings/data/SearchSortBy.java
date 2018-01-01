package wat.seth.dev.capstoneproject.settings.data;

/**
 * Created by seth-wat on 1/1/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import wat.seth.dev.capstoneproject.R;

public class SearchSortBy {
    /*
    Class controls updating search_sort_by shared preference, it's related view,
    and provides access to a click listener.

    search_sort_by holds one of two String values.
     */

    public static final String ASC_VALUE = "Ascending";
    public static final String DESC_VALUE = "Descending";

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
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        current = sharedPref.getString(activity.getString(R.string.search_sort_by),
                activity.getString(R.string.search_sort_by_default_value));
        displayView.setText(current);
    }

    private void toggle() {
        if (current == ASC_VALUE) {
            current = DESC_VALUE;
        } else {
            current = ASC_VALUE;
        }
        displayView.setText(current);
    }

    private void update() {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(activity.getString(R.string.search_sort_by), current);
        editor.commit();
    }

}

