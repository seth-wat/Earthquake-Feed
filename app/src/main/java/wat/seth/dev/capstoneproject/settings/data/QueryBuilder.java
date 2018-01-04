package wat.seth.dev.capstoneproject.settings.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.StringRes;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.net.URL;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;

import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.utils.NetworkUtils;

/**
 * Created by seth-wat on 1/2/2018.
 */


public class QueryBuilder {
    /*
    Class simply loads preferences and constructs query using them as params
    in the url.
     */
    public Activity activity;
    SharedPreferences sharedPreferences;

    public QueryBuilder(Activity activity) {
        this.activity = activity;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public String getBuiltUrl() {
        return build();
    }

    private String build() {
        Uri.Builder builder = Uri.parse(NetworkUtils.BASE_QUERY).buildUpon();

        builder.appendQueryParameter(activity.getString(R.string.end_time), get(R.string.search_end_date, R.string.search_end_date_default_value));
        builder.appendQueryParameter(activity.getString(R.string.start_time), get(R.string.search_start_date, R.string.search_start_date_default_value));
        builder.appendQueryParameter(activity.getString(R.string.max_mag), get(R.string.search_max_mag, R.string.search_max_mag_default_value));
        builder.appendQueryParameter(activity.getString(R.string.min_mag), get(R.string.search_min_mag, R.string.search_min_mag_default_value));

        String sortBy = get(R.string.search_sort_by, R.string.search_sort_by_default_value);
        builder.appendQueryParameter(activity.getString(R.string.order_by), get(R.string.search_order_by, R.string.search_order_by_default_value) + sortBy);

        builder.appendQueryParameter(activity.getString(R.string.max_results), get(R.string.search_max_results, R.string.search_max_results_default_value));


        return builder.build().toString();
    }

//    private boolean shouldReload(Map<String, ?> values) {
//        Map<String, ?> temp = fetchPreferences();
//        for (Map.Entry<String, ?> entry : values.entrySet()) {
//            String tempValue;
//            try {
//                tempValue = temp.get(entry.getKey()).toString();
//            } catch (IllegalStateException e) {
//                return true;
//            }
//
//            if (!tempValue.equals(entry.getValue().toString())) {
//                return true;
//            }
//        }
//        return false;
//    }

    private String get(@StringRes int key, @StringRes int def) {
        return sharedPreferences.getString(activity.getString(key), activity.getString(def));
    }


    private Map<String, ?> fetchPreferences() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return sharedPreferences.getAll();
    }
}
