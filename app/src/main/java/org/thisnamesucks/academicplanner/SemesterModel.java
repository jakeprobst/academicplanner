package org.thisnamesucks.academicplanner;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jake on 10/29/15.
 */
public class SemesterModel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("classes")
    private ArrayList<Integer> classes = new ArrayList<>();

    public  void addClass(ClassModel model)
    {
        ClassDataManager.writeClassData(model);
        classes.add(model.getId());
        SemesterDataManager.writeClassData(this);
    }

    public  void removeClass(ClassModel model)
    {
        classes.remove((Object) model.getId());
        ClassDataManager.removeClassData(model.getId());
    }

    public ArrayList<Integer> getClasses() {
        return classes;
    }

    /*@SerializedName("classes")
    private ArrayList<ClassModel> classes;

    public ArrayList<ClassModel> getClasses() {
        return classes;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
