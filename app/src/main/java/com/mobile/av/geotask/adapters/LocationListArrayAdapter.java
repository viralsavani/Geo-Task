package com.mobile.av.geotask.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.mobile.av.geotask.R;

import java.util.ArrayList;

/**
 * Created by VIRAL on 4/14/2015.
 */
public class LocationListArrayAdapter extends ArrayAdapter<LatLng> {

    private Context context;
    private ArrayList<LatLng> locationList;

    private TextView locationTextView;

    public LocationListArrayAdapter(Context context, int resource, ArrayList<LatLng> locationList) {
        super(context, resource, locationList);
        this.context = context;
        this.locationList = locationList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.item_detail_location_row, null, true);

        locationTextView = (TextView) convertView.findViewById(R.id.location_row_textView_taskDetail);
        locationTextView.setText(locationList.get(position).toString());

        return convertView;
    }
}
