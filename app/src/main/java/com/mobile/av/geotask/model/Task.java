package com.mobile.av.geotask.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Anand on 4/8/2015.
 */
public class Task {
    private int task_id;
    private String title;
    private ArrayList<Item> items;
    private long range;
    private Date expr_date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private boolean repeat;
    private ArrayList<LatLng> location;

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

    public Date getExpr_date() {
        return expr_date;
    }

    public void setExpr_date(Date expr_date) {
        this.expr_date = expr_date;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
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
