package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class NewClassActivity extends AppCompatActivity {

    private int createNewClass() {
        long timestamp = System.currentTimeMillis() / 1000L;

        EditText name_entry = (EditText) findViewById(R.id.new_class_name_entry);
        EditText id_entry = (EditText) findViewById(R.id.new_class_id_entry);

        ClassModel model = new ClassModel();
        model.setId((int)timestamp); // panic in 23 years
        model.setName(name_entry.getText().toString());
        model.setShortName(id_entry.getText().toString());
        ClassDataManager.writeClassData(model);
        SemesterModel semester = SemesterDataManager.getCurrentSemester();
        semester.getClasses().add((int)timestamp);
        SemesterDataManager.writeData(semester);
        return (int)timestamp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Create New Class");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewClassActivity.this, ClassActivity.class);
                intent.putExtra("classid", createNewClass());
                startActivity(intent);
                finish();
            }
        });
    }
}
