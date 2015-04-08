package com.mobile.av.geotask.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public TaskDataSource(Context context){
        taskDBOpenHelper = new TaskDBOpenHelper(context);
    }

    /*
    Open Database connection
     */
    public void open(){
        taskDB = taskDBOpenHelper.getWritableDatabase();
    }

    /*
    Close Database connection
     */
    public void close(){
        taskDBOpenHelper.close();
    }
}
