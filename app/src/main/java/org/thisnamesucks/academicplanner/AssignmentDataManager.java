package org.thisnamesucks.academicplanner;

import android.content.Context;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
        AssignmentModel as = new AssignmentModel();

        as.setId(21);
        as.setName("Project 1");
        as.setDue("Whenever");
        as.setCurrentScore(100);
        as.setTotalScore(120);
        datastore.writeAssignmentData(as);

        as = new AssignmentModel();

        as.setId(22);
        as.setName("Exam");
        as.setDue("Tomorrow at 6am, have fun studying!");
        as.setCurrentScore(75);
        as.setTotalScore(100);
        datastore.writeAssignmentData(as);

        as = new AssignmentModel();

        as.setId(25);
        as.setName("Essay");
        as.setDue("This coming Wednesday");
        as.setCurrentScore(50);
        as.setTotalScore(50);
        datastore.writeAssignmentData(as);

        as = new AssignmentModel();

        as.setId(27);
        as.setName("Lab");
        as.setDue("Edge of tommorow");
        as.setCurrentScore(80);
        as.setTotalScore(100);
        datastore.writeAssignmentData(as);
    }

    public static void initialize(Context ctx) {
        AssignmentDataManager.ctx = ctx;
        AssignmentDataManager.datastore = new AssignmentDataManagerJSON(ctx);

        makeTestData();
    }

    public static void writeAssignmentData(AssignmentModel assignment) {
        datastore.writeAssignmentData(assignment);
    }

    public static AssignmentModel getAssignmentById(int id) {
        if (!assignmentcache.containsKey(id)){
            assignmentcache.put(id, datastore.getAssignmentById(id));
        }
        return assignmentcache.get(id);
    }

    public static void removeAssignmentData(int assignmentID)
    {
        Util.removeFile(ctx,Integer.toString(assignmentID));
    }

    public static AssignmentModel duplicateAssignmentData(AssignmentModel original, int newId)
    {
        AssignmentModel copy = new AssignmentModel(newId);

        copy.setName(original.getName());
        copy.setDue(original.getDue());
        copy.setCurrentScore(original.getCurrentScore());
        copy.setTotalScore(original.getTotalScore());

        return copy;
    }

    public static ArrayList<AssignmentModel> getAssignmentsByIds(ArrayList<Integer> ids) {
        ArrayList<AssignmentModel> assignmentlist = new ArrayList<>();
        for(Integer id: ids) {
            assignmentlist.add(getAssignmentById(id));
        }
        return assignmentlist;
    }
}
