package com.mobile.av.geotask;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mobile.av.geotask.adapters.ItemListArrayAdapter;
import com.mobile.av.geotask.adapters.LocationListArrayAdapter;
import com.mobile.av.geotask.helper.ListResize;
import com.mobile.av.geotask.model.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment {

    private Task task;

    private TextView range;
    private TextView notes;
    private ListView itemListView;
    private ListView locationListView;

    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        task = bundle.getParcelable(".model.Task");

        View taskDetailView = inflater.inflate(R.layout.item_detail_fragment, container, false);

        range = (TextView) taskDetailView.findViewById(R.id.range_textView_taskDetail);
        notes = (TextView) taskDetailView.findViewById(R.id.note_textView_taskDetail);
        itemListView = (ListView) taskDetailView.findViewById(R.id.item_listView_taskDetail);
        locationListView = (ListView) taskDetailView.findViewById(R.id.location_listView_taskDetail);

        range.setText(String.valueOf(task.getRange())+" mts");
        notes.setText(task.getNote());

        ItemListArrayAdapter itemAdapter = new ItemListArrayAdapter(getActivity(), R.layout.item_detail_fragment, task.getItems());
        itemListView.setAdapter(itemAdapter);
        ListResize.setListViewHeightBasedOnChildren(itemListView);

        LocationListArrayAdapter locationAdapter = new LocationListArrayAdapter(getActivity(), R.layout.item_detail_fragment, task.getLocation());
        locationListView.setAdapter(locationAdapter);
        ListResize.setListViewHeightBasedOnChildren(locationListView);

        return taskDetailView;
    }
}
