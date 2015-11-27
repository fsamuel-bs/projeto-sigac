package com.sigac.firefighter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private View mSearchButton;
    private MainFragmentPageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().setHomeButtonEnabled(true);
        mSearchButton = findViewById(R.id.main_search_icon);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new MainFragmentPageAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mSearchButton.setOnClickListener(mOnSearchButtonClickListener);
    }

    private View.OnClickListener mOnSearchButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "Foo", Toast.LENGTH_LONG).show();
        }
    };

    /* TODO: LOL */
    public void goToVictim(Victim victim) {
        mViewPager.setCurrentItem(MainFragmentPageAdapter.VICTIM_TAB_INDEX);
        VictimFragment fragment = (VictimFragment) mAdapter.getItem(MainFragmentPageAdapter.VICTIM_TAB_INDEX);
        fragment.selectVictim(victim);
    }
}
