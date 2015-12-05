package org.thisnamesucks.academicplanner;

import android.content.Context;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jake on 11/10/15.  Updated by Carlos 11/21/15
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
        as.setDue("12/6/2015");
        as.setCurrentScore(100);
        as.setTotalScore(120);
        as.setType(AssignmentType.Project);
        as.setExtraCredit(false);
        as.setDescription("This is a description about the project (optional)");
        as.setNotes("These are specific notes about the project (optional)");
        datastore.writeAssignmentData(as);

        as = new AssignmentModel();

        as.setId(22);
        as.setName("Exam");
        as.setDue("12/8/2015");
        as.setCurrentScore(75);
        as.setTotalScore(100);
        as.setType(AssignmentType.Exam);
        as.setExtraCredit(false);
        as.setDescription("This is a description of the exam (optional)");
        as.setNotes("These are specific notes about the exam (optional)");
        datastore.writeAssignmentData(as);

        as = new AssignmentModel();

        as.setId(25);
        as.setName("Essay");
        as.setDue("12/12/2015");
        as.setCurrentScore(50);
        as.setTotalScore(50);
        as.setType(AssignmentType.Essay);
        as.setExtraCredit(false);
        as.setDescription("This is a description about the essay (optional)");
        as.setNotes("These are specific notes on the essay (optional)");
        datastore.writeAssignmentData(as);

        as = new AssignmentModel();

        as.setId(27);
        as.setName("Lab");
        as.setDue("12/12/2015");
        as.setCurrentScore(80);
        as.setTotalScore(100);
        as.setType(AssignmentType.Lab);
        as.setExtraCredit(false);
        as.setDescription("This is a description about the essay (optional)");
        as.setNotes("These are specific notes on the essay (optional)");
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
        return datastore.getAssignmentById(id);
    }

    public static void deleteAssignment(AssignmentModel assignmentModel)
    {
        datastore.deleteAssignment(assignmentModel);
    }

    public static ArrayList<AssignmentModel> getAssignmentsByIds(ArrayList<Integer> ids) {
        ArrayList<AssignmentModel> assignmentlist = new ArrayList<>();
        for(Integer id: ids) {
            assignmentlist.add(getAssignmentById(id));
        }
        return assignmentlist;
    }
}
