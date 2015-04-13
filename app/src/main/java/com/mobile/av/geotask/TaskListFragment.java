package com.mobile.av.geotask;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.av.geotask.adapters.TaskListRowAdapter;
import com.mobile.av.geotask.db.TaskDataSource;
import com.mobile.av.geotask.model.Task;

import java.util.List;

/**
 * Created by Anand on 4/8/2015.
 */
public class TaskListFragment extends ListFragment {

    TaskDataSource dataSource;
    List<Task> tasks;

    public TaskListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource = new TaskDataSource(getActivity());
        dataSource.open();

        // set initial data TEST
//        dataSource.setAllData(InitialData.initTask());

        tasks = dataSource.getAllFromTask();

//        Custom Array Adapter
        TaskListRowAdapter adapter = new TaskListRowAdapter(getActivity().getApplicationContext(), tasks);
        setListAdapter(adapter);


        dataSource.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.task_list_fragment, container, false);
        return rootView;
    }
}
