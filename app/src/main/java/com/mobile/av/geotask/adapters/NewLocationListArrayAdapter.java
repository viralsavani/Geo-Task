package com.mobile.av.geotask.adapters;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.mobile.av.geotask.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by VIRAL on 4/29/2015.
 */
public class NewLocationListArrayAdapter extends ArrayAdapter<LatLng> {

    private ArrayList<LatLng> locationList;
    private NewLocationListener newLocationListenerCallback;
    private MapFragmentListener mapFragmentListenerCallback;
    private Context context;


    public NewLocationListArrayAdapter(Context context, int resource, ArrayList<LatLng> locationList) {
        super(context, resource, locationList);
        this.locationList = locationList;
        this.context = context;
    }

    public interface NewLocationListener {
        void removeLocation(int position);
    }

    public interface MapFragmentListener {
        void addLocation(int position);
    }

    public void setNewLocationListener(NewLocationListener newLocationListenerCallback) {
        this.newLocationListenerCallback = newLocationListenerCallback;
    }

    public void setMapFragmentListener(MapFragmentListener mapFragmentListenerCallback) {
        this.mapFragmentListenerCallback = mapFragmentListenerCallback;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.location_add_list_row, null, true);

        // Handle location textView
        TextView locationTextView = (TextView) convertView.findViewById(R.id.title_textView_addLocation);
        LocationTextViewOnClickListener textViewOnClickListener = new LocationTextViewOnClickListener(position);
        locationTextView.setOnClickListener(textViewOnClickListener);

        if (locationList.get(position).latitude != 0 && locationList.get(position).longitude != 0) {
            Geocoder geocoder = new Geocoder(context);
            try {
                List<Address> address = geocoder
                        .getFromLocation(locationList.get(position).latitude, locationList.get(position).longitude, 1);
                locationTextView.setText(address.get(0).getAddressLine(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Handle location removeImageButton
        ImageButton imageButton = (ImageButton) convertView.findViewById(R.id.remove_imageButton_addLocation);
        RemoveOnClickListener removeListener = new RemoveOnClickListener(position);
        imageButton.setOnClickListener(removeListener);

        return convertView;
    }


    /**
     * OnClickListener for removeImageButton
     */
    private class RemoveOnClickListener implements View.OnClickListener {
        private int position;

        private RemoveOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            newLocationListenerCallback.removeLocation(position);
        }
    }

    /**
     * OnClickListener or LocationTextView
     */
    private class LocationTextViewOnClickListener implements View.OnClickListener {

        private int position;

        private LocationTextViewOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            mapFragmentListenerCallback.addLocation(position);
        }
    }
}
