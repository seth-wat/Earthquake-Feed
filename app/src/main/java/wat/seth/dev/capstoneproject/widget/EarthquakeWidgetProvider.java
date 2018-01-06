package wat.seth.dev.capstoneproject.widget;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.parceler.Parcels;

import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.data.Earthquake;

/**
 * Implementation of App Widget functionality.
 */
public class EarthquakeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Earthquake earthquake) {
        //Update the UI.
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.earthquake_widget_provider);

        if (earthquake != null) {
            views.setTextViewText(R.id.widget_mag, String.valueOf(earthquake.getMag()));
            views.setTextViewText(R.id.widget_place, earthquake.getReadablePlace());
        } else {
            views.setTextViewText(R.id.widget_place, "An error occurred fetching the data ...");
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.hasExtra(WidgetJobService.EXTRA_EARTHQUAKE)) {
            //Job has finished extract data, get all active widgets.
            Earthquake earthquake = Parcels.unwrap(intent.getParcelableExtra(WidgetJobService.EXTRA_EARTHQUAKE));
            update(context, earthquake);
        }
    }

    @Override
    public void onUpdate( Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Fetch data from USGS API via JobScheduler.
        scheduleJob(context);
    }

    private void update(Context context, Earthquake earthquake) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(context, EarthquakeWidgetProvider.class));

        for (int id : ids) {
            updateAppWidget(context, appWidgetManager, id, earthquake);
        }
    }

    private void scheduleJob(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(WidgetJobService.JOB_ID, new ComponentName(context, WidgetJobService.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        jobScheduler.schedule(builder.build());
    }

}

