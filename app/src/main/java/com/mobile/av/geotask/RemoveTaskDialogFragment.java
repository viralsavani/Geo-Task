package com.mobile.av.geotask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mobile.av.geotask.adapters.TaskListArrayAdapter;
import com.mobile.av.geotask.db.TaskDBOpenHelper;

/**
 * Created by Anand on 4/13/2015.
 */
public class RemoveTaskDialogFragment extends DialogFragment {

    private Listener mListener;

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public interface Listener {
        void returnData(int result);
    }

    public RemoveTaskDialogFragment(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Bundle bundle = getArguments();

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Remove Task")
                .setMessage("Remove task " + bundle.getString(TaskDBOpenHelper.TASK_TITLE))
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.returnData(bundle.getInt(TaskListArrayAdapter.POSITION));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().dismiss();
                    }
                });

        return builder.create();
    }
}
