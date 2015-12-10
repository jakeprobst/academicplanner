package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

public class AssignmentActivity extends AppCompatActivity {
    ClassModel classModel;
    AssignmentModel assignmentModel;
    int classId;
    int semesterId;
    Calendar dueDate = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        classId = getIntent().getExtras().getInt("classid");
        final int assignmentId = getIntent().getExtras().getInt("assignmentid");
        semesterId = getIntent().getExtras().getInt("semesterid");
        classModel = ClassDataManager.getClassById(classId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView start_btn = (TextView) findViewById(R.id.assignment_due_entry);
        start_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentDatePicker newFragment = new FragmentDatePicker();
                newFragment.setCallback(new FragmentDatePicker.FragmentDateCallback() {
                    @Override
                    public void setDate(int year, int month, int day) {
                        dueDate.set(year, month, day);
                        start_btn.setText(dateFormat.format(dueDate.getTime()));
                    }
                    public Calendar getDate() {
                        return dueDate;
                    }
                });
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        //Display assignment type spinner if there are types listed in the rubric
        if(!initializeAssignmentSpinner(classModel))
        {
            View view = findViewById(R.id.assignment_type_entry);
            view.setVisibility(View.INVISIBLE);
        }

        if (assignmentId == -1) {
            assignmentModel = new AssignmentModel();
            //assignmentModel.setName("Assignment " + String.valueOf(classModel.getAssignments().size())); //Set default name

            if(!classModel.getRubric().getRubricItems().isEmpty()) //Set default type if rubric available
                assignmentModel.setType(classModel.getRubric().getRubricItems().get(0).getType());

            assignmentModel.setId((int)System.currentTimeMillis()); //Set ID panic in 23 years

            //Update assignment view display
            setTitle(assignmentModel.getName());
            TextView text = (TextView) this.findViewById(R.id.assignment_name_entry);
            text.setText(assignmentModel.getName());
        }
        else {
            assignmentModel = AssignmentDataManager.getAssignmentById(assignmentId);
            modelToView(assignmentModel);
        }

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAssignment();
                Toast.makeText(AssignmentActivity.this, "Assignment Saved!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AssignmentActivity.this, ClassActivity.class);
                intent.putExtra("classid", classId);
                intent.putExtra("semesterid", semesterId);
                startActivity(intent);
                //finish();
            }
        });*/
    }

    private void saveAssignment()
    {
        viewToModel(assignmentModel);
        AssignmentDataManager.writeAssignmentData(assignmentModel);

        if (!classModel.getAssignments().contains(assignmentModel.getId())) {
            classModel.getAssignments().add(assignmentModel.getId());
        }
        ClassDataManager.writeClassData(classModel);

        AssignmentsDueWidget.notifyDataChanged(this);
    }

    //Initializes a spinner to display current assignment types from the grading rubric. Returns false if the rubric was empty and no spinner was made.
    private boolean initializeAssignmentSpinner(ClassModel classModel)
    {
        if(classModel.getRubric().getRubricItems().isEmpty())
            return false;

        ArrayList<AssignmentType> assignments = new ArrayList<>();
        for(RubricItem r : classModel.getRubric().getRubricItems())
            assignments.add(r.getType());

        AssignmentType[] types = new AssignmentType[assignments.size()];
        types = assignments.toArray(types);

        Spinner spinner = (Spinner) findViewById(R.id.assignment_type_entry);
        ArrayAdapter<AssignmentType> adapter = new ArrayAdapter<AssignmentType>(this, R.layout.custom_spinner, types);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);

        return true;
    }

    private void modelToView(AssignmentModel model)
    {
        String name = model.getName();

        ArrayList<Integer> dueTime = model.getDue();
        dueDate.set(dueTime.get(0), dueTime.get(1), dueTime.get(2));
        String dueString = dateFormat.format(dueDate.getTime());

        String description = model.getDescription();
        String notes = model.getNotes();
        String type = model.getType().name();
        Integer score = model.getCurrentScore();
        Integer total = model.getTotalScore();
        boolean extraCredit = model.isExtraCredit();

        TextView text;
        text = (TextView) this.findViewById(R.id.assignment_name_entry);
        text.setText(name);
        text = (TextView) this.findViewById(R.id.assignment_due_entry);
        text.setText(dueString);
        text = (TextView) this.findViewById(R.id.assignment_description_entry);
        text.setText(description);
        text = (TextView) this.findViewById(R.id.assignment_notes_entry);
        text.setText(notes);
        text = (TextView) this.findViewById(R.id.assignment_score_entry);
        text.setText(score.toString());

        Spinner dropdown = (Spinner) this.findViewById(R.id.assignment_type_entry);

        dropdown.setSelection(getIndex(dropdown,type),true);
        CheckBox isExtraCredit = (CheckBox) this.findViewById(R.id.assignment_extra_credit_entry);
        isExtraCredit.setChecked(extraCredit);

        if(total != 0)
        {
            text = (TextView) this.findViewById(R.id.assignment_total_score_entry);
            text.setText(total.toString());
        }

        setTitle(model.getName());
    }

    //private method to get index of specific dropdown value
    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    private void viewToModel(AssignmentModel model)
    {
        EditText name_entry = (EditText) this.findViewById(R.id.assignment_name_entry);
        TextView date_entry = (TextView) findViewById(R.id.assignment_due_entry);
        EditText score_entry = (EditText) findViewById(R.id.assignment_score_entry);
        EditText total_entry = (EditText) findViewById(R.id.assignment_total_score_entry);

        Spinner type_entry = (Spinner) findViewById(R.id.assignment_type_entry);
        EditText description_entry = (EditText) findViewById(R.id.assignment_description_entry);
        EditText notes_entry = (EditText) findViewById(R.id.assignment_notes_entry);
        CheckBox isExtraCredit = (CheckBox) findViewById(R.id.assignment_extra_credit_entry);

        model.setName(name_entry.getText().toString());
        model.setDue(new ArrayList<Integer>(Arrays.asList(dueDate.get(Calendar.YEAR),
                                                          dueDate.get(Calendar.MONTH),
                                                          dueDate.get(Calendar.DAY_OF_MONTH))));

        model.setDescription(description_entry.getText().toString());
        model.setNotes(notes_entry.getText().toString());
        model.setExtraCredit(isExtraCredit.isChecked());

        String score = score_entry.getText().toString();
        String total = total_entry.getText().toString();

        if(score.isEmpty())
        {
            model.setCurrentScore(0);
        }
        else
        {
            model.setCurrentScore(Integer.parseInt(score));
        }

        if(total.isEmpty())
        {
            model.setTotalScore(0);
        }
        else
        {
            model.setTotalScore(Integer.parseInt(total));
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ClassActivity.class);
        intent.putExtra("semesterid", semesterId);
        intent.putExtra("classid", classModel.getId());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.assignment_save:
                saveAssignment();
                Toast.makeText(AssignmentActivity.this, "Assignment Saved!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AssignmentActivity.this, ClassActivity.class);
                intent.putExtra("classid", classId);
                intent.putExtra("semesterid", semesterId);
                startActivity(intent);
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

