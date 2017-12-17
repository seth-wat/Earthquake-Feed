package wat.seth.dev.capstoneproject;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import wat.seth.dev.capstoneproject.adapters.LaunchScreenPagerAdapter;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0.f);
        ViewPager viewPager = findViewById(R.id.mlist);
        LaunchScreenPagerAdapter mAdapter = new LaunchScreenPagerAdapter(
                getSupportFragmentManager(), this);
        viewPager.setAdapter(mAdapter);


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
