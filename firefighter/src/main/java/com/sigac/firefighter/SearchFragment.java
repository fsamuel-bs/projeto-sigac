package com.sigac.firefighter;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private ListView vVictimsList;
    private ArrayAdapter<VictimItem> mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.search_fragment, container, false);
        vVictimsList = (ListView) view.findViewById(R.id.search_victims_list);
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        vVictimsList.setAdapter(mAdapter);
        vVictimsList.setOnItemClickListener(mOnItemClickListener);
        new FetchVictimsTask().execute();
        return view;
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            VictimItem item = mAdapter.getItem(position);
            Toast.makeText(getActivity(), item.name, Toast.LENGTH_LONG).show();
        }
    };

    private class FetchVictimsTask extends AsyncTask<Void, Void, List<VictimItem>> {

        @Override
        protected void onPreExecute() {
            mAdapter.add(new VictimItem("Loading..."));
        }

        @Override
        protected List<VictimItem> doInBackground(Void... params) {
            List<Victim> victims;
            try {
                victims = DbUtil.getVictims();
            } catch (Exception e) {
                Log.e("SIGAC", "Error " + e.getMessage(), e);
                return new ArrayList<>();
            }
            List<VictimItem> items = new ArrayList<>(victims.size());
            for (Victim victim : victims) {
                VictimItem item = new VictimItem(victim, victim.getName());
                items.add(item);
            }
            return items;
        }

        @Override
        protected void onPostExecute(List<VictimItem> victimItems) {
            mAdapter.clear();
            mAdapter.addAll(victimItems);
            mAdapter.notifyDataSetChanged();
        }
    }

    private static class VictimItem {
        public Victim victim;
        public String name;

        public VictimItem(Victim victim, String name) {
            this.victim = victim;
            this.name = name;
        }

        public VictimItem(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
