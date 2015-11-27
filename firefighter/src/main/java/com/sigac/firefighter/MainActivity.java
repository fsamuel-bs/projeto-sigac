package com.sigac.firefighter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private View mSearchButton;
    private MainFragmentPageAdapter mAdapter;
    private ViewGroup mActionBarContainer;
    private View mSearchIcon;
    private View mBackIcon;
    private Class<? extends Fragment> mFragmentBeforeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBarContainer = (ViewGroup) findViewById(R.id.action_bar_container);
        setActionBar(R.id.action_bar_default);

        mSearchIcon = findViewById(R.id.default_action_bar_search_icon);
        mSearchIcon.setOnClickListener(mOnSearchIconClickListener);

        mBackIcon = findViewById(R.id.search_action_bar_back_icon);
        mBackIcon.setOnClickListener(mOnBackIconClickListener);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new MainFragmentPageAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private View.OnClickListener mOnSearchIconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setActionBar(R.id.action_bar_search);
            mFragmentBeforeSearch = mAdapter.getItem(mViewPager.getCurrentItem()).getClass();
            goToFragment(SearchFragment.class);
        }
    };

    private View.OnClickListener mOnBackIconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setActionBar(R.id.action_bar_default);
            goToFragment(mFragmentBeforeSearch);
        }
    };

    private void setActionBar(int id) {
        for (int i = 0; i < mActionBarContainer.getChildCount(); i++) {
            View actionBar = mActionBarContainer.getChildAt(i);
            actionBar.setVisibility((actionBar.getId() == id) ? View.VISIBLE : View.GONE);
        }
    }

    public void goToVictim(Victim victim) {
        VictimFragment fragment = goToFragment(VictimFragment.class);
        fragment.selectVictim(victim);
    }

    public <T extends Fragment> T goToFragment(Class<T> fragmentClass) {
        int i = mAdapter.getFragmentIndex(fragmentClass);
        mViewPager.setCurrentItem(i);
        T fragment = fragmentClass.cast(mAdapter.getItem(i));
        return fragment;
    }
}
