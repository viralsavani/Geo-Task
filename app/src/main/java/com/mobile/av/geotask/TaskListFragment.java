package com.mobile.av.geotask;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobile.av.geotask.adapters.TaskListArrayAdapter;
import com.mobile.av.geotask.db.TaskDataSource;
import com.mobile.av.geotask.model.Task;

import java.util.List;

/**
 * Created by Anand on 4/8/2015.
 */
public class TaskListFragment extends ListFragment {

    TaskDataSource dataSource;
    List<Task> tasks;
    OnListItemSelectedListener listItemSelectorCallback;
    TaskListArrayAdapter taskListArrayAdapter;

    public TaskListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource = new TaskDataSource(getActivity());
        dataSource.open();

        // set initial data TEST
       //dataSource.setAllData(InitialData.initTask());

        tasks = dataSource.getAllFromTask();

        //Custom Array Adapter
        taskListArrayAdapter = new TaskListArrayAdapter(getActivity(),
                R.layout.task_list_fragment,
                tasks);
        setListAdapter(taskListArrayAdapter);

        dataSource.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.task_list_fragment, container, false);
    }

    // Interface to communicate with MainActivity for any items selected from list
    public interface OnListItemSelectedListener{
        public void onItemClicked(int position, Task task);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listItemSelectorCallback = (OnListItemSelectedListener) activity;
        }catch (ClassCastException e){
            throw  new ClassCastException(activity.toString()+ " must implement OnListItemSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        listItemSelectorCallback.onItemClicked(position, tasks.get(position));
    }
}
