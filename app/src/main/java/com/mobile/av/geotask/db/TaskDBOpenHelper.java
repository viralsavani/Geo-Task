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
    public static final String TASK_TABLE_NAME = "Task";
    public static final String TASK_ID = "task_id";
    public static final String TASK_TITLE = "title";
    public static final String TASK_RANGE = "range";
    public static final String TASK_EXP_DATE = "expr_date";
    public static final String TASK_REPEAT = "repeat";
    public static final String TASK_LOCATION = "location";

    // ItemList table column constants
    public static final String ITEMS_TABLE_NAME = "items";
    public static final String ITEMS_ID = "items_id";
    public static final String ITEMS_STATUS = "status";
    public static final String ITEMS_NAME = "name";
    public static final String ITEMS_NOTE = "note";

    // Query for creating tables
    public static final String TASK_TABLE_CREATE =
            "CREATE TABLE " + TASK_TABLE_NAME + " (" +
                    TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TASK_TITLE + " TEXT, " +
                    TASK_RANGE + " NUMERIC, " +
                    TASK_EXP_DATE + " DATE, " +
                    TASK_REPEAT + " BOOLEAN, " +
                    TASK_LOCATION + " TEXT " +
                    ")";

    public static final String ITEMS_TABLE_CREATE =
            "CREATE TABLE " + ITEMS_TABLE_NAME + " (" +
                    ITEMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TASK_ID + " INTEGER, " +
                    ITEMS_STATUS + " BOOLEAN, " +
                    ITEMS_NAME + " TEXT, " +
                    ITEMS_NOTE + " TEXT, " +
                    "FOREIGN KEY(" + TASK_ID + ") REFERENCES " + TASK_TABLE_NAME + "(" + TASK_ID + ") " +
                    ")";

    public TaskDBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_TABLE_CREATE);
        db.execSQL(ITEMS_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Code to handle database when version changes
         */
    }
}
