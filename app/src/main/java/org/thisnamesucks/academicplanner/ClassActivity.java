package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {
    ClassModel classModel;
    ArrayList<AssignmentModel> assignmentList;
    AssignmentInformationAdapter assignmentInfoAdapter;

    @Override
    protected void onResume() {
        super.onResume();

        classModel = ClassDataManager.getClassById(getIntent().getExtras().getInt("classid"));
        ClassDataManager.updateScores(classModel);

        assignmentList = AssignmentDataManager.getAssignmentsByIds(classModel.getAssignments());
        assignmentInfoAdapter = new AssignmentInformationAdapter(this, assignmentList);

        //Create empty assignment list notice
        if(assignmentList.isEmpty())
        {
            TextView view = (TextView) this.findViewById(R.id.empty_assignment_notice);
            view.setVisibility(view.VISIBLE);
        }
        else
        {
            TextView view = (TextView) this.findViewById(R.id.empty_assignment_notice);
            view.setVisibility(view.INVISIBLE);
        }

        ListView assignmentSelection = (ListView) findViewById(R.id.assignment_list);
        assignmentSelection.setAdapter(assignmentInfoAdapter);

        assignmentSelection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ClassActivity.this, AssignmentActivity.class);
                intent.putExtra("assignmentid", ((AssignmentModel) parent.getItemAtPosition(position)).getId());
                intent.putExtra("classid", classModel.getId());
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

        classModel = ClassDataManager.getClassById(getIntent().getExtras().getInt("classid"));
        setTitle(classModel.getName());

        registerForContextMenu(findViewById(R.id.assignment_list));//Implement context menu

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassActivity.this, AssignmentActivity.class);
                intent.putExtra("classid", classModel.getId());
                intent.putExtra("assignmentid", -1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_class, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        AssignmentModel model = assignmentInfoAdapter.getItem(info.position);

        switch (item.getItemId()) {
            case R.id.duplicate:
                duplicate(model);
                return true;
            case R.id.delete:
                delete(model);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void duplicate(AssignmentModel item) {

        classModel.duplicateAssignment(item, item.getName() + " (copy)");

        //Reset the activity
        finish();
        startActivity(getIntent());

        Toast.makeText(ClassActivity.this, "Assignment Duplicated! " + item.getName(), Toast.LENGTH_SHORT).show();
    }

    public void delete(AssignmentModel item) {

        classModel.removeAssignment(item);

        //Reset the activity
        finish();
        startActivity(getIntent());

        Toast.makeText(ClassActivity.this, item.getName() + " Assignment Deleted!", Toast.LENGTH_SHORT).show();
    }
}
