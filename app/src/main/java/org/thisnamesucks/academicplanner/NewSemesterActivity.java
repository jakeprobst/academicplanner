package org.thisnamesucks.academicplanner;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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


    }

    private void saveSemester() {
        Settings.SettingsModel settings = Settings.getInstance();
        SemesterModel semesterModel = new SemesterModel();
        semesterModel.setId((int) System.currentTimeMillis());

        Spinner spinner = (Spinner) findViewById(R.id.semester_spinner);
        TextView start_btn = (TextView) findViewById(R.id.startdate_btn);
        // TODO: this is way too specific?
        String year = start_btn.getText().toString().substring(0, 4);
        String name = spinner.getSelectedItem().toString() + ' ' + year;
        semesterModel.setName(name);

        SemesterDataManager.writeSemesterData(semesterModel);
        settings.getSemesters().add(semesterModel.getId());
        Settings.save();

        Intent intent = new Intent(NewSemesterActivity.this, SemesterActivity.class);
        intent.putExtra("semesterid", semesterModel.getId());
        startActivity(intent);
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.assignment_save:
                saveSemester();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assignment_toolbar, menu);
        return true;
    }
}
