package com.mobile.av.geotask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.mobile.av.geotask.adapters.NewItemListArrayAdapter;
import com.mobile.av.geotask.adapters.NewLocationListArrayAdapter;
import com.mobile.av.geotask.db.TaskDataSource;
import com.mobile.av.geotask.helper.ListResize;
import com.mobile.av.geotask.model.Item;
import com.mobile.av.geotask.model.Task;

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
    EditText title, note, range;
    Task task;
    TaskDataSource taskDataSource;


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

        title = (EditText) findViewById(R.id.title_editText_addTask);
        note = (EditText) findViewById(R.id.note_editText_addTask);
        range = (EditText) findViewById(R.id.range_editText_addTask);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        range.setText(sharedPreferences.getString(PrefsFragment.RANGE_PREF, "500"));

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
                if (saveTask()) {
                    task = new Task();
                    task.setTitle(title.getText().toString());
                    task.setItems(itemList);
                    task.setLocation(locationList);
                    task.setNote(note.getText().toString());
                    task.setRange(Long.parseLong(range.getText().toString()));
                    taskDataSource = new TaskDataSource(this);
                    taskDataSource.open();
                    taskDataSource.setAllData(task);
                    taskDataSource.close();
                    finish();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean saveTask() {
        if (title.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Enter Task Name", Toast.LENGTH_LONG).show();
            return false;
        } else if (locationList.size() == 0) {
            Toast.makeText(this, "Add at least one location", Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (locationList.get(0).longitude == 0 && locationList.get(0).latitude == 0) {
                Toast.makeText(this, "Add at least one location", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }

    @Override
    public void addLocation(int position) {
        Intent mapIntent = new Intent(this, MapActivity.class);
        mapIntent.putExtra(INDEX_IN_LOCATION_LIST, position);
        startActivityForResult(mapIntent, MAP_INTENT_GET_MSG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MAP_INTENT_GET_MSG:
                if (resultCode == Activity.RESULT_OK) {
                    LatLng location = data.getExtras().getParcelable(MapActivity.LATLNG_OF_LOCATION);
                    int locationIndex = data.getIntExtra(TaskAddActivity.INDEX_IN_LOCATION_LIST, -1);

                    if (locationIndex >= 0) {
                        locationList.set(locationIndex, location);
                        locationListArrayAdapter.notifyDataSetChanged();
                    }
                }
        }
    }
}