package org.thisnamesucks.academicplanner;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.widget.TextView;
import android.widget.DatePicker;
import android.app.Dialog;
import java.util.Calendar;

/**
 * Created by toogymonster on 11/22/15.
 */
public class FragmentStartDate extends DialogFragment implements DatePickerDialog.OnDateSetListener
{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        month = month+1;
        TextView tv = (TextView) getActivity().findViewById(R.id.startdate_tv);
        String stringOfDate = month + "/" + day + "/" + year;
        tv.setText("Start Date: " + stringOfDate);
    }
}