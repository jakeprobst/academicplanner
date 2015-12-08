package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.util.Log;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jake on 11/9/15.
 */

public class SemesterDataManager {
    private static Context ctx;
    private static SemesterDataManagerJSON datastore;

    public static void makeTestData() {
        SemesterModel sem = new SemesterModel();
        sem.setName("Fall 2015");
        sem.setId(1);
        sem.getClasses().add(11);
        sem.getClasses().add(12);
        sem.getClasses().add(13);
        datastore.writeSemesterData(sem);
    }

    public static void initialize(Context ctx) {
        SemesterDataManager.ctx = ctx;
        SemesterDataManager.datastore = new SemesterDataManagerJSON(ctx);

        makeTestData();
    }

    public static void writeSemesterData(SemesterModel semester) {
        datastore.writeSemesterData(semester);
    }

    // TODO: getCurrentSemester should not be a thing
    public static SemesterModel getCurrentSemester() {
        return getSemesterById(1);
    }

    public static SemesterModel getSemesterById(int id) {
        return datastore.getSemesterById(id);
    }
}
