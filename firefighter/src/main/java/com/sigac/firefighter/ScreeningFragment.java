package com.sigac.firefighter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

    private Button mSubmitButton;

    private Victim mVictim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVictim = new Victim();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screening_fragment, container, false);

        mMaleButton = (Button) view.findViewById(R.id.button_male);
        mMaleButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVictim.setSex(Victim.Sex.MALE);
                return true;
            }
        });

        mFemaleButton = (Button) view.findViewById(R.id.button_female);
        mFemaleButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVictim.setSex(Victim.Sex.FEMALE);
                return true;
            }
        });

        mChildButton = (Button) view.findViewById(R.id.button_child);
        mChildButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVictim.setAge(Victim.Age.CHILD);
                return true;
            }
        });

        mYoungButton = (Button) view.findViewById(R.id.button_young);
        mYoungButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVictim.setAge(Victim.Age.YOUNG);
                return true;
            }
        });

        mAdultButton = (Button) view.findViewById(R.id.button_adult);
        mAdultButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVictim.setAge(Victim.Age.ADULT);
                return true;
            }
        });

        mOldButton = (Button) view.findViewById(R.id.button_old);
        mOldButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVictim.setAge(Victim.Age.OLD);
                return true;
            }
        });

        mSafeButton = (Button) view.findViewById(R.id.button_safe);
        mSafeButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVictim.setState(Victim.State.GREEN);
                return true;
            }
        });

        mInjuredbutton = (Button) view.findViewById(R.id.button_injured);
        mInjuredbutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVictim.setState(Victim.State.YELLOW);
                return true;
            }
        });

        mSevereButton = (Button) view.findViewById(R.id.button_severe);
        mSevereButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVictim.setState(Victim.State.RED);
                return true;
            }
        });

        mDeadButton = (Button) view.findViewById(R.id.button_dead);
        mDeadButton .setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mVictim.setState(Victim.State.BLACK);
                return true;
            }
        });

        mSubmitButton = (Button) view.findViewById(R.id.submit_button);
        mSubmitButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DbUtil.persistVictim(mVictim);
                return true;
            }
        });

        return view;
    }
}
