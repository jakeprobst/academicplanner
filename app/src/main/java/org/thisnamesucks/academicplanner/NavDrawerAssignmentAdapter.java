package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by jake on 12/1/15.
 */
public class NavDrawerAssignmentAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    Context context;
    int classId;
    ArrayList<AssignmentModel> assignmentsDue;

    NavDrawerAssignmentAdapter(Context c, int cid) {
        context = c;
        classId = cid;
        assignmentsDue = getPendingAssignments();
    }

    private ArrayList<AssignmentModel> getPendingAssignments() {
        // worst line of code in the entire project

        //return AssignmentDataManager.getAssignmentsByIds(ClassDataManager.getClassById(classId).getAssignments());

        ArrayList<AssignmentModel> out = new ArrayList<>();
        for(AssignmentModel assignment: AssignmentDataManager.getAssignmentsByIds(ClassDataManager.getClassById(classId).getAssignments())) {
            // filter here!
            out.add(assignment);
        }

        notifyDataSetChanged();
        return out;
    }

    public int getCount() {
        return assignmentsDue.size();
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View assignmentView = inflater.inflate(R.layout.navbar_assignment, parent, false);

        AssignmentModel assignmentModel = assignmentsDue.get(pos);
        Log.d("showing:", assignmentModel.getName());

        TextView text = (TextView) assignmentView.findViewById(R.id.navbar_assignment_name);
        text.setText(assignmentModel.getName());

        TextView dueDate = (TextView) assignmentView.findViewById(R.id.navbar_assignment_due);
        dueDate.setText(assignmentModel.getDue());
        //dueDate.setText("huh?");

        return assignmentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(view.getContext(), AssignmentActivity.class);
        intent.putExtra("assignmentid", ((AssignmentModel) parent.getItemAtPosition(position)).getId());
        intent.putExtra("classid", classId);
        view.getContext().startActivity(intent);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public AssignmentModel getItem(int pos) {
        return assignmentsDue.get(pos);
    }

    public void setData(ArrayList<AssignmentModel> assignments) {
        assignmentsDue = assignments;
        notifyDataSetChanged();
    }

    public int getClassId() {
        return classId;
    }
}
