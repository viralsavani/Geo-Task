package com.mobile.av.geotask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.mobile.av.geotask.adapters.NewItemListArrayAdapter;
import com.mobile.av.geotask.adapters.NewLocationListArrayAdapter;
import com.mobile.av.geotask.helper.ListResize;
import com.mobile.av.geotask.model.Item;

import java.util.ArrayList;


public class TaskAddActivity extends ActionBarActivity implements NewItemListArrayAdapter.Listener,
        NewLocationListArrayAdapter.NewLocationListener,
        NewLocationListArrayAdapter.MapFragmentListener {

    public static final String INDEX_IN_LOCATION_LIST = "indexInLocationList";

    ArrayList<Item> itemList;
    ArrayList<LatLng> locationList;
    Item item;
    LatLng location;
    NewItemListArrayAdapter itemListArrayAdapter;
    NewLocationListArrayAdapter locationListArrayAdapter;
    ListView itemListView;
    ListView locationListView;


    private static final int MAP_INTENT_GET_MSG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);

        //Custom Action Bar
        setTitle(Html.fromHtml("<font color='#12cdc2'> Add Task </font>"));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        actionBar.setElevation(10);

        // ItemList handlers
        itemList = new ArrayList<>();
        itemListArrayAdapter = new NewItemListArrayAdapter(this, R.layout.item_add_list_row, itemList);
        itemListArrayAdapter.setListener(this);
        itemListView = (ListView) findViewById(R.id.addItem_listView_addTask);
        itemListView.setAdapter(itemListArrayAdapter);

        // LocationList handlers
        locationList = new ArrayList<>();
        locationListArrayAdapter = new NewLocationListArrayAdapter(this, R.layout.location_add_list_row, locationList);
        locationListArrayAdapter.setNewLocationListener(this);
        locationListArrayAdapter.setMapFragmentListener(this);
        locationListView = (ListView) findViewById(R.id.addLocation_listView_addTask);
        locationListView.setAdapter(locationListArrayAdapter);
    }

    /*
    Method to add new Item in list
     */
    public void addNewItem(View v) {
        item = new Item();
        itemList.add(item);
        itemListArrayAdapter.notifyDataSetChanged();
        ListResize.setListViewHeightBasedOnChildren(itemListView);
    }

    /*
    Implemented method to remove item from list
     */
    @Override
    public void removeItem(int position) {
        itemList.remove(position);
        itemListArrayAdapter.notifyDataSetChanged();
        ListResize.setListViewHeightBasedOnChildren(itemListView);
    }

    /**
     * Method to add new location
     */
    public void addNewLocation(View v) {
        location = new LatLng(0, 0);
        locationList.add(location);
        locationListArrayAdapter.notifyDataSetChanged();
        ListResize.setListViewHeightBasedOnChildren(locationListView);
    }

    /**
     * Implemented method from NewLocationListArrayAdapter's interface
     */
    @Override
    public void removeLocation(int position) {
        locationList.remove(position);
        locationListArrayAdapter.notifyDataSetChanged();
        ListResize.setListViewHeightBasedOnChildren(locationListView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save:
                // Save the task
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addLocation() {
        Intent mapIntent = new Intent(this, MapActivity.class);
        int indexInLocationList = locationList.indexOf(location);
        mapIntent.putExtra(INDEX_IN_LOCATION_LIST, indexInLocationList);
        startActivityForResult(mapIntent, MAP_INTENT_GET_MSG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MAP_INTENT_GET_MSG:
                if (resultCode == Activity.RESULT_OK) {
                    LatLng location = data.getExtras().getParcelable(MapActivity.LATLNG_OF_LOCATION);
                    int locationIndex = data.getIntExtra(TaskAddActivity.INDEX_IN_LOCATION_LIST, -1);

                    if (locationIndex < 0) {
                        Log.e("Geo-Task", "Index to add location in location list is invalid :: -1");
                    } else {
                        locationList.add(locationIndex, location);
                        locationListArrayAdapter.notifyDataSetChanged();
                    }
                }
        }
    }
}
