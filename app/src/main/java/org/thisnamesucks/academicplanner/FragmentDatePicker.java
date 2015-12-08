package org.thisnamesucks.academicplanner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

// TODO: replace other Fragment*Date objects

/**
 * Created by student on 12/3/15.
 */
public class FragmentDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public interface FragmentDateCallback {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        void setDate(int year, int month, int day);
        Calendar getDate();
    }

    FragmentDateCallback callback;

    public void setCallback(FragmentDateCallback callback) {
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current date as the default date in the date picker
        /*final Calendar c = Calendar.getInstance();*/

        Calendar c = callback.getDate();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        //Do something with the date chosen by the user
        //month = month + 1;

        callback.setDate(year, month, day);

        /*TextView et = (TextView) getActivity().findViewById(R.id.due_date_et);
        String stringOfDate = month + "/" + day + "/" + year;*/
        //et.setText(callback);
    }
}
