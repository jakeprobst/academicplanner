package org.thisnamesucks.academicplanner;

import android.app.Application;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jake on 10/24/15.  Updated by Carlos 11/21/15
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

    //Calculates and updates the current scores for a class
    private void updateScores(ClassModel classModel)
    {

        //Calculate unweighted totals
        int curScore = 0;
        int totScore = 0;
        AssignmentModel tmp;

        for(Integer id: classModel.getAssignments()) {
            tmp = AssignmentDataManager.getAssignmentById(id);
            curScore += tmp.getCurrentScore();

            if (!tmp.isExtraCredit())
                totScore += tmp.getTotalScore();
        }

        //Calculated weighed totals
        double[] scores = new double[AssignmentType.values().length];
        double[] totals = new double[AssignmentType.values().length];

        ArrayList<AssignmentModel> assignments = AssignmentDataManager.getAssignmentsByIds(classModel.getAssignments());
        for(AssignmentModel a: assignments) {
            scores[a.getType().ordinal()] += a.getCurrentScore();

            if(!a.isExtraCredit())
                totals[a.getType().ordinal()] += a.getTotalScore();
        }

        double weightedScore = 0;
        double weightedTotal = 0;
        RubricModel rubric = classModel.getRubric();
        for(RubricItem item: rubric.getRubricItems()) {
            weightedScore += scores[item.getType().ordinal()]*(item.getWeight());
            weightedTotal += totals[item.getType().ordinal()]*(item.getWeight());
        }

        //Update model
        classModel.setCurrentScore(curScore);
        classModel.setTotalScore(totScore);
        classModel.setCurrentScoreWeighted(weightedScore/100);
        classModel.setTotalScoreWeighted(weightedTotal/100);
    }

    //Calculates a weighted or unweighted grade for the class
    private int calculateGrade(ClassModel classModel) {

        //Return unweighted grade if no rubric is available
        if (classModel.getRubric().getRubricItems().isEmpty()) {
            if(classModel.getTotalScore()==0) //Set grade at 100% if total score is 0 to avoid division by 0
                return 100;

            return (100*classModel.getCurrentScore()) / classModel.getTotalScore();//Return unweighted grade percentage
        }

        //Return weighted grade
        if(classModel.getTotalScoreWeighted()==0) //Set grade at 100% if total weighed score is 0 to avoid division by 0
            return 100;

        return (int)(100*(classModel.getCurrentScoreWeighted()/classModel.getTotalScoreWeighted()));//Return weighted grade
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View classRow = inflater.inflate(R.layout.class_information_layout, parent, false);

        TextView text;
        ClassModel classItem = classList.get(pos);

        text = (TextView) classRow.findViewById(R.id.class_name);
        text.setText(classItem.getName());
        text = (TextView) classRow.findViewById(R.id.class_id);
        text.setText(classItem.getShortName());

        updateScores(classItem);
        int grade = calculateGrade(classItem); //Calculate score with user entered rubric data if available

        if(classItem.getTotalScore() > 0) {

            text = (TextView) classRow.findViewById(R.id.class_percent);

            if (grade > 90) {
                text.setTextColor(Color.rgb(0, 0xDD, 0));
            } else if (grade > 80) {
                text.setTextColor(Color.rgb(0x22, 0xCC, 0));
            } else if (grade > 70) {
                text.setTextColor(Color.rgb(0x99, 0xBB, 0));
            } else if (grade > 60) {
                text.setTextColor(Color.rgb(0xDD, 0, 0));
            } else {
                text.setTextColor(Color.rgb(0xFF, 0, 0));
            }
            text.setText(Integer.toString(grade) + "%");

            //Temporary option variable for showing weighted scores
            boolean showWeighted = false;

            //Display weighted score if a rubric is available and option to display weighted scores is true
            if(!(classItem.getRubric().getRubricItems().isEmpty()) && showWeighted)
            {
                text = (TextView) classRow.findViewById(R.id.class_score);
                text.setText(Double.toString(classItem.getCurrentScoreWeighted()));
                text = (TextView) classRow.findViewById(R.id.class_total_score);
                text.setText(Double.toString(classItem.getTotalScoreWeighted()) + " (weighted)");
            }
            else {
                text = (TextView) classRow.findViewById(R.id.class_score);
                text.setText(Integer.toString(classItem.getCurrentScore()));
                text = (TextView) classRow.findViewById(R.id.class_total_score);
                text.setText(Integer.toString(classItem.getTotalScore()));
            }
        }
        else{
            text = (TextView) classRow.findViewById(R.id.score_slash);

            if(classItem.getCurrentScore() == 0)
                text.setText("No Scores Set");
            else
                text.setText("Current points: " + Integer.toString(classItem.getCurrentScore()));
        }

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
