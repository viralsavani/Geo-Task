package com.mobile.av.geotask.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anand on 4/8/2015.
 */
public class TaskDBOpenHelper extends SQLiteOpenHelper {

    // DB constants
    public static final String DB_NAME = "geotask.db";
    public static final int VERSION = 1;

    // Task table column constants
    public static final String TASK_TABLE = "task";

    public TaskDBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
