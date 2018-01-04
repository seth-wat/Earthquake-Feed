package wat.seth.dev.capstoneproject.loaders;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.db.Database;
import wat.seth.dev.capstoneproject.utils.JsonUtils;
import wat.seth.dev.capstoneproject.utils.NetworkUtils;
import wat.seth.dev.capstoneproject.handlers.QueryBuilder;

/**
 * Created by seth-wat on 12/14/2017.
 */

public class ApiFetchLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    public static final int FETCH_FROM_API = 8907;
    public static final int FETCH_FROM_PROVIDER = 8908;
    Activity activity;


    public ApiFetchLoader(Context context) {
        super(context);
    }
    public ApiFetchLoader(Context context, Activity activity) {
        super(context);
        this.activity = activity;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        switch(getId()) {
            case FETCH_FROM_API:
                String raw = NetworkUtils.getResponseFromURL(new QueryBuilder(activity).getBuiltUrl());
                return JsonUtils.parseJson(raw);
            case FETCH_FROM_PROVIDER:
                Cursor c = Database.fetchQuakes(getContext());
                return Earthquake.fromCursor(c);
            default:
                return null;
        }

    }

}
