package org.thisnamesucks.academicplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AssignmentActivity extends AppCompatActivity {
    ClassModel classModel;
    AssignmentModel assignmentModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeAssignmentSpinner();//Fill auto-complete options for Assignment Type

        final int classId = getIntent().getExtras().getInt("classid");
        final int assignmentId = getIntent().getExtras().getInt("assignmentid");
        classModel = ClassDataManager.getClassById(classId);

        if (assignmentId == -1) {
            assignmentModel = new AssignmentModel();
            assignmentModel.setId((int)System.currentTimeMillis()); // panic in 23 years
            setTitle("New Assignment");
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

    private void initializeAssignmentSpinner()
    {
        Spinner spinner = (Spinner) findViewById(R.id.assignment_type_entry);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.assignment_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
        text = (TextView) this.findViewById(R.id.assignment_due_entry);
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
        EditText date_entry = (EditText) findViewById(R.id.assignment_due_entry);
        EditText score_entry = (EditText) findViewById(R.id.assignment_score_entry);
        EditText total_entry = (EditText) findViewById(R.id.assignment_total_score_entry);

        Spinner type_entry = (Spinner) findViewById(R.id.assignment_type_entry);
        EditText description_entry = (EditText) findViewById(R.id.assignment_description_entry);
        EditText notes_entry = (EditText) findViewById(R.id.assignment_notes_entry);
        CheckBox isExtraCredit = (CheckBox) findViewById(R.id.assignment_extra_credit_entry);

        model.setName(name_entry.getText().toString());
        model.setType(AssignmentType.valueOf(type_entry.getSelectedItem().toString()));
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
