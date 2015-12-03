package org.thisnamesucks.academicplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

public class SemesterActivity extends NavigationActivity {
    //SemesterInformation semesterInformation;
    SemesterModel semesterModel;
    ArrayList<ClassModel> classList;
    ClassInformationAdapter classInfoAdapter;

    @Override
    protected void onResume() {
        super.onResume();

        semesterModel = SemesterDataManager.getCurrentSemester();
        setTitle(semesterModel.getName());
        classList = ClassDataManager.getClassesByIDs(semesterModel.getClasses());
        classInfoAdapter = new ClassInformationAdapter(this, classList);

        ListView classSelection = (ListView) findViewById(R.id.class_list);

        registerForContextMenu(findViewById(R.id.class_list));//Implement context menu

        classSelection.setAdapter(classInfoAdapter);
        classSelection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SemesterActivity.this, ClassActivity.class);
                intent.putExtra("classid", ((ClassModel) parent.getItemAtPosition(position)).getId());
                startActivity(intent);
            }
        });

        //Create empty assignment list notice
        if(classList.isEmpty())
        {
            TextView view = (TextView) this.findViewById(R.id.empty_class_notice);
            view.setVisibility(View.VISIBLE);
        }
        else
        {
            TextView view = (TextView) this.findViewById(R.id.empty_class_notice);
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SemesterActivity.this, ClassInfoActivity.class);
                intent.putExtra("classid", -1);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_semester, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        ClassModel model = classInfoAdapter.getItem(info.position);

        switch (item.getItemId()) {
            case R.id.edit:
                edit(model);
                return true;
            case R.id.delete:
                createAndShowWarningDialog(model);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void edit(ClassModel item) {
        Intent intent = new Intent(SemesterActivity.this, ClassInfoActivity.class);
        intent.putExtra("classid", item.getId());
        startActivity(intent);

        //Toast.makeText(SemesterActivity.this, "Class Opened!", Toast.LENGTH_SHORT).show();
    }



    public void delete(ClassModel item) {
        semesterModel.getClasses().remove((Object)item.getId());
        ClassDataManager.deleteClass(item);
        SemesterDataManager.writeSemesterData(semesterModel);
        classList.remove(item);
        classInfoAdapter.notifyDataSetChanged();

        //Toast.makeText(SemesterActivity.this, "Class Deleted!", Toast.LENGTH_SHORT).show();
    }

    private void createAndShowWarningDialog(final ClassModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SemesterActivity.this);
        builder.setTitle("Warning");
        builder.setMessage("Are you sure you want to delete this class?");

        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                delete(model);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
