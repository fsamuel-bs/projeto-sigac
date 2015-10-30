package com.sigac.firefighter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.google.common.base.Throwables;

public class MainFragmentPageAdapter extends FragmentPagerAdapter {

    // Who needs types? :P
    private static Object[][] TABS = {
            {"Screening", ScreeningFragment.class},
            {"Search", SearchFragment.class},
    };

    private Context mContext;

    public MainFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public int getCount() {
        return TABS.length;
    }

    @Override
    public Fragment getItem(int position) {
        @SuppressWarnings("unchecked")
        Class<? extends Fragment> klass = (Class<? extends Fragment>) TABS[position][1];
        try {
            return klass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (String) TABS[position][0];
    }

}
