package wat.seth.dev.capstoneproject.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.utils.JsonUtils;
import wat.seth.dev.capstoneproject.utils.NetworkUtils;

/**
 * Created by seth-wat on 12/14/2017.
 */

public class ApiFetchLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    public static final int FETCH_FROM_API = 8907;

    public ApiFetchLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        String raw = NetworkUtils.getResponseFromURL(NetworkUtils.TEST_QUERY);
        return JsonUtils.parseJson(raw);
    }
}
