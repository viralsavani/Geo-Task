package com.mobile.av.geotask.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobile.av.geotask.R;
import com.mobile.av.geotask.model.Task;

import java.util.List;

/**
 * Created by VIRAL on 4/12/2015.
 */
public class TaskListRowAdapter extends BaseAdapter {

    private Context context;
    private List<Task> taskList;
    private TextView taskTitle;
    private TextView expirationDate;
    private TextView range;
    private TextView repeat;

    public TaskListRowAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }


    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Task getItem(int position) {
        if(position >= taskList.size() || position < 0){
            throw new ArrayIndexOutOfBoundsException("<---INDEX FOUND "+position+"--->");
        }
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.task_list_row, null, true);

        taskTitle = (TextView) rowView.findViewById(R.id.taskTitle_textView_task_row_list);
        expirationDate = (TextView) rowView.findViewById(R.id.expires_date_textView_task_row_list);
        range = (TextView) rowView.findViewById(R.id.range_textView_task_row_list);
        repeat = (TextView) rowView.findViewById(R.id.repeat_textView_task_row_list);

        taskTitle.setText(taskList.get(position).getTitle());
        expirationDate.setText(taskList.get(position).getExpr_date());
        range.setText(String.valueOf(taskList.get(position).getRange())+" mts");

        if(taskList.get(position).getRepeat() == 1){
            repeat.setText("YES");
        }else{
            repeat.setText("NO");
        }

        return rowView;
    }
}
