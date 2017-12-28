package wat.seth.dev.capstoneproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.dialogs.UnitPickerPreference;

/**
 * Created by seth-wat on 12/22/2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {

    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }


    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (preference instanceof UnitPickerPreference) {
            NumPickerDialogFragment dialogFragment = NumPickerDialogFragment.newInstance(preference.getKey());
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getFragmentManager(), "SDFSD");
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

}
