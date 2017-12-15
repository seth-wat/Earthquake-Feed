package wat.seth.dev.capstoneproject.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
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
    private int loaderId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("asd", "I ran");
        mAdapter = new QuakeListAdapter(getContext());
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
        return view;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new ApiFetchLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        mAdapter.setEarthquakes(data);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    public static QuakeListFragment instantiate(int id) {
        QuakeListFragment qf = new QuakeListFragment();
        qf.loaderId = id;
        return qf;
    }
}
