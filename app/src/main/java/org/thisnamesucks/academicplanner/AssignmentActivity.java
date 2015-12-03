package org.thisnamesucks.academicplanner;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AssignmentActivity extends AppCompatActivity {
    ClassModel classModel;
    AssignmentModel assignmentModel;

    /*private EditText assignmentDate = (EditText) findViewById(R.id.assignment_due_entry);
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;*/


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        final int classId = getIntent().getExtras().getInt("classid");
        final int assignmentId = getIntent().getExtras().getInt("assignmentid");
        classModel = ClassDataManager.getClassById(classId);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //date-picker for assignment
        Button start_btn = (Button) findViewById(R.id.startdate_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment newFragment = new FragmentDueDate();
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
            assignmentModel.setName("Assignment " + String.valueOf(classModel.getAssignments().size())); //Set default name

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAssignment();
                Toast.makeText(AssignmentActivity.this, "Assignment Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveAssignment()
    {
        viewToModel(assignmentModel);
        AssignmentDataManager.writeAssignmentData(assignmentModel);

        if (!classModel.getAssignments().contains(assignmentModel.getId())) {
            classModel.getAssignments().add(assignmentModel.getId());
        }
        ClassDataManager.writeClassData(classModel);
    }

    //Initialize Date-Time field
    /*private void setDateTimeField() {
        assignmentDate.setInputType(InputType.TYPE_NULL);
        assignmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                assignmentDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }*/

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
        ArrayAdapter<AssignmentType> adapter = new ArrayAdapter<AssignmentType>(this, android.R.layout.simple_list_item_1, types);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinner.setAdapter(adapter);

        return true;
    }

    private void modelToView(AssignmentModel model)
    {
        String name = model.getName();
        String dueDate = model.getDue();
        String description = model.getDescription();
        String notes = model.getNotes();
        String type = model.getType().name();
        Integer score = model.getCurrentScore();
        Integer total = model.getTotalScore();
        boolean extraCredit = model.isExtraCredit();

        TextView text;
        text = (TextView) this.findViewById(R.id.assignment_name_entry);
        text.setText(name);
        text = (TextView) this.findViewById(R.id.due_date_et);
        text.setText(dueDate);
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
        EditText date_entry = (EditText) findViewById(R.id.due_date_et);
        EditText score_entry = (EditText) findViewById(R.id.assignment_score_entry);
        EditText total_entry = (EditText) findViewById(R.id.assignment_total_score_entry);

        Spinner type_entry = (Spinner) findViewById(R.id.assignment_type_entry);
        EditText description_entry = (EditText) findViewById(R.id.assignment_description_entry);
        EditText notes_entry = (EditText) findViewById(R.id.assignment_notes_entry);
        CheckBox isExtraCredit = (CheckBox) findViewById(R.id.assignment_extra_credit_entry);

        model.setName(name_entry.getText().toString());
        model.setDue(date_entry.getText().toString());
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
}
