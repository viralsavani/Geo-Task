package com.mobile.av.geotask.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.mobile.av.geotask.R;
import com.mobile.av.geotask.model.Item;

import java.util.ArrayList;

/**
 * Created by VIRAL on 4/14/2015.
 */
public class ItemListArrayAdapter extends ArrayAdapter<Item> {

    private Context context;
    private ArrayList<Item> itemsList;

    public ItemListArrayAdapter(Context context, int resource, ArrayList<Item> itemsList) {
        super(context, resource, itemsList);
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.detail_view_item_row, null, true);

        return convertView;
    }
}
