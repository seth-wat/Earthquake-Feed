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
import wat.seth.dev.capstoneproject.handlers.EachLoop;
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
    /*
    Unfortunately when I designed this I did not think to fetch
    SharedPreferences on a separate thread. Needs to be fixed todo
     */
    private AdView mAdView;
    private ProgressBar exitProgressBar;
    private ProgressBar adviewProgressBar;
    private ScrollView scrollView;

    SearchMaxMag searchMaxMag;
    SearchMinMag searchMinMag;
    SearchNumResults searchNumResults;

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
        Button searchNumResultsUpButton = view.findViewById(R.id.search_max_results_up_button);
        Button searchNumResultsDownButton = view.findViewById(R.id.search_max_results_down_button);
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
        search min mag
         */
        searchMinMag = new SearchMinMag(getActivity(), searchMinMagView, 0.1, 0.1);

        searchMinMagDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMinMag.setType(EachLoop.TYPE_DECREMENT);
                searchMinMag.eachLoop();
                searchMinMag.finish();
            }
        });
        searchMinMagUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMinMag.setType(EachLoop.TYPE_INCREMENT);
                searchMinMag.eachLoop();
                searchMinMag.finish();

            }
        });
        /*
        search max mag
         */
        searchMaxMag = new SearchMaxMag(getActivity(), searchMaxMagView, 0.1, 0.1);

        searchMaxMagDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMaxMag.setType(EachLoop.TYPE_DECREMENT);
                searchMaxMag.eachLoop();
                searchMaxMag.finish();
            }
        });
        searchMaxMagUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMaxMag.setType(EachLoop.TYPE_INCREMENT);
                searchMaxMag.eachLoop();
                searchMaxMag.finish();

            }
        });

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
        searchNumResults = new SearchNumResults(getActivity(), searchMaxResultsView, 5, 5);
        searchNumResultsDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchNumResults.setType(EachLoop.TYPE_DECREMENT);
                searchNumResults.eachLoop();
                searchNumResults.finish();
            }
        });
        searchNumResultsUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchNumResults.setType(EachLoop.TYPE_INCREMENT);
                searchNumResults.eachLoop();
                searchNumResults.finish();

            }
        });
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
        Build and show ad
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
        //If the user exited the app via ad and returned progress bar should be hidden
        exitProgressBar.setVisibility(View.INVISIBLE);
    }
}
