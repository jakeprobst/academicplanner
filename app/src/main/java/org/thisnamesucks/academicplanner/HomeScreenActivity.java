package org.thisnamesucks.academicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(R.id.homescreen_img);
        imageView.setImageResource(R.drawable.home_icon);

        Button semesters_btn = (Button) findViewById(R.id.semesters_btn);
        semesters_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, ScheduleActivity.class);
                startActivity(intent);
            }
        });

    }

}