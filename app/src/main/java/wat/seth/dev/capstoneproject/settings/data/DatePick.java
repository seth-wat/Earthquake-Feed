package wat.seth.dev.capstoneproject.settings.data;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

    private String key;
    private Calendar rightNow;
    private Activity activity;
    private TextView displayView;
    private int month;
    private int day;
    private int year;
    private View.OnClickListener clickListener;

    public DatePick(TextView displayView, Activity activity, String type) {
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
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String stringValue = sharedPref.getString(key, defaultValue);
        if (stringValue == defaultValue) {
            /*
            This initializes the shared preference if it has never
            been set.
             */
            month = rightNow.get(Calendar.MONTH);
            day = rightNow.get(Calendar.DAY_OF_MONTH);
            year = rightNow.get(Calendar.YEAR);
        } else {
            /*
            This just extracts month, day, year from the
            String stored in shared pref.
             */
            int firstIndex = stringValue.indexOf("-");
            month = Integer.valueOf(stringValue.substring(0, firstIndex));
            int nextIndex = stringValue.indexOf("-", firstIndex + 1);
            day = Integer.valueOf(stringValue.substring(firstIndex + 1, nextIndex));
            year = Integer.valueOf(stringValue.substring(nextIndex + 1));
        }

        displayView.setText(getDisplayDate());
    }

    private void setClickListener(final int m, final int d, final int y) {
        clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        activity, DatePick.this, y, m, d);
                datePickerDialog.getDatePicker().setMaxDate(rightNow.getTimeInMillis());
                datePickerDialog.show();
            }
        };
    }

    private String getDisplayDate() {
        //Formats string to display/store
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(month + 1);
        stringBuilder.append("-");
        stringBuilder.append(day);
        stringBuilder.append("-");
        stringBuilder.append(year);
        return new String(stringBuilder);
    }
    @Override
    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
        //Update UI and SharedPreferences
        month = m;
        day = d;
        year = y;
        String value = getDisplayDate();
        displayView.setText(getDisplayDate());
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
