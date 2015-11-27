package com.sigac.firefighter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.google.common.base.Preconditions;


/* TODO: Handle back button/state */
public class MainActivity extends AppCompatActivity {

    private static final float EPS = 0.001f;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private View mSearchButton;
    private MainFragmentPageAdapter mAdapter;
    private ViewGroup mActionBarContainer;
    private View mSearchIcon;
    private View mBackIcon;
    private Class<? extends Fragment> mFragmentBeforeSearch;
    private EditText mSearchQuery;
    private View mActionBarDefault;
    private View mActionBarSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBarContainer = (ViewGroup) findViewById(R.id.action_bar_container);
        setActionBar(R.id.action_bar_default);
        mActionBarSearch = findViewById(R.id.action_bar_search);
        mActionBarDefault = findViewById(R.id.action_bar_default);

        mSearchIcon = findViewById(R.id.default_action_bar_search_icon);
        mSearchIcon.setOnClickListener(mOnSearchIconClickListener);

        mSearchQuery = (EditText) findViewById(R.id.action_bar_search_query);
        mSearchQuery.addTextChangedListener(mOnSearchQueryChangeListener);


        mBackIcon = findViewById(R.id.search_action_bar_back_icon);
        mBackIcon.setOnClickListener(mOnBackIconClickListener);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new MainFragmentPageAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);

        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private TextWatcher mOnSearchQueryChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            Fragment fragment = mAdapter.getItem(mViewPager.getCurrentItem());
            Preconditions.checkState(fragment instanceof SearchFragment, "Modifying search query without search fragment active");
            SearchFragment searchFragment = (SearchFragment) fragment;
            searchFragment.search(s.toString());
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            /* TODO: Remove position dependency plz */
            if (position == MainFragmentPageAdapter.SCREENING_TAB_INDEX && position + 1 == MainFragmentPageAdapter.SEARCH_TAB_INDEX) {
                setAlpha(mActionBarDefault, 1 - positionOffset);
                setAlpha(mActionBarSearch, positionOffset);
            }
        }
    };

    private void setAlpha(View view, float alpha) {
        view.setAlpha(alpha);
        view.setVisibility((alpha < EPS) ? View.GONE : View.VISIBLE);
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
