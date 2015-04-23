package com.mobile.av.geotask;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.mobile.av.geotask.adapters.ItemListArrayAdapter;
import com.mobile.av.geotask.adapters.LocationListArrayAdapter;
import com.mobile.av.geotask.helper.ListResize;
import com.mobile.av.geotask.model.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment {

    private Task task;

    private TextView expirationDate;
    private TextView range;
    private TextView repeat;
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

        expirationDate = (TextView) taskDetailView.findViewById(R.id.expires_textView_taskDetail);
        range = (TextView) taskDetailView.findViewById(R.id.range_textView_taskDetail);
        repeat = (TextView) taskDetailView.findViewById(R.id.repeat_textView_taskDetail);
        notes = (TextView) taskDetailView.findViewById(R.id.note_textView_taskDetail);
        itemListView = (ListView) taskDetailView.findViewById(R.id.item_listView_taskDetail);
        locationListView = (ListView) taskDetailView.findViewById(R.id.location_listView_taskDetail);

        for (LatLng latLng : task.getLocation()){
            Log.d("--------------------->", latLng.toString());
        }


        expirationDate.setText(task.getExpr_date());
        range.setText(String.valueOf(task.getRange())+" mts");
        notes.setText(task.getNote());

        if (task.getRepeat() == 1){
            repeat.setText("YES");
        }else{
            repeat.setText("NO");
        }

        ItemListArrayAdapter itemAdapter = new ItemListArrayAdapter(getActivity(), R.layout.item_detail_fragment, task.getItems());
        itemListView.setAdapter(itemAdapter);
        ListResize.setListViewHeightBasedOnChildren(itemListView);

        LocationListArrayAdapter locationAdapter = new LocationListArrayAdapter(getActivity(), R.layout.item_detail_fragment, task.getLocation());
        locationListView.setAdapter(locationAdapter);
        ListResize.setListViewHeightBasedOnChildren(locationListView);

        return taskDetailView;
    }
}
