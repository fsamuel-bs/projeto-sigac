package com.sigac.firefighter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ScreeningFragment extends Fragment {

    private TextView mIdTextView;

    private Button mMaleButton;
    private Button mFemaleButton;

    private Button mChildButton;
    private Button mYoungButton;
    private Button mAdultButton;
    private Button mOldButton;

    private Button mSafeButton;
    private Button mInjuredbutton;
    private Button mSevereButton;
    private Button mDeadButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screening_fragment, container, false);

        mMaleButton = (Button) view.findViewById(R.id.button_male);
        mFemaleButton = (Button) view.findViewById(R.id.button_female);

        mChildButton = (Button) view.findViewById(R.id.button_child);
        mYoungButton = (Button) view.findViewById(R.id.button_young);
        mAdultButton = (Button) view.findViewById(R.id.button_male);
        mOldButton = (Button) view.findViewById(R.id.button_male);

        mSafeButton = (Button) view.findViewById(R.id.button_safe);
        mInjuredbutton = (Button) view.findViewById(R.id.button_injured);
        mSevereButton = (Button) view.findViewById(R.id.button_severe);
        mDeadButton = (Button) view.findViewById(R.id.button_dead);

        return view;
    }
}
