package com.mobile.av.geotask;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.mobile.av.geotask.adapters.NewItemListArrayAdapter;
import com.mobile.av.geotask.helper.ListResize;
import com.mobile.av.geotask.model.Item;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TaskAddActivity extends ActionBarActivity implements NewItemListArrayAdapter.Listener, DatePickerFragment.DatePickerListener{

    ArrayList<Item> itemList;
    Item item;
    NewItemListArrayAdapter itemListArrayAdapter;
    ListView itemListView;
    EditText expirationDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_add);

        //Custom Action Bar
        setTitle(Html.fromHtml("<font color='#12cdc2'> Add Task </font>"));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        actionBar.setElevation(10);

        // Initialize editText for expiration date
        expirationDate = (EditText) findViewById(R.id.expires_editText_addTask);

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

    /**
     * Show DatePickerDialog when user clicks on Expiration date editText
     */
    public void showDatePickerDialog(View v){
        Bundle dateBundle = new Bundle();

        // If the date is selected then date dialog opens with that date else
        // with current date.
        if(expirationDate.getText().toString().equals("mm-dd-yyyy")){
            final Calendar c = Calendar.getInstance();
            dateBundle.putInt("month", c.get(Calendar.YEAR));
            dateBundle.putInt("day", c.get(Calendar.MONTH));
            dateBundle.putInt("year", c.get(Calendar.DAY_OF_MONTH));
        }else{
            String date[] = expirationDate.getText().toString().split("-");
            dateBundle.putInt("month", Integer.parseInt(date[0]));
            dateBundle.putInt("day", Integer.parseInt(date[1]));
            dateBundle.putInt("year", Integer.parseInt(date[2]));
        }

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setDatePickerListener(this);
        datePickerFragment.setArguments(dateBundle);
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
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

    /**
     * Callback of DatePicker Fragment.
     * @param date  String representing date in mm-dd-yyyy form
     */
    @Override
    public void datePickerReturnData(String date) {
        expirationDate.setText(date);
    }
}
