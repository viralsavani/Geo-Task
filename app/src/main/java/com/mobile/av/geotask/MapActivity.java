package com.mobile.av.geotask;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    MarkerOptions markerOptions;
    LatLng latLng;

    private TextView searchTextView;
    private ImageButton searchButton;

    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //Custom Action Bar
        setTitle(Html.fromHtml("<font color='#12cdc2'> Select Location </font>"));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        actionBar.setElevation(10);

        searchTextView = (TextView) findViewById(R.id.search_textView_addLocation);
        searchButton = (ImageButton) findViewById(R.id.search_imageButton_addLocation);

        // Handle onClick for search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address = searchTextView.getText().toString();

                if (address != null){
                    new GeoCoderTask().execute(address);
                }
            }
        });

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("DEFAULT"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save_location:
                // Save the Location selected by user
                // Perform a check here if user has selected a Location if not
                // take no action.
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private class GeoCoderTask extends AsyncTask<String, Void, List<Address>> {
        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of GeoCoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 10 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 10);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {

            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }else {
                // Clears all the existing markers on the map
                mMap.clear();

                // Adding Markers on Google Map for each matching address
                for (int i = 0; i < addresses.size(); i++) {

                    Address address = addresses.get(i);

                    // Creating an instance of GeoPoint, to display in Google Map
                    latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    String addressLine = "";
                    for (int j = 0; j < address.getMaxAddressLineIndex(); j++) {
                        addressLine = addressLine + address.getAddressLine(j);
                    }

                    String addressText = String.format(
                            addressLine,
                            address.getLocality(),
                            address.getCountryName());

                    markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title(address.getAddressLine(0));
                    markerOptions.snippet(addressText);

                    mMap.addMarker(markerOptions);

                    // Locate the first location
                    if (i == 0)
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        }
    }
}
