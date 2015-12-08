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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jake on 12/1/15.
 */
public class NavDrawerAssignmentAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
    Context context;
    int classId;
    ArrayList<AssignmentModel> assignmentsDue;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

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

        ArrayList<Integer> date = assignmentModel.getDue();
        Calendar cal = Calendar.getInstance();
        cal.set(date.get(0), date.get(1), date.get(2));
        dueDate.setText(dateFormat.format(cal.getTime()));

        return assignmentView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(view.getContext(), AssignmentActivity.class);
        intent.putExtra("assignmentid", ((AssignmentModel) parent.getItemAtPosition(position)).getId());
        intent.putExtra("classid", classId);
        intent.putExtra("semesterid", SemesterDataManager.getCurrentSemester().getId());
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
