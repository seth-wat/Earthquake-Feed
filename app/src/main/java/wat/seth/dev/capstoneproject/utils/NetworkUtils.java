package wat.seth.dev.capstoneproject.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;


/**
 * Created by seth-wat on 12/12/2017.
 */

public final class NetworkUtils {
    private static final String LOG = NetworkUtils.class.getSimpleName();

    public static final String BASE_QUERY = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake";
    public static final String WIDGET_QUERY = BASE_QUERY + "&orderby=magnitude&starttime=";
    public static final String TEST_QUERY = BASE_QUERY + "&starttime=2017-12-12&limit=20";
    public static final String GOOGLE_QUERY = "http://www.google.com/#q=news of earthquake ";


    public static URL urlFromString(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getResponseFromURL(String stringUrl) {
        URL url = urlFromString(stringUrl);
        StringBuilder response = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStreamReader isR = new InputStreamReader(urlConnection.getInputStream(), Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isR);
            String line = br.readLine();
            while (line != null) {
                response.append(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            Log.e(LOG, "Failed to get response: " + e.getMessage());
            return response.toString();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return response.toString();

    }

    public static boolean hasInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
