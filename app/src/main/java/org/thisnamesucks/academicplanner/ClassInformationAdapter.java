package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jake on 10/24/15.
 */
public class ClassInformationAdapter extends BaseAdapter {
    Context context;
    ArrayList<ClassModel> classList;


    public ClassInformationAdapter(Context c, ArrayList<ClassModel> cl) {
        context = c;
        classList = cl;
    }

    @Override
    public int getCount() {
        return classList.size();
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View classRow = inflater.inflate(R.layout.class_information_layout, parent, false);

        TextView text;
        text = (TextView) classRow.findViewById(R.id.class_name);
        text.setText(classList.get(pos).name);
        text = (TextView) classRow.findViewById(R.id.class_id);
        text.setText(classList.get(pos).id);
        text = (TextView) classRow.findViewById(R.id.class_score);
        text.setText(Integer.toString(classList.get(pos).currentScore));
        double grade = 1.0*classList.get(pos).currentScore/classList.get(pos).totalScore;

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

        text = (TextView) classRow.findViewById(R.id.class_total_score);
        text.setText(Integer.toString(classList.get(pos).totalScore));

        return classRow;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public ClassModel getItem(int pos) {
        return classList.get(pos);
    }

    public void add(ClassModel info) {
        classList.add(info);
        notifyDataSetChanged();
    }
}
