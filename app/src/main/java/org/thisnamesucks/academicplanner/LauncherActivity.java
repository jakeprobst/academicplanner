package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Settings.SettingsModel settings = Settings.getInstance(this);
        SemesterDataManager.initialize(this);
        ClassDataManager.initialize(this);
        AssignmentDataManager.initialize(this);

        // TODO: actually handle not having a current semester with a redirect to create one
        Log.d("launch", Integer.toString(settings.getSemesters().size()));
        if (settings.getSemesters() == null || settings.getSemesters().size() == 0) {
            Intent intent = new Intent(this, NewSemesterActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, SemesterActivity.class);
            intent.putExtra("semesterid", SemesterDataManager.getCurrentSemester().getId());
            startActivity(intent);
        }
    }
}
