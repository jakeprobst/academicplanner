package org.thisnamesucks.academicplanner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
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

        SemesterModel semesterModel = new SemesterModel(getApplicationContext());
        setTitle(semesterModel.getName());
        classInfoAdapter = new ClassInformationAdapter(this, semesterModel.getClasses());

        ListView classSelection = (ListView) findViewById(R.id.class_list);

        classSelection.setAdapter(classInfoAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show add new class activity
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
