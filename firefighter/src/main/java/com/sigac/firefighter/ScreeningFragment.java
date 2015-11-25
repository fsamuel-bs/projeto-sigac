package com.sigac.firefighter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.sigac.firefighter.model.ObservableModelManager;
import com.sigac.firefighter.view.ButtonGroup;

// TODO: Save buttons state when pressed, there is no way to tell the gender of the current victim being created!
public class ScreeningFragment extends Fragment {

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

    private TextView mIdField;
    private Button mUpdateIdButton;
    private EditText mVictimName;

    private Victim mVictim;

    private ObservableModelManager mModelManager;

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModelManager = ObservableModelManager.Factory.get();
        mVictim = new Victim();
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screening_fragment, container, false);

        link(view);

        mSexGroup = new ButtonGroup();
        mAgeGroup = new ButtonGroup();
        mStateGroup = new ButtonGroup();

        setActions();
        new GetTagTask().execute();

        return view;
    }

    private void link(View view) {
        mVictimName = (EditText) view.findViewById(R.id.victim_name);
        mIdField = (TextView) view.findViewById(R.id.id_field);

        mUpdateIdButton = (Button) view.findViewById(R.id.button_update_tag);

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
        mUpdateIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetTagTask().execute();
            }
        });

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
                mVictim.setToken(mIdField.getText().toString());
                mVictim.setName(mVictimName.getText().toString());
                new PersistVictimsTask().execute();
                clear();
            }
        });

    }

    private void clear() {
        mVictimName.setText("");
        mIdField.setText("");

        mSexGroup.deselect();
        mAgeGroup.deselect();
        mStateGroup.deselect();

        mVictim = new Victim();
    }

    private class PersistVictimsTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mModelManager.persistVictim(mVictim);
            return null;
        }
    }

    private class GetTagTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            final String tag = mModelManager.getTag();
            new Handler(mContext.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    mIdField.setText(tag);
                }
            });
            return null;
        }
    }
}
