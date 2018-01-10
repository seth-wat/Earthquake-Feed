package wat.seth.dev.capstoneproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.handlers.HandleIt;
import wat.seth.dev.capstoneproject.handlers.NotifMag;
import wat.seth.dev.capstoneproject.handlers.SearchMaxMag;
import wat.seth.dev.capstoneproject.handlers.SearchMinMag;
import wat.seth.dev.capstoneproject.handlers.DatePick;
import wat.seth.dev.capstoneproject.handlers.SearchNumResults;
import wat.seth.dev.capstoneproject.handlers.SearchOrderBy;
import wat.seth.dev.capstoneproject.handlers.SearchSortBy;

/**
 * Created by seth-wat on 12/22/2017.
 */

public class SettingsFragment extends Fragment {
    private AdView mAdView;
    private ProgressBar exitProgressBar;
    private ProgressBar adviewProgressBar;
    private ScrollView scrollView;

    public SettingsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MobileAds.initialize(getContext(), getString(R.string.ads_test_key));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        /*
        All the views that display preference values.
         */
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
        Non value related views
         */
        exitProgressBar = view.findViewById(R.id.exit_progress_bar);
        mAdView = view.findViewById(R.id.adView);
        adviewProgressBar = view.findViewById(R.id.adView_progress_bar);
        scrollView = view.findViewById(R.id.scroll_settings);

        /*
        Each SharedPreference has a custom class that handles updating the preference values and UI.
        All SharedPreferences that both increment and decrement make use of the HandleIt class.
        Handle it allows for continuous updating while a button is pressed.

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

        /*
        Animate scroll view
         */
        Animation fromTop = AnimationUtils.loadAnimation(getContext(), R.anim.enter_from_top);
        scrollView.startAnimation(fromTop);

        /*
        Build and show add
         */
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adviewProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                exitProgressBar.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //If the user exited the app via add and returned progress bar should be hidden
        exitProgressBar.setVisibility(View.INVISIBLE);
    }
}
