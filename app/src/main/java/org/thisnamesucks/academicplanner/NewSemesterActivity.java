package org.thisnamesucks.academicplanner;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class NewSemesterActivity extends AppCompatActivity {
    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_semester);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Create New Semester");

        Spinner spinner = (Spinner) findViewById(R.id.semester_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.
                createFromResource(this,
                R.array.semester_spinner_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final TextView start_btn = (TextView) findViewById(R.id.startdate_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentDatePicker newFragment = new FragmentDatePicker();
                newFragment.setCallback(new FragmentDatePicker.FragmentDateCallback() {
                    @Override
                    public void setDate(int year, int month, int day) {
                        startDate.set(year, month, day);
                        start_btn.setText(dateFormat.format(startDate.getTime()));
                    }
                    public Calendar getDate() {
                        return startDate;
                    }
                });
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });
        final TextView end_btn = (TextView) findViewById(R.id.enddate_btn);
        end_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentDatePicker newFragment = new FragmentDatePicker();
                newFragment.setCallback(new FragmentDatePicker.FragmentDateCallback() {
                    @Override
                    public void setDate(int year, int month, int day) {
                        endDate.set(year, month, day);
                        end_btn.setText(dateFormat.format(endDate.getTime()));
                    }
                    public Calendar getDate() {
                        return endDate;
                    }
                });
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        String[] schools = getResources().getStringArray(R.array.schools_array);
        getResources().openRawResource(R.raw.sorted_schools);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_school);
        ArrayAdapter<String> schoolsAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                Util.getStringArrayFromResource(this, R.raw.sorted_schools));
        textView.setAdapter(schoolsAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
