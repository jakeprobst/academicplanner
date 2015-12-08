package org.thisnamesucks.academicplanner;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.ListView;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class AssignmentsDueWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Log.d("update:", Integer.toString(appWidgetId));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.assignments_due_widget);
        views.setTextViewText(R.id.appwidget_text, "Assignments");

        Intent gotoapp = new Intent(context, AssignmentActivity.class);
        PendingIntent pintent = PendingIntent.getActivity(context, 0, gotoapp, 0);
        views.setPendingIntentTemplate(R.id.widget_list, pintent);

        Intent intent = new Intent(context, AssignmentListService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        views.setRemoteAdapter(R.id.widget_list, intent);
        views.setEmptyView(R.id.widget_list, R.id.empty_assignment_notice);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        /*SemesterDataManager.initialize(context);
        ClassDataManager.initialize(context);
        AssignmentDataManager.initialize(context);*/
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void notifyDataChanged(Context ctx) {
        Log.d("notify", "did this even get seen?");
        AppWidgetManager manager = AppWidgetManager.getInstance(ctx);
        int[] ids = manager.getAppWidgetIds(new ComponentName(ctx, AssignmentsDueWidget.class));
        for(int id: ids) {
            Log.d("notify", Integer.toString(id));
            manager.notifyAppWidgetViewDataChanged(id, R.id.widget_list);
        }
    }
}

