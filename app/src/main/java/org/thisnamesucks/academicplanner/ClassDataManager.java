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
        cs.setCurrentScore(175);
        cs.setTotalScore(220);
        cs.getAssignments().add(21);
        cs.getAssignments().add(22);
        datastore.writeClassData(cs);

        cs = new ClassModel();
        cs.setId(12);
        cs.setName("Software Development");
        cs.setShortName("CS 370");
        cs.setCurrentScore(50);
        cs.setTotalScore(50);
        cs.getAssignments().add(25);
        datastore.writeClassData(cs);

        cs = new ClassModel();
        cs.setId(13);
        cs.setName("Computer Architecture");
        cs.setShortName("CS 351");
        cs.setCurrentScore(80);
        cs.setTotalScore(100);
        cs.getAssignments().add(27);
        datastore.writeClassData(cs);
    }

    public static void initialize(Context ctx) {
        ClassDataManager.ctx = ctx;
        ClassDataManager.datastore = new ClassDataManagerJSON(ctx);

        makeTestData();
    }

    //public static void writeAssignmentData(ClassModel classdata) {
    //    datastore.writeClassData(classdata);
    //}

    public static void writeClassData(ClassModel classdata) {
        datastore.writeClassData(classdata);
    }

    public static void deleteClassData(ClassModel model) { datastore.removeClassData(model.getId());}

    public static ClassModel getClassById(int id) {
        // set a sort of write-callback?
        // like, in the setters call a function I set here?
        // or just save things manually that might just be easier
        if (!classcache.containsKey(id)){
            classcache.put(id, datastore.getClassById(id));
        }
        return classcache.get(id);
    }

    public static ArrayList<ClassModel> getClassesByIDs(ArrayList<Integer> ids) {
        ArrayList<ClassModel> classList = new ArrayList<>();
        for(Integer id: ids) {
            classList.add(getClassById(id));
        }
        return classList;
    }


    public static void removeClassData(int classID)
    {
        Util.removeFile(ctx,Integer.toString(classID));
    }

    public static void updateScores(ClassModel model)
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
    }
}
