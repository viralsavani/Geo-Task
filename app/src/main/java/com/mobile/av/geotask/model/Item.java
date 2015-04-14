package com.mobile.av.geotask.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Anand on 4/8/2015.
 */
public class Item implements Parcelable{

    private int item_id;
    private int status = 0;
    private String name;
    private String note;

    public Item(){}

    public Item(Parcel in){
        item_id = in.readInt();
        status = in.readInt();
        name = in.readString();
        note = in.readString();
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ID: " + item_id +
                "\nStatus: " + status +
                "\nName: " + name +
                "\nNote: " + note;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(item_id);
        dest.writeInt(status);
        dest.writeString(name);
        dest.writeString(note);
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
