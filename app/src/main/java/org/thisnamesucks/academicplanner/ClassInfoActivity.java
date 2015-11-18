package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClassInfoActivity extends AppCompatActivity {



    private ClassModel createNewClass() {

        long timestamp = System.currentTimeMillis();
        ClassModel classModel = new ClassModel((int) timestamp);

        updateClassModel(classModel);

        ClassDataManager.writeClassData(classModel);
        SemesterModel semester = SemesterDataManager.getCurrentSemester();

        semester.addClass(classModel);
        SemesterDataManager.writeClassData(semester);

        return classModel;
    }

    private void updateClassInfoView(ClassModel model)
    {
        String name = model.getName();
        String shortName = model.getShortName();

        TextView text;
        text = (TextView) this.findViewById(R.id.class_name_entry);
        text.setText(name);
        text = (TextView) this.findViewById(R.id.class_id_entry);
        text.setText(shortName);


        ClassDataManager.updateScores(model);
        setTitle(model.getName());
    }

    private void updateClassModel(ClassModel model)
    {
        EditText name_entry = (EditText) this.findViewById(R.id.class_name_entry);
        EditText id_name = (EditText) this.findViewById(R.id.class_id_entry);

        model.setName(name_entry.getText().toString());
        model.setShortName(id_name.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int classId = getIntent().getExtras().getInt("classid");

        if (classId == -1) {
            setTitle("Create New Class");
        }
        else {
            updateClassInfoView(ClassDataManager.getClassById(classId));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassInfoActivity.this, ClassActivity.class);

                ClassModel classModel;

                if(classId == -1) {
                    classModel = createNewClass();
                }
                else{
                    classModel = ClassDataManager.getClassById(classId);
                    updateClassModel(classModel);
                }

                intent.putExtra("classid", classModel.getId());

                Toast.makeText(ClassInfoActivity.this, "Class Saved!", Toast.LENGTH_SHORT).show();

                startActivity(intent);
                finish();
            }
        });
    }
}
