package org.thisnamesucks.academicplanner;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jake on 11/10/15.
 */
public class AssignmentDataManager {
    private static Context ctx;
    private static AssignmentDataManagerJSON datastore;
    //private static ArrayList<SemesterModel> semesters = new ArrayList<>();
    private static Map<Integer, AssignmentModel> assignmentcache = new HashMap<>();

    public static void makeTestData() {
    }

    public static void initialize(Context ctx) {
        AssignmentDataManager.ctx = ctx;
        AssignmentDataManager.datastore = new AssignmentDataManagerJSON(ctx);

        makeTestData();
    }

    public static void writeData(AssignmentModel assignment) {
        datastore.writeAssignmentData(assignment);
    }

    public static AssignmentModel getAssignmentById(int id) {
        if (!assignmentcache.containsKey(id)){
            assignmentcache.put(id, datastore.getAssignmentById(id));
        }
        return assignmentcache.get(id);
    }

    public static ArrayList<AssignmentModel> getAssignmentsByIds(ArrayList<Integer> ids) {
        ArrayList<AssignmentModel> assignmentlist = new ArrayList<>();
        for(Integer id: ids) {
            assignmentlist.add(getAssignmentById(id));
        }
        return assignmentlist;
    }
}
