package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {
    ClassModel classmodel;
    ArrayList<AssignmentModel> assignmentList;
    AssignmentInformationAdapter assignmentInfoAdapter;

    @Override
    protected void onResume() {
        super.onResume();

        classmodel = ClassDataManager.getClassById(getIntent().getExtras().getInt("classid"));
        assignmentList = AssignmentDataManager.getAssignmentsByIds(classmodel.getAssignments());
        assignmentInfoAdapter = new AssignmentInformationAdapter(this, assignmentList);

        //Create empty assignment list notice
        if(assignmentList.isEmpty())
        {
            TextView view = (TextView) this.findViewById(R.id.empty_notice);
            view.setVisibility(view.VISIBLE);
        }

        ListView assignmentSelection = (ListView) findViewById(R.id.class_list);
        assignmentSelection.setAdapter(assignmentInfoAdapter);
        assignmentSelection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClassActivity.this, AssignmentActivity.class);
                intent.putExtra("assignmentid", ((AssignmentModel) parent.getItemAtPosition(position)).getId());
                intent.putExtra("classid", classmodel.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        classmodel = ClassDataManager.getClassById(getIntent().getExtras().getInt("classid"));
        setTitle(classmodel.getName());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassActivity.this, AssignmentActivity.class);
                intent.putExtra("classid", classmodel.getId());
                intent.putExtra("assignmentid", -1);
                startActivity(intent);
            }
        });
    }
}
