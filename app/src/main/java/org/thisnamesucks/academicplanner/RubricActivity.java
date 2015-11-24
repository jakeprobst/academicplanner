package org.thisnamesucks.academicplanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class RubricActivity extends AppCompatActivity {
    //ClassModel classModel;
    RubricModel rubric;
    Gson gson;
    String originalRubric;

    //ArrayList<RubricItem> rubricitems = new ArrayList<>();
    //RubricItemAdapter rubricadapter = new RubricItemAdapter(this, rubricitems);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubric);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Modify Rubric");

        int classId = getIntent().getExtras().getInt("classid");
        ClassModel classModel = ClassDataManager.getClassById(classId);
        Log.d("classid", Integer.toString(classId));
        gson = new Gson();
        rubric = classModel.getRubric();
        originalRubric = gson.toJson(rubric);//Takes the current unchanged rubric JSON string

        //Rubric is instantiated when new class model is made to avoid null situations
        /*if (rubric == null) {
            Log.d("zzz", "hella null");
            rubric = new RubricModel();
            rubric.setRubricItems(new ArrayList<RubricItem>());
        }*/

        final RubricItemAdapter rubricadapter = new RubricItemAdapter(this, rubric.getRubricItems());

        ListView rubriclist = (ListView) findViewById(R.id.rubric_item_list);
        ImageButton additem = (ImageButton) findViewById(R.id.new_rubric_item);
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rubricadapter.add(new RubricItem());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RubricActivity.this, "Changes Saved!", Toast.LENGTH_SHORT).show();

                //Go back with updated rubric data
                goBack(gson.toJson(rubric));
            }
        });

        rubriclist.setAdapter(rubricadapter);
    }

    @Override
    public void onBackPressed() {

        //Show warning only if changes were made to rubric
        if(!gson.toJson(rubric).equals(originalRubric))
            showWarningDialog();
        else
            goBack(originalRubric);
    }

    public void showWarningDialog()
    {
        //Make warning message that changes won't be saved
        AlertDialog.Builder builder = new AlertDialog.Builder(RubricActivity.this);
        builder.setTitle("Warning");
        builder.setMessage("Your changes haven't been saved. Do you wish to save changes?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                //Go back with updated rubric data
                goBack(gson.toJson(rubric));
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                //Go back with old data
                goBack(originalRubric);
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public void goBack(String rubricJSON)
    {
        Intent intent = new Intent();
        intent.putExtra("rubric", rubricJSON);
        setResult(0, intent);
        super.onBackPressed();
    }
}
