package wat.seth.dev.capstoneproject;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import wat.seth.dev.capstoneproject.adapters.LaunchScreenPagerAdapter;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
        getSupportActionBar().setElevation(0.f);
        ViewPager viewPager = findViewById(R.id.mlist);
        LaunchScreenPagerAdapter mAdapter = new LaunchScreenPagerAdapter(
                getSupportFragmentManager(), this);
        viewPager.setAdapter(mAdapter);


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_options, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}
