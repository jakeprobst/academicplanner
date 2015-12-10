package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SemesterDataManager.initialize(this);
        ClassDataManager.initialize(this);
        AssignmentDataManager.initialize(this);

        // TODO: actually handle not having a current semester with a redirect to create one
        if (SemesterDataManager.getCurrentSemester() == null) {

        }

        Intent intent = new Intent(this, NewSemesterActivity.class);
        intent.putExtra("semesterid", SemesterDataManager.getCurrentSemester().getId());
        startActivity(intent);
    }
}
