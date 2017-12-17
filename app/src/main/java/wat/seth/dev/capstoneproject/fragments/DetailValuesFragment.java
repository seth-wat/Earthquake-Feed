package wat.seth.dev.capstoneproject.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.parceler.Parcels;

import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.data.Earthquake;

/**
 * Created by seth-wat on 12/15/2017.
 */

public class DetailValuesFragment extends Fragment {
    public static final String SAVED_QUAKE = "saved_quake";
    private Earthquake earthquake;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_values, container, false);

        if (savedInstanceState != null && savedInstanceState.getParcelable(SAVED_QUAKE) != null) {
            earthquake = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_QUAKE));
        }

        TextView magValue = view.findViewById(R.id.mag_value);
        TextView feltByValue = view.findViewById(R.id.felt_by_value);
        TextView locationValue = view.findViewById(R.id.location_value);
        TextView depthValue = view.findViewById(R.id.depth_value);
        TextView mmiValue = view.findViewById(R.id.mmi_value);
        TextView timeValue = view.findViewById(R.id.time_value);

        magValue.setText(String.valueOf(earthquake.getMag()));
        feltByValue.setText(String.valueOf(earthquake.getFelt()));
        String location = String.valueOf(earthquake.getLatitude()) + ", "
                + String.valueOf(earthquake.getLongitude());
        locationValue.setText(location);
        depthValue.setText(String.valueOf(earthquake.getDepth()));
        mmiValue.setText(String.valueOf(earthquake.getMmi()));
        timeValue.setText(String.valueOf(earthquake.getTime()));

        return view;
    }

    public static DetailValuesFragment instantiate(Earthquake e) {
        DetailValuesFragment f = new DetailValuesFragment();
        f.earthquake = e;
        return f;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable p = Parcels.wrap(earthquake);
        outState.putParcelable(SAVED_QUAKE, p);
    }
}
