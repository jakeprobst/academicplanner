package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ClassInfoActivity extends AppCompatActivity {
    ClassModel classModel;
    private ClassModel createNewClass() {
        ClassModel classModel = new ClassModel();
        classModel.setId((int)System.currentTimeMillis());

        ClassDataManager.writeClassData(classModel);
        SemesterModel semester = SemesterDataManager.getCurrentSemester();

        semester.getClasses().add(classModel.getId());
        SemesterDataManager.writeSemesterData(semester);

        return classModel;
    }

    private void updateClassInfoView(ClassModel model) {
        String name = model.getName();
        String shortName = model.getShortName();

        TextView text;
        text = (TextView) this.findViewById(R.id.class_name_entry);
        text.setText(name);
        text = (TextView) this.findViewById(R.id.class_id_entry);
        text.setText(shortName);

        //ClassDataManager.updateScores(model);
        setTitle(model.getName());
    }

    private void updateClassModel(ClassModel model) {
        EditText name_entry = (EditText) this.findViewById(R.id.class_name_entry);
        EditText id_name = (EditText) this.findViewById(R.id.class_id_entry);

        model.setName(name_entry.getText().toString());
        model.setShortName(id_name.getText().toString());
        ClassDataManager.writeClassData(model);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            Gson gson = new Gson();
            classModel.setRubric(gson.fromJson(data.getExtras().getString("rubric"), RubricModel.class));
            ClassDataManager.writeClassData(classModel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int classId = getIntent().getExtras().getInt("classid");

        //final ClassModel classModel;
        if (classId == -1) {
            setTitle("Create New Class");
            classModel = createNewClass();
        }
        else {
            classModel = ClassDataManager.getClassById(classId);
            updateClassInfoView(classModel);
        }

        Button rubric = (Button) findViewById(R.id.class_modify_rubric);
        rubric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassInfoActivity.this, RubricActivity.class);
                Log.d("sending classid:", Integer.toString(classModel.getId()));
                intent.putExtra("classid", classModel.getId());
                startActivityForResult(intent, 0);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassInfoActivity.this, ClassActivity.class);

                updateClassModel(classModel);

                intent.putExtra("classid", classModel.getId());

                Toast.makeText(ClassInfoActivity.this, "Class Saved!", Toast.LENGTH_SHORT).show();

                startActivity(intent);
                finish();
            }
        });
    }
}
