package wat.seth.dev.capstoneproject.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;

import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.settings.data.HandleIt;
import wat.seth.dev.capstoneproject.settings.data.NotifMag;
import wat.seth.dev.capstoneproject.settings.data.NotifRange;
import wat.seth.dev.capstoneproject.settings.data.SearchMaxMag;
import wat.seth.dev.capstoneproject.settings.data.SearchMinMag;
import wat.seth.dev.capstoneproject.settings.data.DatePick;
import wat.seth.dev.capstoneproject.settings.data.SearchNumResults;
import wat.seth.dev.capstoneproject.settings.data.SearchOrderBy;
import wat.seth.dev.capstoneproject.settings.data.SearchSortBy;

/**
 * Created by seth-wat on 12/22/2017.
 */

public class SettingsFragment extends Fragment {

    public SettingsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        /*
        All the views that display preference values.
         */
        TextView rangeTextView = view.findViewById(R.id.notif_range_value);
        TextView magTextView = view.findViewById(R.id.notif_mag_value);
        TextView searchMinMagView = view.findViewById(R.id.search_min_mag_value);
        TextView searchMaxMagView = view.findViewById(R.id.search_max_mag_value);
        TextView searchStartDateView = view.findViewById(R.id.search_start_date_value);
        TextView searchEndDateView = view.findViewById(R.id.search_end_date_value);
        TextView searchMaxResultsView = view.findViewById(R.id.search_max_results_value);
        TextView searchOrderByView = view.findViewById(R.id.search_order_by_value);
        TextView searchSortByView = view.findViewById(R.id.search_sort_by_value);

        /*
        All the buttons that update preferences.
         */
        SwitchCompat notifToggleSwitch = view.findViewById(R.id.notif_toggle_switch);
        Button rangeUpButton = view.findViewById(R.id.notif_range_up_button);
        Button rangeDownButton = view.findViewById(R.id.notif_range_down_button);
        Button magUpButton = view.findViewById(R.id.notif_mag_up_button);
        Button magDownButton = view.findViewById(R.id.notif_mag_down_button);
        Button searchMinMagUpButton = view.findViewById(R.id.search_min_mag_up_button);
        Button searchMinMagDownButton = view.findViewById(R.id.search_min_mag_down_button);
        Button searchMaxMagUpButton = view.findViewById(R.id.search_max_mag_up_button);
        Button searchMaxMagDownButton = view.findViewById(R.id.search_max_mag_down_button);
        Button searchStartDateButton = view.findViewById(R.id.search_start_date_button);
        Button searchEndDateButton = view.findViewById(R.id.search_end_date_button);
        Button searchMaxResultsUpButton = view.findViewById(R.id.search_max_results_up_button);
        Button searchMaxResultsDownButton = view.findViewById(R.id.search_max_results_down_button);
        Button searchOrderByButton = view.findViewById(R.id.search_order_by_button);
        Button searchSortbyButton = view.findViewById(R.id.search_sort_by_button);

        /*
        Each SharedPreference has a custom class that handles updating the preference values and UI.
        All SharedPreferences that both increment and decrement make use of the HandleIt class.
        Handle it allows for continuous updating while a button is pressed.


        notification range
         */
        NotifRange range = new NotifRange(getActivity(), rangeTextView, 25, 25);
        HandleIt handleRangeDecrement = new HandleIt(range, 1000, NotifRange.TYPE_DECREMENT);
        HandleIt handleRangeIncrement = new HandleIt(range, 1000, NotifRange.TYPE_INCREMENT);

        rangeDownButton.setOnTouchListener(handleRangeDecrement.getTouchListener());
        rangeUpButton.setOnTouchListener(handleRangeIncrement.getTouchListener());

        /*
        notification mag
         */
        NotifMag mag = new NotifMag(getActivity(), magTextView, 0.1, 0.1);
        HandleIt handleMagDecrement = new HandleIt(mag, 1000, NotifMag.TYPE_DECREMENT);
        HandleIt handleMagIncrement = new HandleIt(mag, 1000, NotifMag.TYPE_INCREMENT);

        magDownButton.setOnTouchListener(handleMagDecrement.getTouchListener());
        magUpButton.setOnTouchListener(handleMagIncrement.getTouchListener());

        /*
        search min mag
         */
        SearchMinMag searchMinMag = new SearchMinMag(getActivity(), searchMinMagView, 0.1, 0.1);
        HandleIt handleSearchMinMagDecrement = new HandleIt(searchMinMag, 1000, NotifMag.TYPE_DECREMENT);
        HandleIt handleSearchMinMagIncrement = new HandleIt(searchMinMag, 1000, NotifMag.TYPE_INCREMENT);

        searchMinMagDownButton.setOnTouchListener(handleSearchMinMagDecrement.getTouchListener());
        searchMinMagUpButton.setOnTouchListener(handleSearchMinMagIncrement.getTouchListener());

        /*
        search max mag
         */
        SearchMaxMag searchMaxMag = new SearchMaxMag(getActivity(), searchMaxMagView, 0.1, 0.1);
        HandleIt handleSearchMaxMagDecrement = new HandleIt(searchMaxMag, 1000, NotifMag.TYPE_DECREMENT);
        HandleIt handleSearchMaxMagIncrement = new HandleIt(searchMaxMag, 1000, NotifMag.TYPE_INCREMENT);

        searchMaxMagDownButton.setOnTouchListener(handleSearchMaxMagDecrement.getTouchListener());
        searchMaxMagUpButton.setOnTouchListener(handleSearchMaxMagIncrement.getTouchListener());

        /*
        search start date
         */
        DatePick startDate = new DatePick(searchStartDateView, getActivity(), DatePick.TYPE_START_DATE);
        searchStartDateButton.setOnClickListener(startDate.getClickListener());

        /*
        search end date
         */
        DatePick endDate = new DatePick(searchEndDateView, getActivity(), DatePick.TYPE_END_DATE);
        searchEndDateButton.setOnClickListener(endDate.getClickListener());

        /*
        search num results
         */
        SearchNumResults searchNumResults = new SearchNumResults(getActivity(), searchMaxResultsView, 5, 5);
        HandleIt handleItResultsDecrement = new HandleIt(searchNumResults, 1000, SearchNumResults.TYPE_DECREMENT);
        HandleIt handleItResultsIncrement = new HandleIt(searchNumResults, 1000, SearchNumResults.TYPE_INCREMENT);

        searchMaxResultsDownButton.setOnTouchListener(handleItResultsDecrement.getTouchListener());
        searchMaxResultsUpButton.setOnTouchListener(handleItResultsIncrement.getTouchListener());

        /*
        search order by
         */
        SearchOrderBy searchOrderBy = new SearchOrderBy(searchOrderByView, getActivity());
        searchOrderByButton.setOnClickListener(searchOrderBy.getClickListener());

        /*
        search sort by
         */
        SearchSortBy searchSortBy = new SearchSortBy(searchSortByView, getActivity());
        searchSortbyButton.setOnClickListener(searchSortBy.getClickListener());

        return view;
    }


}
