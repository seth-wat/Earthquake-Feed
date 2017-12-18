package wat.seth.dev.capstoneproject.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.MapActivity;
import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.adapters.QuakeListAdapter;
import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.loaders.ApiFetchLoader;

/**
 * Created by seth-wat on 12/14/2017.
 */

public class QuakeListFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {
    private QuakeListAdapter mAdapter;
    private RecyclerView rv;
    FloatingActionButton fab;
    private int loaderId;
    public static final String EXTRA_LOADER_ID = "extra_loader_id";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new QuakeListAdapter(getContext());
        if (savedInstanceState != null && savedInstanceState.get(EXTRA_LOADER_ID) != null) {
            loaderId = savedInstanceState.getInt(EXTRA_LOADER_ID);
        }
        getActivity().getSupportLoaderManager().initLoader(
                loaderId, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.list_fragment, container, false);
        rv = view.findViewById(R.id.my_rv);
        rv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        rv.setAdapter(mAdapter);
        fab = view.findViewById(R.id.world_map_fab);
        return view;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new ApiFetchLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, final ArrayList<Earthquake> data) {
        mAdapter.setEarthquakes(data);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Parcelable p = Parcels.wrap(data);
                Intent intent = new Intent(getContext(), MapActivity.class);
                intent.putExtra(MapActivity.MAP_EARTHQUAKES_EXTRA, p);
                getActivity().startActivity(intent);
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
