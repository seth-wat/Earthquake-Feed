package wat.seth.dev.capstoneproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.utils.JsonUtils;
import wat.seth.dev.capstoneproject.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                String raw = NetworkUtils.getResponseFromURL(NetworkUtils.TEST_QUERY);
                ArrayList<Earthquake> mEarthquakes = JsonUtils.parseJson(raw);
                if (mEarthquakes == null) {
                    return null;
                } else {
                    Log.v(TAG, mEarthquakes.get(0).getPlace());
                }
                return null;
            }
        }.execute();

    }
}
