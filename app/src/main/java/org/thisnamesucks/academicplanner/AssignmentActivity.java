package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AssignmentActivity extends AppCompatActivity {
    ClassModel classModel;
    AssignmentModel assignmentModel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int classId = getIntent().getExtras().getInt("classid");
        final int assignmentId = getIntent().getExtras().getInt("assignmentid");
        classModel = ClassDataManager.getClassById(classId);

        if (assignmentId == -1) {
            assignmentModel = new AssignmentModel();
            setTitle("New Assignment");
        }
        else {

            assignmentModel = AssignmentDataManager.getAssignmentById(assignmentId);

            TextView text;
            text = (TextView) this.findViewById(R.id.assignment_name_entry);
            text.setText(assignmentModel.getName());
            text = (TextView) this.findViewById(R.id.assignment_due);
            text.setText(assignmentModel.getDue());

            setTitle(assignmentModel.getName());
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAssignment(assignmentId);
                Toast.makeText(AssignmentActivity.this, "Assignment Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void saveAssignment(int id)
    {
        if(id == -1) //If new assignment, create new assignment object and set values
        {
            long timestamp = System.currentTimeMillis();

            EditText name_entry = (EditText) findViewById(R.id.assignment_name_entry);
            EditText date_entry = (EditText) findViewById(R.id.assignment_due);

            AssignmentModel model = new AssignmentModel();
            model.setId((int) timestamp); // panic in 23 years
            model.setName(name_entry.getText().toString());
            model.setDue(date_entry.getText().toString());
            AssignmentDataManager.writeData(model);
            classModel.getAssignments().add((int) timestamp);
            ClassDataManager.writeClassData(classModel);
        }
        else //Otherwise change current values
        {
            EditText name_entry = (EditText) findViewById(R.id.assignment_name_entry);
            EditText date_entry = (EditText) findViewById(R.id.assignment_due);

            AssignmentModel model = AssignmentDataManager.getAssignmentById(id);
            model.setName(name_entry.getText().toString());
            model.setDue(date_entry.getText().toString());
            AssignmentDataManager.writeData(model);
            ClassDataManager.writeClassData(classModel);
        }
    }
}
