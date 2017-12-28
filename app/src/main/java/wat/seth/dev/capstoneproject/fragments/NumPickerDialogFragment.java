package wat.seth.dev.capstoneproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.dialogs.UnitPickerPreference;

/**
 * Created by seth-wat on 12/27/2017.
 */

public class NumPickerDialogFragment extends PreferenceDialogFragmentCompat {
    UnitPickerPreference preference;

    TextView valueView;
    TextView nextView;
    TextView prevView;

    float currentVal;
    float prevVal;
    float nextVal;

    public static NumPickerDialogFragment newInstance(String key) {
        NumPickerDialogFragment nP = new NumPickerDialogFragment();
        Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        nP.setArguments(b);

        return nP;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentVal = savedInstanceState.getFloat("current");
            nextVal = savedInstanceState.getFloat("next");
            prevVal = savedInstanceState.getFloat("prev");
        }
    }



    @Override
    protected void onBindDialogView(View view) {
        valueView = view.findViewById(R.id.current_number_text_view);
        nextView = view.findViewById(R.id.next_number_text_view);
        prevView = view.findViewById(R.id.previous_number_text_view);
        ImageView valueUpButton = view.findViewById(R.id.value_up_button);

        preference = (UnitPickerPreference) getPreference();
        currentVal = preference.getCurrent();
        nextVal = currentVal + .200000f;
        prevVal = currentVal - .200000f;

        valueUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementValues();
                updateViews();
            }
        });

        updateViews();

    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        String s = formatNum(String.valueOf(currentVal));
        if(preference.callChangeListener(s)) {
            preference.saveValue(s);
        }
    }

    public void updateViews() {
        preference.setValue(formatNum(String.valueOf(currentVal)));
        prevView.setText(formatNum(String.valueOf(prevVal)));
        valueView.setText(formatNum(String.valueOf(currentVal)));
        nextView.setText(formatNum(String.valueOf(nextVal)));
    }

    public void incrementValues() {
        nextVal = nextVal + .200000f;
        prevVal = prevVal + .200000f;
        currentVal = currentVal + .200000f;
    }

    public void decrementValues() {
        nextVal = nextVal - .200000f;
        prevVal = prevVal - .200000f;
        currentVal = currentVal - .200000f;
    }

    public String formatNum(String val) {
        return val.substring(0, val.indexOf(".") + 2);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putFloat("current", currentVal);
        outState.putFloat("next", nextVal);
        outState.putFloat("prev", prevVal);
        super.onSaveInstanceState(outState);
    }
}
