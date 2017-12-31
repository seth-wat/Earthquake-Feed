package wat.seth.dev.capstoneproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.settings.data.HandleIt;
import wat.seth.dev.capstoneproject.settings.data.NotifMag;
import wat.seth.dev.capstoneproject.settings.data.NotifRange;

/**
 * Created by seth-wat on 12/22/2017.
 */

public class SettingsFragment extends Fragment {
    Boolean rangeUpPress = false;
    Boolean rangeUpPressInner = false;
    Boolean rangeDownPress = false;
    public SettingsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        SwitchCompat notifToggleSwitch = view.findViewById(R.id.notif_toggle_switch);
        TextView rangeTextView = view.findViewById(R.id.range_value_text_view);
        TextView magTextView = view.findViewById(R.id.mag_value_text_view);

        Button rangeUpButton = view.findViewById(R.id.range_up_button);
        Button rangeDownButton = view.findViewById(R.id.range_down_button);
        Button magUpButton = view.findViewById(R.id.mag_up_button);
        Button magDownButton = view.findViewById(R.id.mag_down_button);

        NotifRange range = new NotifRange(getActivity(), rangeTextView, 25, 25);
        HandleIt handleRangeDecrement = new HandleIt(range, 1000, NotifRange.TYPE_DECREMENT);
        HandleIt handleRangeIncrement = new HandleIt(range, 1000, NotifRange.TYPE_INCREMENT);

        rangeDownButton.setOnTouchListener(handleRangeDecrement.getTouchListener());
        rangeUpButton.setOnTouchListener(handleRangeIncrement.getTouchListener());

        NotifMag mag = new NotifMag(getActivity(), magTextView, 0.1, 0.1);
        HandleIt handleMagDecrement = new HandleIt(mag, 1000, NotifMag.TYPE_DECREMENT);
        HandleIt handleMagIncrement = new HandleIt(mag, 1000, NotifMag.TYPE_INCREMENT);

        magDownButton.setOnTouchListener(handleMagDecrement.getTouchListener());
        magUpButton.setOnTouchListener(handleMagIncrement.getTouchListener());



        notifToggleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                /*
                write start / cease notification code
                 */
            }
        });
        return view;
    }


    //    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view =  inflater.inflate(R.layout.list_fragment, container, false);
//        rv = view.findViewById(R.id.my_rv);
//        rv.setLayoutManager(new GridLayoutManager(getContext(), 1));
//        rv.setAdapter(mAdapter);
//        fab = view.findViewById(R.id.world_map_fab);
//        return view;
//    }


}
