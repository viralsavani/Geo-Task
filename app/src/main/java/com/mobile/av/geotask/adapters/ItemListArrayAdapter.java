package com.mobile.av.geotask.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mobile.av.geotask.R;
import com.mobile.av.geotask.model.Item;

import java.util.ArrayList;

/**
 * Created by VIRAL on 4/14/2015.
 */
public class ItemListArrayAdapter extends ArrayAdapter<Item> {

    private Context context;
    private ArrayList<Item> itemsList;

    private TextView itemName;
    private CheckBox itemCheckBox;

    public ItemListArrayAdapter(Context context, int resource, ArrayList<Item> itemsList) {
        super(context, resource, itemsList);
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.item_detail_checkbox_row, null, true);

        itemName = (TextView) convertView.findViewById(R.id.item_row_textView_taskDetail);
        itemCheckBox = (CheckBox) convertView.findViewById(R.id.item_row_checkBox_taskDetail);

        itemName.setText(itemsList.get(position).getName());

        if(itemsList.get(position).getStatus() == 0){
            itemCheckBox.setChecked(false);
        }else{
            itemCheckBox.setChecked(true);
        }

        return convertView;
    }
}
