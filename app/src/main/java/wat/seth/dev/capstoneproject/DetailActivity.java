package wat.seth.dev.capstoneproject;

import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.fragments.DetailValuesFragment;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    Earthquake earthquake;
    GoogleMap gm;
    CameraPosition cameraPosition;
    public static final String EARTHQUAKE_EXTRA = "earthquake_extra";
    public static final String MAP_CAMERA_POSITION = "map_camera_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        earthquake = Parcels.unwrap((Parcelable)getIntent().getExtras().get(EARTHQUAKE_EXTRA));

        getSupportActionBar().setSubtitle(earthquake.getPlace());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        DetailValuesFragment f = DetailValuesFragment.instantiate(earthquake);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container, f)
                .commit();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gm = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        LatLng location = new LatLng(earthquake.getLatitude(), earthquake.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(location));

        if (cameraPosition != null) {
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 7.f));
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MAP_CAMERA_POSITION, Parcels.wrap(cameraPosition));
    }
}
