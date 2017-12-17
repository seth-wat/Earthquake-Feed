package wat.seth.dev.capstoneproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.DetailActivity;
import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.data.Earthquake;

/**
 * Created by seth-wat on 12/14/2017.
 */

public class QuakeListAdapter extends RecyclerView.Adapter<QuakeListAdapter.ViewHolder> {
    ArrayList<Earthquake> earthquakes = new ArrayList<>();
    Context mContext = null;

    public QuakeListAdapter(Context context) {
        mContext = context;
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
        holder.place.setText(String.valueOf(earthquake.getPlace()));
        String time = String.valueOf(earthquake.getTime()) + " UTC";
        holder.time.setText(time);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.EARTHQUAKE_EXTRA, Parcels.wrap(earthquakes.get(position)));
                mContext.startActivity(intent);
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
        ImageView magRep;
        TextView mag;
        TextView place;
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            magRep = itemView.findViewById(R.id.mag_representation);
            mag = itemView.findViewById(R.id.mag);
            place = itemView.findViewById(R.id.place);
            time = itemView.findViewById(R.id.time);
        }
    }
}
