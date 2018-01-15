package wat.seth.dev.capstoneproject.widget;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;



import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Calendar;

import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.utils.JsonUtils;
import wat.seth.dev.capstoneproject.utils.NetworkUtils;

/**
 * Created by seth-wat on 1/4/2018.
 */

public class WidgetJobService extends JobService {
    public static final String EXTRA_EARTHQUAKE = "wat.seth.dev.capstoneproject.widget.EXTRA_EARTHQUAKE";
    public static final String ACTION_EARTHQUAKE = "wat.seth.dev.capstoneproject.widget.DATA";
    public static final int JOB_ID = 601304;
    private AsyncTask<Void, Void, Earthquake> backgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        backgroundTask = new AsyncTask<Void, Void, Earthquake>() {

            @Override
            protected Earthquake doInBackground(Void... voids) {


                String raw = NetworkUtils.getResponseFromURL(NetworkUtils.WIDGET_QUERY + getDate());
                ArrayList<Earthquake> eq =  JsonUtils.parseJson(raw);
                if (eq != null) return eq.get(0);
                return null;
            }

            @Override
            protected void onPostExecute(Earthquake earthquake) {
                Intent intent = new Intent(ACTION_EARTHQUAKE);
                intent.putExtra(EXTRA_EARTHQUAKE, Parcels.wrap(earthquake));
                sendBroadcast(intent);
                jobFinished(job, false);

            }

        }.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (backgroundTask != null) backgroundTask.cancel(true);
        return false;
    }

    private String getDate() {
        Calendar c = Calendar.getInstance();
        StringBuilder sB = new StringBuilder();
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        sB.append(c.get(Calendar.YEAR));
        sB.append("-");
        sB.append(month < 10 ? "0" + month : month);
        sB.append("-");
        sB.append(day < 10 ? "0" + day : day);
        return new String(sB);
    }
}
