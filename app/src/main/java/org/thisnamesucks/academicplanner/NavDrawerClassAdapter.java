package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jake on 12/1/15.
 */
public class NavDrawerClassAdapter extends BaseAdapter {
    Context context;
    ArrayList<Integer> classList = new ArrayList<>();

    public NavDrawerClassAdapter(Context c) {
        context = c;
        classList = SemesterDataManager.getCurrentSemester().getClasses();
    }

    @Override
    public int getCount() {
        return classList.size();
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View classView = inflater.inflate(R.layout.navbar_class, parent, false);

        ClassModel classModel = ClassDataManager.getClassById(classList.get(pos));

        TextView text = (TextView) classView.findViewById(R.id.navbar_classname);
        text.setText(classModel.getName());

        /*LinearLayout layout = (LinearLayout) classView.findViewById(R.id.navbar_layout);

        for(AssignmentModel assignment: AssignmentDataManager.getAssignmentsByIds(classModel.getAssignments())) {
            View aView = inflater.inflate(R.layout.navbar_assignment, parent, false);

            text = (TextView) aView.findViewById(R.id.navbar_assignment_name);
            text.setText(assignment.getName());


            text = (TextView) aView.findViewById(R.id.navbar_assignment_due);
            Log.d("due2?", assignment.getDue());
            text.setText(assignment.getDue());

            layout.addView(aView);
        }*/



        ListView assignments = (ListView) classView.findViewById(R.id.navbar_assignments);
        NavDrawerAssignmentAdapter asAdapter = new NavDrawerAssignmentAdapter(context, classList.get(pos));
        //asAdapter.setData(AssignmentDataManager.getAssignmentsByIds(ClassDataManager.getClassById(classList.get(pos)).getAssignments()));
        //asAdapter.notifyDataSetChanged();
        assignments.setAdapter(asAdapter);

        //notifyDataSetChanged();
        //assignments.setEnabled(false);

        return classView;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public Integer getItem(int pos) {
        return classList.get(pos);
    }

    /*public void add(ClassModel info) {
        classList.add(info);
        notifyDataSetChanged();
    }*/
}
