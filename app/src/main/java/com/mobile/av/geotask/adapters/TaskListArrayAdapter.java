package com.mobile.av.geotask.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.av.geotask.R;
import com.mobile.av.geotask.model.Task;

import java.util.List;

/**
 * Created by VIRAL on 4/12/2015.
 */
public class TaskListArrayAdapter extends ArrayAdapter<Task> {

    private Context context;
    private List<Task> taskList;

    public TaskListArrayAdapter(Context context, int resource, List<Task> taskList) {
        super(context, resource, taskList);
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.task_list_row, null, true);

        TextView taskTitle = (TextView) convertView.findViewById(R.id.taskTitle_textView_task_row_list);
        TextView expirationDate = (TextView) convertView.findViewById(R.id.expires_date_textView_task_row_list);
        TextView range = (TextView) convertView.findViewById(R.id.range_textView_task_row_list);
        TextView repeat = (TextView) convertView.findViewById(R.id.repeat_textView_task_row_list);
        ImageView discardTask = (ImageView) convertView.findViewById(R.id.discardTask_imageView_task_row_list);

        taskTitle.setText(taskList.get(position).getTitle());
        expirationDate.setText(taskList.get(position).getExpr_date());
        range.setText("Range " + String.valueOf(taskList.get(position).getRange()) + " mts");

        if (taskList.get(position).getRepeat() == 1) {
            repeat.setText("YES");
        } else {
            repeat.setText("NO");
        }

        discardTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Test " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
