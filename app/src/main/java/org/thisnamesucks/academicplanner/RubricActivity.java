package org.thisnamesucks.academicplanner;

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

import com.google.gson.Gson;

import java.util.ArrayList;

public class RubricActivity extends AppCompatActivity {
    //ClassModel classModel;
    RubricModel rubric;
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
        rubric = classModel.getRubric();
        if (rubric == null) {
            Log.d("zzz", "hella null");
            rubric = new RubricModel();
            rubric.rubricItems = new ArrayList<>();
        }

        final RubricItemAdapter rubricadapter = new RubricItemAdapter(this, rubric.rubricItems);

        ListView rubriclist = (ListView) findViewById(R.id.rubric_item_list);
        ImageButton additem = (ImageButton) findViewById(R.id.new_rubric_item);
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rubricadapter.add(new RubricItem());
            }
        });


        rubriclist.setAdapter(rubricadapter);
    }

    @Override
    public void onBackPressed() {


        //perhaps this stuff should be under a save button?
        Gson gson = new Gson();
        String json = gson.toJson(rubric);
        Intent intent = new Intent();
        intent.putExtra("rubric", json);
        setResult(0, intent);

        super.onBackPressed();
    }


}
