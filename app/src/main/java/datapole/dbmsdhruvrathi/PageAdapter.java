package datapole.dbmsdhruvrathi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by dhruv on 17/2/17.
 */
public class PageAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = "pagerAdapter";
    public static int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Log.d(TAG, "pos: " + position);
        switch (position) {
            case 0:
                return new MediaActivity();

            case 1:
                return new ShowDB();

            case 2:
                return new ShowDB();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}