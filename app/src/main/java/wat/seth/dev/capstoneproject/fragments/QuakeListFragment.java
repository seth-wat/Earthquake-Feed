package wat.seth.dev.capstoneproject.fragments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.MapActivity;
import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.adapters.QuakeListAdapter;
import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.loaders.ApiFetchLoader;
import wat.seth.dev.capstoneproject.utils.NetworkUtils;

/**
 * Created by seth-wat on 12/14/2017.
 */
/*
 Fragment that contains the recent or saved list of earthquakes.
 */
public class QuakeListFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {
    private QuakeListAdapter mAdapter;
    private FrameLayout errorView;
    private Button errorViewButton;
    private TextView errorViewText;
    private ImageView errorImageView;
    private RecyclerView rv;
    FloatingActionButton fab;
    ProgressBar progressBar;
    private int loaderId;
    public static final String EXTRA_LOADER_ID = "extra_loader_id";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.get(EXTRA_LOADER_ID) != null) {
            loaderId = savedInstanceState.getInt(EXTRA_LOADER_ID);
        }
        mAdapter = new QuakeListAdapter(getContext(), getActivity());
    }

    @Override
    public void onResume() {
        if (!NetworkUtils.hasInternet(getContext())) {
            progressBar.setVisibility(View.INVISIBLE);
        }
        /*
        The loader is behaving incorrectly when returning from SettingsActivity and no internet
        connection, unfortunately without a big rewrite loader needs to be reset here. TODO

        I think this has something to do with FragmentPagerAdapter.
        */
        getActivity().getSupportLoaderManager().restartLoader(loaderId, null, this);
        super.onResume();

    }

    @Override
    public void onPause() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.list_fragment, container, false);
        //get view references
        rv = view.findViewById(R.id.my_rv);
        errorView = view.findViewById(R.id.error_screen);
        errorViewButton = view.findViewById(R.id.error_retry_button);
        errorViewText = view.findViewById(R.id.error_display_message);
        errorImageView = view.findViewById(R.id.error_image_view);
        fab = view.findViewById(R.id.world_map_fab);
        progressBar = view.findViewById(R.id.list_progress_bar);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(mAdapter);

        getActivity().getSupportLoaderManager().initLoader(
                loaderId, null, this);

        return view;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new ApiFetchLoader(getContext(), getActivity());

    }

    @Override
    public void onLoadFinished(final Loader<ArrayList<Earthquake>> loader, final ArrayList<Earthquake> data) {
        //If the data is null determine source of data, handle appropriately, and do not update adapter.
        progressBar.setVisibility(View.INVISIBLE);
        if (data == null) {
            if (loader.getId() == ApiFetchLoader.FETCH_FROM_API) {
                errorView.setVisibility(View.VISIBLE);
                errorViewButton.setVisibility(View.VISIBLE);
                errorViewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().getSupportLoaderManager().restartLoader(ApiFetchLoader.FETCH_FROM_API,
                                null, QuakeListFragment.this);
                    }
                });
            } else if (loader.getId() == ApiFetchLoader.FETCH_FROM_PROVIDER) {
                errorView.setVisibility(View.VISIBLE);
                errorViewText.setText(getString(R.string.no_saved));
                errorViewButton.setVisibility(View.INVISIBLE);
                errorImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bookmark_background));
                errorViewButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().getSupportLoaderManager().restartLoader(ApiFetchLoader.FETCH_FROM_API,
                                null, QuakeListFragment.this);
                    }
                });
            }
            return;
        }
        //end error checking code
        errorView.setVisibility(View.INVISIBLE);
        mAdapter.setEarthquakes(data);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Parcelable p = Parcels.wrap(data);
                Intent intent = new Intent(getContext(), MapActivity.class);
                intent.putExtra(MapActivity.MAP_EARTHQUAKES_EXTRA, p);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();
                getActivity().startActivity(intent, b);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    public static QuakeListFragment instantiate(int id) {
        QuakeListFragment qf = new QuakeListFragment();
        qf.loaderId = id;
        return qf;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_LOADER_ID, loaderId);
    }

}
