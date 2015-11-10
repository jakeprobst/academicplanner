package org.thisnamesucks.academicplanner;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jake on 11/9/15.
 */
public class ClassDataManager {
    private static Context ctx;
    private static ClassDataManagerJSON datastore;
    //private static ArrayList<ClassModel> classes = new ArrayList<>();
    //private static Map<Integer, ClassModel> classes = new HashMap<>();

    public static void makeTestData() {
        ClassModel cs = new ClassModel();
        cs.setId(11);
        cs.setName("Data Structures");
        cs.setShortName("CS 315");
        cs.setCurrentScore(100);
        cs.setTotalScore(120);
        datastore.writeClassData(cs);

        cs = new ClassModel();
        cs.setId(12);
        cs.setName("Software Development");
        cs.setShortName("CS 370");
        cs.setCurrentScore(120);
        cs.setTotalScore(170);
        datastore.writeClassData(cs);

        cs = new ClassModel();
        cs.setId(13);
        cs.setName("Computer Architecture");
        cs.setShortName("CS 351");
        cs.setCurrentScore(10);
        cs.setTotalScore(100);
        datastore.writeClassData(cs);
    }

    public static void initialize(Context ctx) {
        ClassDataManager.ctx = ctx;
        ClassDataManager.datastore = new ClassDataManagerJSON(ctx);

        makeTestData();

        //classes = datastore.getClassData();
    }

    public static void writeData() {
        //datastore.writeClassData();
    }


    public static ClassModel getClassById(int id) {
        // set a sort of write-callback?
        // like, in the setters call a function I set here?
        // or just save things manually that might just be easier
        return datastore.getClassData(id);
    }

    public static ArrayList<ClassModel> getByIDs(ArrayList<Integer> ids) {
        ArrayList<ClassModel> classlist = new ArrayList<>();
        for(Integer id: ids) {
            classlist.add(getClassById(id));
        }
        return classlist;
    }




}