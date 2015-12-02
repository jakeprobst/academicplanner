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

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        SemesterDataManager.initialize(this);
        ClassDataManager.initialize(this);
        AssignmentDataManager.initialize(this);


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

            TextView text = (TextView) cView.findViewById(R.id.navbar_classname);
            text.setText(c.getName());

            ListView assignments = (ListView) cView.findViewById(R.id.navbar_assignments);
            NavDrawerAssignmentAdapter asAdapter = new NavDrawerAssignmentAdapter(this, c.getId());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
