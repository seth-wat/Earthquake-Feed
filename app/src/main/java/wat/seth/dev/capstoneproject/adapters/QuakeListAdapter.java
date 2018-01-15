package wat.seth.dev.capstoneproject.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.parceler.Parcels;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.DetailActivity;
import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.utils.ColorUtils;

/**
 * Created by seth-wat on 12/14/2017.
 */

public class QuakeListAdapter extends RecyclerView.Adapter<QuakeListAdapter.ViewHolder> {
    ArrayList<Earthquake> earthquakes = new ArrayList<>();
    Context mContext = null;
    Activity activity;

    public QuakeListAdapter(Context context, Activity activity) {
        mContext = context;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(mContext);
        View view = li.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Earthquake earthquake = earthquakes.get(position);

        holder.mag.setText(String.valueOf(earthquake.getMag()));
        holder.place.setText(earthquake.getReadablePlace());
        holder.placeDistance.setText(earthquake.getReadablePlaceDistance(true, mContext.getResources()));
        holder.time.setText(earthquake.getReadableDate() +
                 mContext.getResources().getString(R.string.space) + earthquake.getReadableTime());
        holder.magRep.setColorFilter(ColorUtils.setMagColor(earthquake.getMag(), mContext));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.EARTHQUAKE_EXTRA, Parcels.wrap(earthquakes.get(position)));
                Bundle b = ActivityOptions.makeSceneTransitionAnimation(activity).toBundle();
                mContext.startActivity(intent, b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return earthquakes.size();

    }

    public void setEarthquakes(ArrayList<Earthquake> e) {
        this.earthquakes = e;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mag;
        TextView place;
        TextView placeDistance;
        TextView time;
        ImageView magRep;

        public ViewHolder(View itemView) {
            super(itemView);
            magRep = itemView.findViewById(R.id.mag_representation);
            mag = itemView.findViewById(R.id.mag);
            place = itemView.findViewById(R.id.place);
            placeDistance = itemView.findViewById(R.id.place_distance);
            time = itemView.findViewById(R.id.time);
            magRep = itemView.findViewById(R.id.mag_representation);
        }
    }


}
