package com.mobile.av.geotask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

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


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.remove_task_dialog_fragment, null);
        builder.setView(dialogView);

        TextView taskNameTextView = (TextView) dialogView.findViewById(R.id.dialog_fragment_taskName_textView);
        taskNameTextView.setText(bundle.getString(TaskDBOpenHelper.TASK_TITLE));

        Button okButton = (Button) dialogView.findViewById(R.id.dialog_fragment_button_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.returnData(bundle.getInt(TaskListArrayAdapter.POSITION));
                getDialog().dismiss();
            }
        });

        Button cancelButton = (Button) dialogView.findViewById(R.id.dialog_fragment_button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return builder.create();
    }
}
