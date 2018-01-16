package wat.seth.dev.capstoneproject.handlers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import wat.seth.dev.capstoneproject.R;

/**
 * Created by seth-wat on 1/1/2018.
 */

public class DatePick implements DatePickerDialog.OnDateSetListener {
    /*
    This class updates search_start_date and search_end_date preferences, their related views,
    and provides access to a click listener.
    */
    public static final String TYPE_START_DATE = "type_start_date";
    public static final String TYPE_END_DATE = "type_end_date";
    private final String defaultValue = "0";
    private String type;
    private String key;
    private Calendar rightNow;
    private Activity activity;
    private TextView displayView;
    private int month;
    private int day;
    private int year;
    private View.OnClickListener clickListener;

    public DatePick(TextView displayView, Activity activity, String type) {
        this.type = type;
        if (type == TYPE_START_DATE) {
            key = activity.getString(R.string.search_start_date);
        } else {
            key = activity.getString(R.string.search_end_date);
        }
        this.displayView = displayView;
        this.activity = activity;
        rightNow  = Calendar.getInstance();
        init();
        setClickListener(month, day, year);
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    private void init() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        String stringValue = sharedPref.getString(key, defaultValue);
        if (stringValue == defaultValue) {
            /*
            This initializes the shared preference if it has never
            been set.
             */
            month = rightNow.get(Calendar.MONTH) + 1;
            day = rightNow.get(Calendar.DAY_OF_MONTH);
            year = rightNow.get(Calendar.YEAR);
        } else {
            /*
            This just extracts month, day, year from the
            String stored in shared pref.
             */
            int firstIndex = stringValue.indexOf("-");
            year = Integer.valueOf(stringValue.substring(0, firstIndex));
            int nextIndex = stringValue.indexOf("-", firstIndex + 1);
            month = Integer.valueOf(stringValue.substring(firstIndex + 1, nextIndex));
            day = Integer.valueOf(stringValue.substring(nextIndex + 1)) - 1;
        }

        displayView.setText(getDisplayDate());
    }

    private void setClickListener(final int m, final int d, final int y) {
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        activity, DatePick.this, y, m - 1, d);
                datePickerDialog.getDatePicker().setMaxDate(rightNow.getTimeInMillis());
                datePickerDialog.show();
            }
        };
    }

    private String getDisplayDate() {
        //Formats string to display/store
        String dayString = day < 10 ? "0" + String.valueOf(day) : String.valueOf(day);
        String monthString = month  < 10 ? "0" + String.valueOf(month) : String.valueOf(month);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append("-");
        stringBuilder.append(monthString);
        stringBuilder.append("-");
        stringBuilder.append(dayString);
        return new String(stringBuilder);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        //Update UI and SharedPreferences
        month = m + 1;
        day = d;
        year = y;
        displayView.setText(getDisplayDate());
        day = day + 1;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, getDisplayDate());
        editor.commit();
    }
}
