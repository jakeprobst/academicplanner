package org.thisnamesucks.academicplanner;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

/**
 * Created by jake on 12/7/15.
 */

public class AssignmentListProvider implements RemoteViewsService.RemoteViewsFactory {
    // java doesnt have a pair builtin, huh
    private class CAPair implements Comparable<CAPair> {
        public ClassModel cl;
        public AssignmentModel as;
        public CAPair(ClassModel c, AssignmentModel a) {
            cl = c;
            as = a;
        }

        public int compareTo(CAPair other) {
            for(int i = 0; i < 3; i++) {
                if (as.getDue().get(i) > other.as.getDue().get(i))
                    return 1;
                if (as.getDue().get(i) < other.as.getDue().get(i))
                    return -1;
            }
            return 0;
        }

    }
    Context context;
    int widgetId;
    ArrayList<CAPair> assignmentList = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());

    public AssignmentListProvider(Context c, Intent intent) {
        context = c;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        SemesterModel sem = SemesterDataManager.getCurrentSemester();
        ArrayList<ClassModel> classes = ClassDataManager.getClassesByIDs(sem.getClasses());
        //ArrayList<AssignmentModel> assignments = new ArrayList<>();
        assignmentList.clear();
        for(ClassModel c: classes) {
            for(AssignmentModel a: AssignmentDataManager.getAssignmentsByIds(c.getAssignments())) {
                assignmentList.add(new CAPair(c, a));
            }
            //assignmentList.addAll(AssignmentDataManager.getAssignmentsByIds(c.getAssignments()));
        }
        Collections.sort(assignmentList);
    }

    public void onDestroy() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public int getViewTypeCount(){
        return 1;
    }

    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getCount() {
        return assignmentList.size();
    }

    @Override
    public RemoteViews getViewAt(int pos) {
        RemoteViews assignmentRow = new RemoteViews(context.getPackageName(), R.layout.widget_assignment_layout);

        CAPair model = assignmentList.get(pos);
        assignmentRow.setTextViewText(R.id.widget_class, model.cl.getShortName());
        assignmentRow.setTextViewText(R.id.widget_name, model.as.getName());

        ArrayList<Integer> date = model.as.getDue();
        Calendar cal = Calendar.getInstance();
        cal.set(date.get(0), date.get(1), date.get(2));
        assignmentRow.setTextViewText(R.id.widget_due, dateFormat.format(cal.getTime()));

        Intent intent = new Intent(context, AssignmentActivity.class);
        intent.putExtra("assignmentid", model.as.getId());
        intent.putExtra("classid", model.cl.getId());

        assignmentRow.setOnClickFillInIntent(R.id.widget_assignment_layout, intent);

        return assignmentRow;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
