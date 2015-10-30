package com.sigac.firefighter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class VictimFragment extends Fragment {

    private View vVictimId;
    private Spinner vVictimSex;
    private Spinner vVictimPriority;
    private EditText vVictimComment;
    private ArrayAdapter<CharSequence> mVictimPriorityAdapter;
    private ArrayAdapter<CharSequence> mVictimSexAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.victim_fragment, container, false);
        vVictimId = (EditText) view.findViewById(R.id.victim_id);
        vVictimComment = (EditText) view.findViewById(R.id.victim_comment);

        vVictimSex = (Spinner) view.findViewById(R.id.victim_sex);
        mVictimSexAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.victim_sex,
                android.R.layout.simple_spinner_item);
        vVictimSex.setAdapter(mVictimSexAdapter);


        vVictimPriority = (Spinner) view.findViewById(R.id.victim_priority);
        mVictimPriorityAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.victim_priorities,
                android.R.layout.simple_spinner_item);
        vVictimPriority.setAdapter(mVictimPriorityAdapter);

        return view;
    }
}
