package org.thisnamesucks.academicplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class ClassInfoActivity extends AppCompatActivity {
    ClassModel classModel;
    boolean newClassModel = false;

    private void updateClassInfoView(ClassModel model) {
        String name = model.getName();
        String shortName = model.getShortName();
        String instructor = model.getInstructor();
        String location = model.getLocation();
        String notes = model.getNotes();
        String units = Double.toString(model.getUnits());
        String meetTimes = model.getMeetTimes();

        TextView text;
        text = (TextView) this.findViewById(R.id.class_name_entry);
        text.setText(name);
        text = (TextView) this.findViewById(R.id.class_id_entry);
        text.setText(shortName);
        text = (TextView) this.findViewById(R.id.class_instructor_entry);
        text.setText(instructor);
        text = (TextView) this.findViewById(R.id.class_location_entry);
        text.setText(location);
        text = (TextView) this.findViewById(R.id.class_notes_entry);
        text.setText(notes);
        text = (TextView) this.findViewById(R.id.class_units_entry);
        text.setText(units);
        text = (TextView) this.findViewById(R.id.class_meeting_times_entry);
        text.setText(meetTimes);

        RadioButton isLetterGrade = (RadioButton) findViewById(R.id.class_letter_grade_entry);
        RadioButton isPassNoPass = (RadioButton) findViewById(R.id.class_pass_fail_entry);
        isLetterGrade.setChecked(model.isLetterGrade());
        isPassNoPass.setChecked(!model.isLetterGrade());

        //ClassDataManager.updateScores(model);
        setTitle(model.getName());
    }

    private void updateClassModel(ClassModel model) {
        EditText name = (EditText) this.findViewById(R.id.class_name_entry);
        EditText id_entry = (EditText) this.findViewById(R.id.class_id_entry);
        EditText instructor = (EditText) this.findViewById(R.id.class_instructor_entry);
        EditText location = (EditText) this.findViewById(R.id.class_location_entry);
        EditText notes = (EditText) this.findViewById(R.id.class_notes_entry);
        EditText units = (EditText) this.findViewById(R.id.class_units_entry);
        EditText meetTimes = (EditText) this.findViewById(R.id.class_meeting_times_entry);
        RadioButton isLetterGrade = (RadioButton) findViewById(R.id.class_letter_grade_entry);

        model.setName(name.getText().toString());
        model.setShortName(id_entry.getText().toString());
        model.setInstructor(instructor.getText().toString());
        model.setLocation(location.getText().toString());
        model.setNotes(notes.getText().toString());
        model.setMeetTimes(meetTimes.getText().toString());
        model.setLetterGrade(isLetterGrade.isChecked());

        String unitText = units.getText().toString();

        if(unitText.isEmpty())
        {
            model.setUnits(0);
        }
        else
        {
            model.setUnits(Double.parseDouble(unitText));
        }

        ClassDataManager.writeClassData(model);
    }

    public void saveNewClass()
    {
        ClassDataManager.writeClassData(classModel);
        SemesterModel semester = SemesterDataManager.getCurrentSemester();

        semester.getClasses().add(classModel.getId());
        SemesterDataManager.writeSemesterData(semester);
    }

    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            Gson gson = new Gson();
            classModel.setRubric(gson.fromJson(data.getExtras().getString("rubric"), RubricModel.class));
            ClassDataManager.writeClassData(classModel);
        }
    }

    @Override
    public void onBackPressed()
    {
        showWarningDialog();
    }

    public void showWarningDialog()
    {
        //Make warning message that changes won't be saved
        AlertDialog.Builder builder = new AlertDialog.Builder(ClassInfoActivity.this);
        builder.setTitle("Warning");
        builder.setMessage("Your changes haven't been saved. Do you wish to save changes?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                if(newClassModel)
                {
                    saveNewClass();
                }

                updateClassModel(classModel);
                ClassInfoActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

                ClassInfoActivity.super.onBackPressed();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int classId = getIntent().getExtras().getInt("classid");

        if (classId == -1) {
            setTitle("Create New Class");

            classModel = new ClassModel();
            classModel.setId((int)System.currentTimeMillis());

            newClassModel = true;
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

                Intent intent;

                updateClassModel(classModel);

                if(newClassModel)//Go to class view if a new class was created
                {
                    intent = new Intent(ClassInfoActivity.this, ClassActivity.class);
                    intent.putExtra("classid", classModel.getId());
                    saveNewClass();
                }
                else
                {
                    intent = new Intent(ClassInfoActivity.this, SemesterActivity.class);
                }

                Toast.makeText(ClassInfoActivity.this, "Class Saved!", Toast.LENGTH_SHORT).show();

                startActivity(intent);
                finish();
            }
        });
    }
}
