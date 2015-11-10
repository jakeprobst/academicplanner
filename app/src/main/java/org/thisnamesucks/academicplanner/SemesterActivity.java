package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SemesterActivity extends AppCompatActivity {
    //SemesterInformation semesterInformation;
    //SemesterModel semesterModel;
    ClassInformationAdapter classInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SemesterModel semesterModel = new SemesterModel(getApplicationContext());
        SemesterDataManager.initialize(this);
        ClassDataManager.initialize(this);
        SemesterModel semesterModel = SemesterDataManager.getCurrentSemester();
        setTitle(semesterModel.getName());
        classInfoAdapter = new ClassInformationAdapter(this, ClassDataManager.getByIDs(semesterModel.getClasses()));

        ListView classSelection = (ListView) findViewById(R.id.class_list);
        classSelection.setAdapter(classInfoAdapter);
        classSelection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SemesterActivity.this, ClassActivity.class);
                intent.putExtra("classname", ((ClassModel) parent.getItemAtPosition(position)).getName());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show add new class activity
                Intent intent = new Intent(SemesterActivity.this, NewClassActivity.class);
                startActivity(intent);
            }
        });
    }

}
