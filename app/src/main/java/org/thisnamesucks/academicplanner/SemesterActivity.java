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
    ClassInformationAdapter classInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Fall 2015"); // to be set based on selected semester

        classInfoAdapter = new ClassInformationAdapter(this, new ArrayList<ClassInformation>());
        classInfoAdapter.add(new ClassInformation("Software Development", "CS 370", 280, 300));
        classInfoAdapter.add(new ClassInformation("Data Structures", "CS 315", 350, 450));
        classInfoAdapter.add(new ClassInformation("Computer Architecture", "CS 351", 10, 110));

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
