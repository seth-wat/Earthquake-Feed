package wat.seth.dev.capstoneproject;

import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.twitter.sdk.android.core.Twitter;

import org.parceler.Parcels;

import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.fragments.DetailValuesFragment;
import wat.seth.dev.capstoneproject.interfaces.TakeSnapShot;
import wat.seth.dev.capstoneproject.utils.SocialHelper;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback, TakeSnapShot,
        GoogleMap.SnapshotReadyCallback {

    public static final String EARTHQUAKE_EXTRA = "earthquake_extra";
    public static final String MAP_CAMERA_POSITION = "map_camera_position";

    Earthquake earthquake;
    GoogleMap gm;
    CameraPosition cameraPosition;
    String socialType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Twitter.initialize(this);

        earthquake = Parcels.unwrap((Parcelable)getIntent().getExtras().get(EARTHQUAKE_EXTRA));

        getSupportActionBar().setSubtitle(earthquake.getReadablePlaceDistance(true) + " of " + earthquake.getReadablePlace());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        Add map fragment
         */
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        /*
        Add detail fragment
         */
        DetailValuesFragment f = DetailValuesFragment.instantiate(earthquake, this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detail_container, f)
                .commit();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gm = googleMap;

        LatLng location = new LatLng(earthquake.getLatitude(), earthquake.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(location));
        /*
        cameraPosition holds saved state
         */
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

    @Override
    public void onSnapshotReady(Bitmap bitmap) {
        switch(socialType) {
            case SocialHelper.TWITTER:
                SocialHelper.twitterShare(bitmap, DetailActivity.this, earthquake);
            default:
                return;
        }
    }

    /*
    Called inside Twitter FAB in details fragment.
     */
    @Override
    public void captureMap(String type) {
        socialType = type;
        gm.snapshot(this);
    }
}
