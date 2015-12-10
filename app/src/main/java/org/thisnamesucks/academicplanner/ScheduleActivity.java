package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity
{
    ScheduleModel scheduleModel;
    ArrayList<ScheduleModel> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ///scheduleModel = ScheduleDataManager.getScheduleById(getIntent().getExtras().getInt("id"));
        ///scheduleList = ScheduleDataManager.getScheduleById(scheduleModel.getAssignments());
        ///assignmentInfoAdapter = new AssignmentInformationAdapter(this, assignmentList);

        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SemesterDataManager.initialize(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ScheduleActivity.this, NewSemesterActivity.class);
                intent.putExtra("name", -1);
                startActivity(intent);
            }
        });
    }
}
