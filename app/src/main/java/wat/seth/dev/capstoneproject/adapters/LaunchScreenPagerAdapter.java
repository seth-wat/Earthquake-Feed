package wat.seth.dev.capstoneproject.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.fragments.QuakeListFragment;
import wat.seth.dev.capstoneproject.loaders.ApiFetchLoader;

/**
 * Created by seth-wat on 12/14/2017.
 */

public class LaunchScreenPagerAdapter extends FragmentPagerAdapter {
    Context mContext;

    public LaunchScreenPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return QuakeListFragment.instantiate(ApiFetchLoader.FETCH_FROM_API);
            case 1:
                return QuakeListFragment.instantiate(ApiFetchLoader.FETCH_FROM_PROVIDER);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return mContext.getResources().getString(R.string.recent_tab);
            case 1:
                return mContext.getResources().getString(R.string.saved_tab);
            default:
                return null;
        }
    }
}
