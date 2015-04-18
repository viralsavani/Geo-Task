package com.mobile.av.geotask;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.mobile.av.geotask.adapters.NewItemListArrayAdapter;
import com.mobile.av.geotask.helper.ListResize;
import com.mobile.av.geotask.model.Item;

import java.util.ArrayList;


public class TaskAddActivity extends ActionBarActivity implements NewItemListArrayAdapter.Listener{

    ArrayList<Item> itemList;
    Item item;
    NewItemListArrayAdapter itemListArrayAdapter;
    ListView itemListView;

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
}
