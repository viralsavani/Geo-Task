package com.mobile.av.geotask;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by VIRAL on 4/22/2015.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerListener datePickerCallback;


    public interface DatePickerListener{
        public void datePickerReturnData(String date);
    }

    public void setDatePickerListener(DatePickerListener datePickerCallback){
        this.datePickerCallback = datePickerCallback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle dateBundle = getArguments();

        int year = dateBundle.getInt("year");
        int month = dateBundle.getInt("month");
        int day = dateBundle.getInt("day");

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = String.valueOf(monthOfYear)+"-"+String.valueOf(dayOfMonth)+"-"+String.valueOf(year);
        datePickerCallback.datePickerReturnData(date);
    }
}
