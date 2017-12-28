package wat.seth.dev.capstoneproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.data.Earthquake;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {
    public static final String MAP_EARTHQUAKES_EXTRA = "map_earthquakes_extra";
    public static final String MAP_CAMERA_POSITION = "map_camera_position";

    ArrayList<Earthquake> earthquakes = new ArrayList<>();
    CameraPosition cameraPosition;
    GoogleMap gM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null && getIntent().getParcelableExtra(MAP_EARTHQUAKES_EXTRA) != null) {
            earthquakes = Parcels.unwrap(getIntent().getParcelableExtra(MAP_EARTHQUAKES_EXTRA));
        }

        if (savedInstanceState != null && savedInstanceState.getParcelable(MAP_CAMERA_POSITION) != null) {
            cameraPosition = Parcels.unwrap(savedInstanceState.getParcelable(MAP_CAMERA_POSITION));
        }

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.world_map_fragment);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gM = googleMap;
        LatLng loc = new LatLng(0, 0);
        for (int i = 0; i < earthquakes.size(); i++) {
            Earthquake e = earthquakes.get(i);
            loc = new LatLng(e.getLatitude(), e.getLongitude());
            Marker m = googleMap.addMarker(new MarkerOptions().position(loc).title(e.getPlace()));
            m.setTag(earthquakes.get(i));
        }

        if (cameraPosition != null) {
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 4.f));
        }

        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EARTHQUAKE_EXTRA, Parcels.wrap(marker.getTag()));
        startActivity(intent);
        return false;
    }

        @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MAP_EARTHQUAKES_EXTRA, Parcels.wrap(earthquakes));
        outState.putParcelable(MAP_CAMERA_POSITION, Parcels.wrap(cameraPosition));
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraPosition = gM.getCameraPosition();
    }


}