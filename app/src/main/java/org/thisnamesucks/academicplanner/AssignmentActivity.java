package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

        final int classId = getIntent().getExtras().getInt("classid");
        int assignmentId = getIntent().getExtras().getInt("assignmentid");
        classModel = ClassDataManager.getClassById(classId);
        if (assignmentId == -1) {
            assignmentModel = new AssignmentModel();
            setTitle("New Assignment");
        }
        else {
            assignmentModel = AssignmentDataManager.getAssignmentById(assignmentId);
            //setTitle(assignmentModel.getName());
            setTitle(assignmentModel.getName());
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAssignment();
                Toast.makeText(AssignmentActivity.this, "Assignment Saved!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AssignmentActivity.this, ClassActivity.class);
                intent.putExtra("classid", classId);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveAssignment()
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
        //return (int)timestamp;
    }
}
