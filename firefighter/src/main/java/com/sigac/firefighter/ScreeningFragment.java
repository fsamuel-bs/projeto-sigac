package com.sigac.firefighter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ScreeningFragment extends Fragment {

    private Button mMaleButton;
    private Button mFemaleButton;

    private Button mChildButton;
    private Button mYoungButton;
    private Button mAdultButton;
    private Button mOldButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screening_fragment, container, false);
        return view;
    }
}
