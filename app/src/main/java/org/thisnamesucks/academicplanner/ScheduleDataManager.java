package org.thisnamesucks.academicplanner;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by toogymonster on 12/6/15.
 */
public class ScheduleDataManager
{
    private static Context ctx;
    private static ScheduleDataManagerJSON scheduleData;
    private static Map<Integer, ScheduleModel> scheduleCache = new HashMap<>();

    public static void makeTestData()
    {//// TODO: 12/6/15 create schedule data
        ScheduleModel schedulemodel = new ScheduleModel();
        schedulemodel.setSeason("Spring");
        schedulemodel.setId(2);
        scheduleData.writeScheduleData(schedulemodel);
    }

    public static void initialize(Context ctx)
    {
        ScheduleDataManager.ctx = ctx;
        ScheduleDataManager.scheduleData = new ScheduleDataManagerJSON(ctx);
        makeTestData();
    }

    public static void writeScheduleData(ScheduleModel schedule)
    {
        ScheduleModel schedulemodel = new ScheduleModel();
        scheduleData.writeScheduleData(schedule);
    }

    public static ScheduleModel getCurrentSchedule()
    {
        return getScheduleById(2);
    }

    public static ScheduleModel getScheduleById(int id)
    {
        if (!scheduleCache.containsKey(id))
        {
            scheduleCache.put(id, scheduleData.getScheduleById(id));
        }
        return scheduleCache.get(id);
    }
}
