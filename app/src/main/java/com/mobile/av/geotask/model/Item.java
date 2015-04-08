package com.mobile.av.geotask.model;

/**
 * Created by Anand on 4/8/2015.
 */
public class Item {

    private int item_id;
    private boolean status = false;
    private String name;
    private String note;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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
}
