package com.sigac.firefighter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.sigac.firefighter.model.ObservableModelManager;
import com.sigac.firefighter.view.ButtonGroup;

public class VictimFragment extends Fragment {

    private Button mMaleButton;
    private Button mFemaleButton;
    private ButtonGroup mSexGroup;

    private Button mChildButton;
    private Button mYoungButton;
    private Button mAdultButton;
    private Button mOldButton;
    private ButtonGroup mAgeGroup;

    private Button mSafeButton;
    private Button mInjuredButton;
    private Button mSevereButton;
    private Button mDeadButton;
    private ButtonGroup mStateGroup;

    private Button mSubmitButton;

    private TextView mTokenField;
    private EditText mNameField;

    private Victim mVictim;

    private ObservableModelManager mModelManager;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mModelManager = ObservableModelManager.Factory.get();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.victim_fragment, container, false);

        link(view);

        mSexGroup = new ButtonGroup();
        mAgeGroup = new ButtonGroup();
        mStateGroup = new ButtonGroup();

        setActions();

        return view;
    }

    private void link(View view) {
        mNameField = (EditText) view.findViewById(R.id.victim_name);
        mTokenField = (TextView) view.findViewById(R.id.id_field);

        mMaleButton = (Button) view.findViewById(R.id.button_male);
        mFemaleButton = (Button) view.findViewById(R.id.button_female);

        mChildButton = (Button) view.findViewById(R.id.button_child);
        mYoungButton = (Button) view.findViewById(R.id.button_young);
        mAdultButton = (Button) view.findViewById(R.id.button_adult);
        mOldButton = (Button) view.findViewById(R.id.button_old);

        mSafeButton = (Button) view.findViewById(R.id.button_safe);
        mInjuredButton = (Button) view.findViewById(R.id.button_injured);
        mSevereButton = (Button) view.findViewById(R.id.button_severe);
        mDeadButton = (Button) view.findViewById(R.id.button_dead);

        mSubmitButton = (Button) view.findViewById(R.id.submit_button);
    }

    private void setActions() {
        mMaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSexGroup.selectButton(mMaleButton);
                mVictim.setSex(Victim.Sex.MALE);
            }
        });
        mFemaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSexGroup.selectButton(mFemaleButton);
                mVictim.setSex(Victim.Sex.FEMALE);
            }
        });

        mChildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgeGroup.selectButton(mChildButton);
                mVictim.setAge(Victim.Age.CHILD);
            }
        });
        mYoungButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgeGroup.selectButton(mYoungButton);
                mVictim.setAge(Victim.Age.YOUNG);
            }
        });
        mAdultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgeGroup.selectButton(mAdultButton);
                mVictim.setAge(Victim.Age.ADULT);
            }
        });
        mOldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAgeGroup.selectButton(mOldButton);
                mVictim.setAge(Victim.Age.OLD);
            }
        });

        mSafeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateGroup.selectButton(mSafeButton);
                mVictim.setStatus(Victim.Status.GREEN);
            }
        });
        mInjuredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateGroup.selectButton(mInjuredButton);
                mVictim.setStatus(Victim.Status.YELLOW);
            }
        });
        mSevereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateGroup.selectButton(mSevereButton);
                mVictim.setStatus(Victim.Status.RED);
            }
        });
        mDeadButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStateGroup.selectButton(mDeadButton);
                mVictim.setStatus(Victim.Status.BLACK);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVictim.setToken(mTokenField.getText().toString());
                mVictim.setName(mNameField.getText().toString());
                new PersistVictimsTask().execute(new Victim(mVictim));
                clear();
            }
        });
    }

    private void clear() {
        mNameField.setText("");
        mTokenField.setText("");

        mSexGroup.deselect();
        mMaleButton.callOnClick();

        mAgeGroup.deselect();
        mAdultButton.callOnClick();

        mStateGroup.deselect();
        mSafeButton.callOnClick();

        mVictim = new Victim();
    }

    public void selectVictim(Victim victim) {
        mVictim = victim;

        mTokenField.setText(victim.getToken());
        mNameField.setText(victim.getName());

        switch (mVictim.getSex()) {
            case MALE: mMaleButton.callOnClick(); break;
            case FEMALE: mFemaleButton.callOnClick(); break;
        }

        switch (mVictim.getAge()) {
            case CHILD: mChildButton.callOnClick(); break;
            case YOUNG: mYoungButton.callOnClick(); break;
            case ADULT: mAdultButton.callOnClick(); break;
            case OLD: mOldButton.callOnClick(); break;
        }

        switch (mVictim.getStatus()) {
            case GREEN: mSafeButton.callOnClick(); break;
            case YELLOW: mInjuredButton.callOnClick(); break;
            case RED: mSevereButton.callOnClick(); break;
            case BLACK: mDeadButton.callOnClick(); break;
        }
    }

    private class PersistVictimsTask extends AsyncTask<Victim, Void, Void> {
        @Override
        protected Void doInBackground(Victim... params) {
            mModelManager.persistVictim(params[0]);
            return null;
        }
    }
}
