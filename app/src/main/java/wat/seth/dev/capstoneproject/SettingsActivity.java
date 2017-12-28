package wat.seth.dev.capstoneproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import wat.seth.dev.capstoneproject.fragments.SettingsFragment;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.preference_holder, new SettingsFragment())
                    .commit();
        }
    }
}
