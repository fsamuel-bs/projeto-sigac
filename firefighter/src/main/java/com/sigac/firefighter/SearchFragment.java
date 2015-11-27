package com.sigac.firefighter;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.sigac.firefighter.model.ModelManager;
import com.sigac.firefighter.model.ObservableModelManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFragment extends Fragment {

    public static final VictimItem EMPTY_ITEM = new VictimItem("Empty", R.drawable.ic_action_halt_holo_light);
    public static final VictimItem LOADING_ITEM = new VictimItem("Loading...", R.drawable.ic_action_turn_right_holo_light);

    private ListView vVictimsList;
    private VictimItemAdapter mAdapter;
    private ObservableModelManager mModelManager;

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModelManager = ObservableModelManager.Factory.get();
        mModelManager.addObserver(mObserver);

        mContext = getActivity();
    }

    private ObservableModelManager.Observer mObserver = new ObservableModelManager.Observer() {
        @Override
        public void onChange(ModelManager m) {
            new FetchVictimsTask().execute();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.search_fragment, container, false);
        vVictimsList = (ListView) view.findViewById(R.id.search_victims_list);
        mAdapter = new VictimItemAdapter();


        setListItems();
        vVictimsList.setAdapter(mAdapter);
        vVictimsList.setOnItemClickListener(mOnItemClickListener);
        new FetchVictimsTask().execute();
        return view;
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            VictimItem item = mAdapter.getItem(position);
            if (item == null || item.victim == null) {
                return;
            }
            MainActivity main = (MainActivity) getActivity();
            main.goToVictim(item.victim);
        }
    };

    private class FetchVictimsTask extends AsyncTask<Void, Void, List<VictimItem>> {

        @Override
        protected void onPreExecute() {
            new Handler(mContext.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    setListItems(LOADING_ITEM);
                }
            });
        }

        @Override
        protected List<VictimItem> doInBackground(Void... params) {
            List<Victim> victims;
            try {
                victims = mModelManager.getVictims();
            } catch (Exception e) {
                return new ArrayList<>();
            }

            if (victims == null) {
                return null;
            }

            List<VictimItem> items = new ArrayList<>(victims.size());
            for (Victim victim : victims) {
                VictimItem item = new VictimItem(victim, victim.getName());
                items.add(item);
            }
            return items;
        }

        @Override
        protected void onPostExecute(final List<VictimItem> victimItems) {
            if (victimItems == null) {
                return;
            }

            new Handler(mContext.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    setListItems(victimItems);
                }
            });
        }
    }

    private void setListItems(List<VictimItem> victimItems) {
        mAdapter.mVictims.clear();
        mAdapter.mVictims.addAll(victimItems);
        if (victimItems.isEmpty()) {
            mAdapter.mVictims.add(EMPTY_ITEM);
        }
        mAdapter.mActiveVictims.clear();
        mAdapter.mActiveVictims.addAll(mAdapter.mVictims);
        mAdapter.notifyDataSetChanged();
    }

    private void setListItems(VictimItem... victimItems) {
        setListItems(Arrays.asList(victimItems));
    }

    public void search(String query) {
        query = query.trim();
        boolean changes = false;
        for (VictimItem item : mAdapter.mVictims) {
            if (item == EMPTY_ITEM || item == LOADING_ITEM) {
                continue;
            }
            boolean active = (query.length() <= 1)
                    || item.victim.getName().toLowerCase().startsWith(query.toLowerCase());
            if (active != item.active) {
                changes = true;
                item.active = active;
            }
        }
        if (changes) {
            mAdapter.notifyDataSetChanged();
        }

    }

    private static class VictimItem {
        public Victim victim;
        public String name;
        public boolean active = true;
        public int drawableId = R.drawable.ic_action_user_holo_light;

        public VictimItem(Victim victim, String name) {
            this.victim = victim;
            this.name = name;
        }

        public VictimItem(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private class VictimItemAdapter extends BaseAdapter {

        private List<VictimItem> mVictims = new ArrayList<>();
        private List<VictimItem> mActiveVictims = new ArrayList<>();

        @Override
        public int getCount() {
            return mActiveVictims.size();
        }

        @Override
        public VictimItem getItem(int position) {
            return mActiveVictims.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void notifyDataSetChanged() {
            mActiveVictims.clear();
            for (VictimItem victim : mVictims) {
                if (victim.active) {
                    mActiveVictims.add(victim);
                }
            }
            super.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.search_item_layout, parent, false);
            }
            // TODO: Use RecyclerView with the ViewHolder pattern
            VictimItemViewHolder viewHolder = (VictimItemViewHolder) view.getTag();
            if (viewHolder == null) {
                viewHolder = new VictimItemViewHolder();
                viewHolder.container = view;
                viewHolder.textView = (TextView) view.findViewById(R.id.search_item_name);
                view.setTag(viewHolder);
            }
            VictimItem item = getItem(position);
            viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(item.drawableId, 0, 0, 0);
            viewHolder.textView.setText(item.name);

            if (item.victim != null && item.victim.getStatus() != null) {
                int color = 0x00000000;
                switch (item.victim.getStatus()) {
                    case GREEN: color = getResources().getColor(R.color.safe_victim_bg); break;
                    case YELLOW: color = getResources().getColor(R.color.injured_victim_bg); break;
                    case RED: color = getResources().getColor(R.color.severe_victim_bg); break;
                    case BLACK: color = getResources().getColor(R.color.dead_victim_bg); break;
                }
                viewHolder.container.setBackgroundColor(color);
            }

            return view;
        }
    }

    private static class VictimItemViewHolder {
        public View container;
        public TextView textView;

    }
}
