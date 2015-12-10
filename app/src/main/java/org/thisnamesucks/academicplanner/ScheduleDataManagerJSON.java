package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by toogymonster on 12/6/15.
 */
public class ScheduleDataManagerJSON
{
    private Context ctx;

    ScheduleDataManagerJSON(Context ctx) {
        this.ctx = ctx;
    }

    private String getFilePath(int id) {
        return Integer.toString(id);
    }

    public ScheduleModel getScheduleById(int id)
    {
        String data = Util.getFileContents(ctx, getFilePath(id));
        Gson gson = new Gson();
        Log.d("from file", data);
        return gson.fromJson(data, ScheduleModel.class);
    }

    public void writeScheduleData(ScheduleModel schedule) {
        Gson gson = new Gson();
        String json = gson.toJson(schedule);
        Log.d("to file", json);
        Util.writeToFile(ctx, getFilePath(schedule.getId()), json);
    }
}
