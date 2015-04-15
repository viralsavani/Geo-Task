package com.mobile.av.geotask;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.mobile.av.geotask.model.Task;


public class MainActivity extends ActionBarActivity implements TaskListFragment.OnListItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Custom Action Bar
        setTitle(Html.fromHtml("<font color='#12cdc2'> Tasks </font>"));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        actionBar.setElevation(10);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                Intent prefsIntent = new Intent(this, PrefsActivity.class);
                startActivity(prefsIntent);
                break;
            case R.id.action_add_task:
                Intent addTaskIntent = new Intent(this, TaskAddActivity.class);
                startActivity(addTaskIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Implemented method from Task List Fragment
     */
    @Override
    public void onItemClicked(int position, Task task) {
        Intent listItemClickIntent = new Intent(this, TaskDetailActivity.class);
        listItemClickIntent.putExtra(".model.Task", task);
        startActivity(listItemClickIntent);
    }
}
