package org.thisnamesucks.academicplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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

    private void modelToView(AssignmentModel model)
    {
        String name = model.getName();
        String dueDate = model.getDue();
        Integer score = model.getCurrentScore();
        Integer total = model.getTotalScore();

        TextView text;
        text = (TextView) this.findViewById(R.id.assignment_name_entry);
        text.setText(name);
        text = (TextView) this.findViewById(R.id.assignment_due_entry);
        text.setText(dueDate);
        text = (TextView) this.findViewById(R.id.assignment_score_entry);
        text.setText(score.toString());

        if(total != 0)
        {
            text = (TextView) this.findViewById(R.id.assignment_total_score_entry);
            text.setText(total.toString());
        }

        setTitle(model.getName());
    }

    private void viewToModel(AssignmentModel model)
    {
        EditText name_entry = (EditText) this.findViewById(R.id.assignment_name_entry);
        EditText date_entry = (EditText) findViewById(R.id.assignment_due_entry);
        EditText score_entry = (EditText) findViewById(R.id.assignment_score_entry);
        EditText total_entry = (EditText) findViewById(R.id.assignment_total_score_entry);

        model.setName(name_entry.getText().toString());
        model.setDue(date_entry.getText().toString());

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
