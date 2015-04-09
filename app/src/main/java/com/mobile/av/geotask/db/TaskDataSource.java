package com.mobile.av.geotask.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            TaskDBOpenHelper.TASK_EXP_DATE,
            TaskDBOpenHelper.TASK_REPEAT,
            TaskDBOpenHelper.TASK_LOCATION
    };

    public static final String[] items_All_Column = {
            TaskDBOpenHelper.ITEMS_ID,
            TaskDBOpenHelper.TASK_ID,
            TaskDBOpenHelper.ITEMS_STATUS,
            TaskDBOpenHelper.ITEMS_NAME,
            TaskDBOpenHelper.ITEMS_NOTE
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
            while (cursor.moveToNext()) {
                task = new Task();
                task.setTitle(cursor.getString(cursor.getColumnIndex(TaskDBOpenHelper.TASK_TITLE)));
                task.setRange(cursor.getLong(cursor.getColumnIndex(TaskDBOpenHelper.TASK_RANGE)));
                task.setExpr_date(cursor.getString(cursor.getColumnIndex(TaskDBOpenHelper.TASK_EXP_DATE)));
                task.setRepeat(cursor.getInt(cursor.getColumnIndex(TaskDBOpenHelper.TASK_REPEAT)));

                tasks.add(task);
            }
        }
        return tasks;
    }
}
