package org.thisnamesucks.academicplanner;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jake on 10/29/15.
 * edited 12-04-15
 */
public class SemesterModel
{
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;
    @SerializedName("season")
    private String season;
    @SerializedName("school")
    private String school;
    @SerializedName("startdate")
    private String startdate;
    @SerializedName("enddate")
    private String enddate;

    @SerializedName("classes")
    private ArrayList<Integer> classes = new ArrayList<>();

    public void setClasses(ArrayList<Integer> classes) {
        this.classes = classes;
    }

    public ArrayList<Integer> getClasses() {
        return classes;
    }

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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

}
