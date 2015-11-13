package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by CAD on 010 11/10/2015.
 */
public class AssignmentInformationAdapter extends BaseAdapter {

    Context context;
    ArrayList<AssignmentModel> assignmentList;


    public AssignmentInformationAdapter(Context c, ArrayList<AssignmentModel> cl) {
        context = c;
        assignmentList = cl;
    }

    @Override
    public int getCount() {
        return assignmentList.size();
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View classRow = inflater.inflate(R.layout.class_information_layout, parent, false);

        TextView text;
        text = (TextView) classRow.findViewById(R.id.assignment_name);
        Log.d("pos", Integer.toString(pos));
        Log.d("list", assignmentList.toString());
        text.setText(assignmentList.get(pos).getName());

        text = (TextView) classRow.findViewById(R.id.assignment_score);
        text.setText(Integer.toString(assignmentList.get(pos).getCurrentScore()));
        double grade = 1.0*assignmentList.get(pos).getCurrentScore()/assignmentList.get(pos).getTotalScore();

        if (grade > .9) {
            text.setTextColor(Color.rgb(0, 0xDD, 0));
        }
        else if (grade > .8) {
            text.setTextColor(Color.rgb(0x22, 0xCC, 0));
        }
        else if (grade > .7) {
            text.setTextColor(Color.rgb(0x99, 0xBB, 0));
        }
        else if (grade > .6) {
            text.setTextColor(Color.rgb(0xDD, 0, 0));
        }
        else {
            text.setTextColor(Color.rgb(0xFF, 0, 0));
        }

        text = (TextView) classRow.findViewById(R.id.assignment_total_score);
        text.setText(Integer.toString(assignmentList.get(pos).getTotalScore()));

        return classRow;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public AssignmentModel getItem(int pos) {
        return assignmentList.get(pos);
    }

    public void add(AssignmentModel info) {
        assignmentList.add(info);
        notifyDataSetChanged();
    }
    
}
