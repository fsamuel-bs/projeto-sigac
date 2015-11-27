package com.sigac.firefighter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private View mSearchButton;
    private MainFragmentPageAdapter mAdapter;
    private ViewGroup mActionBarContainer;
    private View mSearchIcon;
    private View mBackIcon;

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
            Toast.makeText(MainActivity.this, "Change fragment to search results", Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener mOnBackIconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setActionBar(R.id.action_bar_default);
            Toast.makeText(MainActivity.this, "Change fragment to previous one", Toast.LENGTH_LONG).show();
        }
    };

    private void setActionBar(int id) {
        for (int i = 0; i < mActionBarContainer.getChildCount(); i++) {
            View actionBar = mActionBarContainer.getChildAt(i);
            actionBar.setVisibility((actionBar.getId() == id) ? View.VISIBLE : View.GONE);
        }
    }

    public void goToVictim(Victim victim) {
        int i = mAdapter.getFragmentIndex(VictimFragment.class);
        mViewPager.setCurrentItem(i);
        VictimFragment fragment = (VictimFragment) mAdapter.getItem(MainFragmentPageAdapter.VICTIM_TAB_INDEX);
        fragment.selectVictim(victim);
    }
}
