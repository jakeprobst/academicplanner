package org.thisnamesucks.academicplanner;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jake on 11/9/15. Updated by Carlos 11/13/15.
 */
public class ClassDataManager {
    private static Context ctx;
    private static ClassDataManagerJSON datastore;
    //private static ArrayList<ClassModel> classes = new ArrayList<>();
    private static Map<Integer, ClassModel> classcache = new HashMap<>();

    public static void makeTestData() {
        ClassModel cs = new ClassModel();

        cs.setId(11);
        cs.setName("Data Structures");
        cs.setShortName("CS 315");
        cs.setInstructor("Billy");
        cs.setLetterGrade(true);
        cs.setUnits(4);
        cs.setLocation("Who knows");
        cs.setNotes("I hate this class.");
        cs.setMeetTimes("Don't remember.");
        cs.setCurrentScore(175);
        cs.setTotalScore(220);
        cs.getAssignments().add(21);
        cs.getAssignments().add(22);
        cs.getRubric().getRubricItems().add(new RubricItem(AssignmentType.Project, 25));
        cs.getRubric().getRubricItems().add(new RubricItem(AssignmentType.Exam, 40));
        cs.getRubric().getRubricItems().add(new RubricItem(AssignmentType.FinalExam,25));
        cs.getRubric().getRubricItems().add(new RubricItem(AssignmentType.Homework,10));
        datastore.writeClassData(cs);

        cs = new ClassModel();
        cs.setId(12);
        cs.setName("Software Development");
        cs.setShortName("CS 370");
        cs.setInstructor("Frodo Baggins");
        cs.setLetterGrade(false);
        cs.setUnits(10);
        cs.setLocation("Middle Earth");
        cs.setNotes("I need to destroy the Ring of Power.");
        cs.setMeetTimes("Every day.");
        cs.setCurrentScore(50);
        cs.setTotalScore(50);
        cs.getAssignments().add(25);
        datastore.writeClassData(cs);

        cs = new ClassModel();
        cs.setId(13);
        cs.setName("Computer Architecture");
        cs.setShortName("CS 351");
        cs.setInstructor("Don't know");
        cs.setLetterGrade(true);
        cs.setUnits(2);
        cs.setLocation("Hawaii");
        cs.setNotes("It's an easy A.");
        cs.setMeetTimes("When I feel like it.");
        cs.setCurrentScore(80);
        cs.setTotalScore(100);
        cs.getRubric().getRubricItems().add(new RubricItem(AssignmentType.Lab, 50));
        cs.getRubric().getRubricItems().add(new RubricItem(AssignmentType.Homework, 30));
        cs.getRubric().getRubricItems().add(new RubricItem(AssignmentType.FinalExam, 15));
        cs.getRubric().getRubricItems().add(new RubricItem(AssignmentType.Other, 5));
        cs.getAssignments().add(27);
        datastore.writeClassData(cs);
    }

    public static void initialize(Context ctx) {
        ClassDataManager.ctx = ctx;
        ClassDataManager.datastore = new ClassDataManagerJSON(ctx);

        makeTestData();
    }

    public static void writeClassData(ClassModel classdata) {
        datastore.writeClassData(classdata);
    }

    public static ClassModel getClassById(int id) {
        return datastore.getClassById(id);
    }

    public static ArrayList<ClassModel> getClassesByIDs(ArrayList<Integer> ids) {
        ArrayList<ClassModel> classList = new ArrayList<>();
        for(Integer id: ids) {
            classList.add(getClassById(id));
        }
        return classList;
    }

    public static void deleteClass(ClassModel classModel)
    {
        for(AssignmentModel a: AssignmentDataManager.getAssignmentsByIds(classModel.getAssignments())) {
            AssignmentDataManager.deleteAssignment(a);
        }
        datastore.deleteClass(classModel);

    }

    /*public static void updateScores(ClassModel model)
    {
        int curScore = 0;
        int totScore = 0;
        AssignmentModel tmp;

        for(Integer id: model.getAssignments()) {
            tmp = AssignmentDataManager.getAssignmentById(id);
            curScore += tmp.getCurrentScore();
            totScore += tmp.getTotalScore();
        }

        model.setCurrentScore(curScore);
        model.setTotalScore(totScore);
    }*/
}
