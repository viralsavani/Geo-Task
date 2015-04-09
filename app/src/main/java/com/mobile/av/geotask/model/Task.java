package com.mobile.av.geotask.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Anand on 4/8/2015.
 */
public class Task {

    private int task_id;
    private String title;
    private ArrayList<Item> items;
    private long range;
    private String expr_date;
    private int repeat = 0;
    private ArrayList<LatLng> location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public long getRange() {
        return range;
    }

    public void setRange(long range) {
        this.range = range;
    }

    public String getExpr_date() {
        return expr_date;
    }

    public void setExpr_date(String expr_date) {
        this.expr_date = expr_date;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public ArrayList<LatLng> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<LatLng> location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Title: " + title +
                "\nRange: " + range +
                "\nExpr_Date: " + expr_date.toString() +
                "\nRepeat: " + repeat;
    }
}
