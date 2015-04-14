package com.mobile.av.geotask.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.av.geotask.R;
import com.mobile.av.geotask.RemoveTaskDialogFragment;
import com.mobile.av.geotask.db.TaskDBOpenHelper;
import com.mobile.av.geotask.db.TaskDataSource;
import com.mobile.av.geotask.model.Task;

import java.util.List;

/**
 * Created by VIRAL on 4/12/2015.
 */
public class TaskListArrayAdapter extends ArrayAdapter<Task> implements RemoveTaskDialogFragment.Listener {

    public static final String REMOVE_DIALOG = "removeDialog";
    public static final String POSITION = "position";

    private Context context;
    private List<Task> taskList;
    private RemoveTaskDialogFragment removeDialog;
    private Bundle bundle;

    private TaskDataSource taskDataSource;

    public TaskListArrayAdapter(Context context, int resource, List<Task> taskList) {
        super(context, resource, taskList);
        this.context = context;
        this.taskList = taskList;
        taskDataSource = new TaskDataSource(context);
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
                showRemoveDialog(position);
            }
        });

        return convertView;
    }

    /*
    Method to handle user input onClick
     */
    public void showRemoveDialog(int position){
        removeDialog = new RemoveTaskDialogFragment();

        bundle = new Bundle();
        bundle.putString(TaskDBOpenHelper.TASK_TITLE, taskList.get(position).getTitle());
        bundle.putInt(POSITION, position);

        removeDialog.setArguments(bundle);
        removeDialog.setListener(this);
        removeDialog.show(((Activity) context).getFragmentManager(), REMOVE_DIALOG);
    }

    /*
    implemented listener from RemoveTaskDialogFragment
     */
    @Override
    public void returnData(int position) {
        // list index can't be negative
        if (position != -1) {
            listItemDelete(taskList.get(position).getTask_id());
            taskList.remove(position);
            notifyDataSetChanged();
        }
    }

    public void listItemDelete(int taskId){
        taskDataSource.open();
        taskDataSource.deleteTask(taskId);
        taskDataSource.close();
    }
}
