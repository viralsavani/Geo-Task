package com.mobile.av.geotask.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.mobile.av.geotask.R;
import com.mobile.av.geotask.model.Item;

import java.util.ArrayList;

/**
 * Created by Anand on 4/17/2015.
 */
public class NewItemListArrayAdapter extends ArrayAdapter<Item> {

    private ArrayList<Item> itemList;
    private MyTextWatcher watcher;

    public NewItemListArrayAdapter(Context context, int resource, ArrayList<Item> itemList) {
        super(context, resource, itemList);
        this.itemList = itemList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.item_add_list_row, null, true);

        EditText editText = (EditText) convertView.findViewById(R.id.title_editText_addItem);
        editText.setText(itemList.get(position).getName());
        editText.removeTextChangedListener(watcher);
        watcher = new MyTextWatcher(position);
        editText.addTextChangedListener(watcher);

        return convertView;
    }

    private class MyTextWatcher implements TextWatcher {
        private int position;

        private MyTextWatcher(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            itemList.get(position).setName(s.toString());
        }
    }

    public ArrayList<Item> getItemNames() {
        return itemList;
    }
}