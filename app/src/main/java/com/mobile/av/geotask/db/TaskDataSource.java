package com.mobile.av.geotask.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;
import com.mobile.av.geotask.model.Item;
import com.mobile.av.geotask.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand on 4/8/2015.
 */
public class TaskDataSource {

    public static final String[] task_All_Column = {
            TaskDBOpenHelper.TASK_ID,
            TaskDBOpenHelper.TASK_TITLE,
            TaskDBOpenHelper.TASK_RANGE,
            TaskDBOpenHelper.TASK_NOTE,
            TaskDBOpenHelper.TASK_LOCATION
    };

    public static final String[] items_All_Column = {
            TaskDBOpenHelper.ITEMS_ID,
            TaskDBOpenHelper.TASK_ID,
            TaskDBOpenHelper.ITEMS_STATUS,
            TaskDBOpenHelper.ITEMS_NAME
    };

    private SQLiteOpenHelper taskDBOpenHelper;
    private SQLiteDatabase taskDB;

    public TaskDataSource(Context context) {
        taskDBOpenHelper = new TaskDBOpenHelper(context);
    }

    /*
    Open Database connection
     */
    public void open() {
        taskDB = taskDBOpenHelper.getWritableDatabase();
    }

    /*
    Close Database connection
     */
    public void close() {
        taskDBOpenHelper.close();
    }

    /*
    Return List of all Task
     */
    public List<Task> getAllFromTask() {
        Cursor cursor = taskDB.query(TaskDBOpenHelper.TASK_TABLE_NAME, task_All_Column, null, null, null, null, null);

        List<Task> tasks = new ArrayList<>();
        if (cursor.getCount() > 0) {
            Task task;
            ArrayList<LatLng> locationList;
            while (cursor.moveToNext()) {
                task = new Task();
                locationList = new ArrayList<>();
                task.setTask_id(cursor.getInt(cursor.getColumnIndex(TaskDBOpenHelper.TASK_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(TaskDBOpenHelper.TASK_TITLE)));
                task.setRange(cursor.getLong(cursor.getColumnIndex(TaskDBOpenHelper.TASK_RANGE)));
                task.setNote(cursor.getString(cursor.getColumnIndex(TaskDBOpenHelper.TASK_NOTE)));

                String locationString = cursor.getString(cursor.getColumnIndex(TaskDBOpenHelper.TASK_LOCATION));
                String[] split = locationString.split(";");
                  for (String string : split){
                      if(string.length() > 1) {
                          String[] latLong = string.split(",");
                          locationList.add(new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1])));
                      }
                  }
                task.setLocation(locationList);

                tasks.add(task);
            }
        }
        cursor.close();
        return tasks;
    }

    /*
    insert all data ------------- TEST TO INSERT DATA INTO DATABASE
     */
    public void setAllData(List<Task> tasks) {
        ContentValues values;
        for (Task task : tasks) {
            values = new ContentValues();

            values.put(TaskDBOpenHelper.TASK_TITLE, task.getTitle());
            values.put(TaskDBOpenHelper.TASK_RANGE, task.getRange());

            ArrayList<LatLng> latLngs = task.getLocation();
            StringBuilder sb = new StringBuilder();
            for (LatLng latLng : latLngs) {
                sb.append(latLng.latitude + ",");
                sb.append(latLng.longitude + ";");
            }
            values.put(TaskDBOpenHelper.TASK_LOCATION, sb.toString());

            long insertID = taskDB.insert(TaskDBOpenHelper.TASK_TABLE_NAME, null, values);

            ArrayList<Item> items = task.getItems();
            for(Item item: items) {
                values = new ContentValues();

                values.put(TaskDBOpenHelper.TASK_ID, insertID);
                values.put(TaskDBOpenHelper.ITEMS_STATUS, item.getStatus());
                values.put(TaskDBOpenHelper.ITEMS_NAME, item.getName());

                taskDB.insert(TaskDBOpenHelper.ITEMS_TABLE_NAME, null, values);
            }
        }
    }

    /*
    Remove Task from Both Tables
     */
    public void deleteTask(int taskId){
        taskDB.delete(TaskDBOpenHelper.TASK_TABLE_NAME,
                TaskDBOpenHelper.TASK_ID + " = ? ",
                new String[]{String.valueOf(taskId)});

        taskDB.delete(TaskDBOpenHelper.ITEMS_TABLE_NAME,
                TaskDBOpenHelper.TASK_ID + " = ? ",
                new String[]{String.valueOf(taskId)});

    }

    /**
     * Retrieves all the items of specified task.
     * @param taskId
     */
    public ArrayList<Item> getItemsForTask(int taskId){
        Cursor cursor = taskDB.query(TaskDBOpenHelper.ITEMS_TABLE_NAME,
                                        items_All_Column,
                                        TaskDBOpenHelper.TASK_ID + " = ?" ,
                                        new String[] {String.valueOf(taskId)},
                                        null, null, null);

        ArrayList<Item> itemList = new ArrayList<>();

        if (cursor.getCount() > 0){
            Item item;
            while (cursor.moveToNext()){
                item = new Item();
                item.setItem_id(cursor.getInt(cursor.getColumnIndex(TaskDBOpenHelper.ITEMS_ID)));
                item.setStatus(cursor.getInt(cursor.getColumnIndex(TaskDBOpenHelper.ITEMS_STATUS)));
                item.setName(cursor.getString(cursor.getColumnIndex(TaskDBOpenHelper.ITEMS_NAME)));
                itemList.add(item);
            }
        }
        cursor.close();
        return itemList;
    }
}