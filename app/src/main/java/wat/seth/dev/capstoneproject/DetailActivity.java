package wat.seth.dev.capstoneproject;

import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.twitter.sdk.android.core.Twitter;

import org.parceler.Parcels;

import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.fragments.DetailValuesFragment;
import wat.seth.dev.capstoneproject.interfaces.TakeSnapShot;
import wat.seth.dev.capstoneproject.utils.ColorUtils;
import wat.seth.dev.capstoneproject.utils.SocialHelper;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback, TakeSnapShot,
        GoogleMap.SnapshotReadyCallback {

    public static final String EARTHQUAKE_EXTRA = "earthquake_extra";
    public static final String MAP_CAMERA_POSITION = "map_camera_position";

    private Earthquake earthquake;
    private GoogleMap gM;
    private CameraPosition cameraPosition;
    private String socialType;

    private FrameLayout mapContainer;
    private ProgressBar twitterProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Twitter.initialize(this);

        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelable(MAP_CAMERA_POSITION) != null) {
                cameraPosition = Parcels.unwrap(savedInstanceState.getParcelable(MAP_CAMERA_POSITION));
            }
        }
        earthquake = Parcels.unwrap((Parcelable)getIntent().getExtras().get(EARTHQUAKE_EXTRA));
        //Set-up action bar
        setSupportActionBar((Toolbar) findViewById(R.id.detail_toolbar));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle(earthquake.getReadablePlaceDistance(true, getResources()) + " of " + earthquake.getReadablePlace());
        //Get references
        mapContainer = findViewById(R.id.map_cotainer);
        twitterProgressBar = findViewById(R.id.twitter_progress_bar);
        /*
        Add map fragment
         */
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        /*
        Add detail fragment
         */
        DetailValuesFragment f = DetailValuesFragment.instantiate(earthquake, this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, f)
                    .commit();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Slide map into view
        Animation fromTop = AnimationUtils.loadAnimation(this, R.anim.enter_from_top);
        mapContainer.startAnimation(fromTop);
        //Get reference to GoogleMap for saving sate
        gM = googleMap;
        //Map marker setup
        LatLng location = new LatLng(earthquake.getLatitude(), earthquake.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(ColorUtils.getMarkerColor(earthquake.getMag())));
        googleMap.addMarker(markerOptions);
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
        twitterProgressBar.setVisibility(View.VISIBLE);
        socialType = type;
        if (gM != null) {
            gM.snapshot(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        twitterProgressBar.setVisibility(View.INVISIBLE);
        if (gM != null) {
            cameraPosition = gM.getCameraPosition();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
