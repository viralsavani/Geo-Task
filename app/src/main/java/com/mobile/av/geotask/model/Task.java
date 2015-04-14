package com.mobile.av.geotask.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Anand on 4/8/2015.
 */
public class Task implements Parcelable{

    private int task_id;
    private String title;
    private ArrayList<Item> items;
    private long range;
    private String expr_date;
    private int repeat = 0;
    private ArrayList<LatLng> location;

    public Task(){
        items = new ArrayList<>();
        location = new ArrayList<>();
    }

    public Task(Parcel in){
        this();
        task_id = in.readInt();
        title = in.readString();
        in.readTypedList(items,Item.CREATOR);
        range = in.readLong();
        expr_date = in.readString();
        repeat = in.readInt();
        in.readTypedList(location,LatLng.CREATOR);
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(task_id);
        dest.writeString(title);
        dest.writeTypedList(items);
        dest.writeLong(range);
        dest.writeString(expr_date);
        dest.writeInt(repeat);
        dest.writeTypedList(location);
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };
}
