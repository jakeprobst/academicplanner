package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity {

    protected DrawerLayout fullLayout;
    protected FrameLayout frameLayout;
    protected Toolbar toolbar;
    protected DrawerLayout drawer;
    protected ActionBarDrawerToggle toggle;

    private NavDrawerClassAdapter classAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_navigation);



        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
    }

    private void fillToDoList() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.navbar_layout);

        ArrayList<ClassModel> classes = ClassDataManager.getClassesByIDs(SemesterDataManager.getCurrentSemester().getClasses());

        for(ClassModel c: classes) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View cView = inflater.inflate(R.layout.navbar_class, null);

            NavDrawerAssignmentAdapter asAdapter = new NavDrawerAssignmentAdapter(this, c.getId());
            if (asAdapter.assignmentsDue.size() == 0) {
                continue;
            }

            TextView text = (TextView) cView.findViewById(R.id.navbar_classname);
            text.setText(c.getName());

            ListView assignments = (ListView) cView.findViewById(R.id.navbar_assignments);
            assignments.setAdapter(asAdapter);
            assignments.setOnItemClickListener(asAdapter);
            layout.addView(cView);
        }
    }

    @Override
    public void setContentView(int layoutId) {

        fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navigation, null);
        frameLayout = (FrameLayout) fullLayout.findViewById(R.id.navbarcontent);

        toolbar = (Toolbar) getLayoutInflater().inflate(layoutId, frameLayout, true).findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.setContentView(fullLayout);

        fillToDoList();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }*/
}
