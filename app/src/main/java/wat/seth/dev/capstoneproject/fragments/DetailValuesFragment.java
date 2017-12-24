package wat.seth.dev.capstoneproject.fragments;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twitter.sdk.android.core.Twitter;

import org.parceler.Parcels;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.interfaces.TakeSnapShot;
import wat.seth.dev.capstoneproject.loaders.SaveLoader;
import wat.seth.dev.capstoneproject.utils.ExpandingFabUtil;
import wat.seth.dev.capstoneproject.utils.SocialHelper;

/**
 * Created by seth-wat on 12/15/2017.
 */

public class DetailValuesFragment extends Fragment implements LoaderManager.LoaderCallbacks {
    public static final String SAVED_QUAKE = "saved_quake";
    private Earthquake earthquake;

    private boolean saved = false; //Tracks state of earthquake in db.

    private FloatingActionButton mainFab;
    private FloatingActionButton saveFab;
    private FloatingActionButton twitterFab;
    private FloatingActionButton searchFab;

    ExpandingFabUtil fabUtil;
    /*
    Interface called to take a snapshot of the map
    in DetailActivity and share.
     */
    TakeSnapShot takeSnapShot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(getContext());
        /*
        Check if the earthquake is saved in the db.
         */
        getActivity().getSupportLoaderManager().initLoader(SaveLoader.CHECK, null, this);

    }

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

        mainFab = view.findViewById(R.id.detail_options_fab);
        twitterFab = view.findViewById(R.id.twitter_fab);
        saveFab = view.findViewById(R.id.save_fab);
        searchFab = view.findViewById(R.id.search_fab);


        /*
        Populate views
         */
        magValue.setText(earthquake.getReadableMag());
        feltByValue.setText(String.valueOf(earthquake.getReadableFelt()));
        String location = String.valueOf(earthquake.getLatitude()) + ", \n"
                + String.valueOf(earthquake.getLongitude());
        locationValue.setText(location);
        depthValue.setText(String.valueOf(earthquake.getReadableDepth(true)));
        mmiValue.setText(earthquake.getReadableMmi());
        timeValue.setText(earthquake.getReadableDate() + "\n" + earthquake.getReadableTime());

        /*
        Handle FAB actions
         */

        fabUtil = new ExpandingFabUtil(false);
        fabUtil.appendFab(twitterFab);
        fabUtil.appendFab(saveFab);
        fabUtil.appendFab(searchFab);

        twitterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeSnapShot.captureMap(SocialHelper.TWITTER);
            }
        });

        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (saved) {
                    getActivity().getSupportLoaderManager().restartLoader(
                            SaveLoader.DELETE, null, DetailValuesFragment.this);
                } else {
                    getActivity().getSupportLoaderManager().restartLoader(
                            SaveLoader.SAVE, null, DetailValuesFragment.this);
                }
            }
        });

        searchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.google.com/#q=" + earthquake.getPlace());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        mainFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabUtil.animateFabs();
            }
        });

        return view;
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new SaveLoader(getContext(), earthquake);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        /*
        Handles checking/saving/removing earthquake from db.
         */
        /*
        Strange bug where loader.getId() was returning incorrect in onLoadFinished,
        this if block just represents switch case(SaveLoader.CHECK)
         */
        if (((SaveLoader) loader).wasCheck() && data != null && ((ArrayList<Earthquake>) data).size() != 0) {
            saved = true;
            Drawable d = getResources().getDrawable(R.drawable.ic_saved_24dp, null);
            saveFab.setImageDrawable(d);
            return;
        }
        switch (loader.getId()) {
            case SaveLoader.SAVE:
                if (data != null) {
                    saved = true;
                    Drawable d = getResources().getDrawable(R.drawable.ic_saved_24dp, null);
                    saveFab.setImageDrawable(d);
                    return;
                }
            case SaveLoader.DELETE:
                if (data != null && ((Integer) data) > 0) {
                    saved = false;
                    Drawable d = getResources().getDrawable(R.drawable.ic_save_black_24dp, null);
                    saveFab.setImageDrawable(d);
                    return;
                }
            default:
                return;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable p = Parcels.wrap(earthquake);
        outState.putParcelable(SAVED_QUAKE, p);
    }

    public static DetailValuesFragment instantiate(Earthquake e, TakeSnapShot capture) {
        DetailValuesFragment f = new DetailValuesFragment();
        f.earthquake = e;
        f.takeSnapShot = capture;
        return f;
    }

}
