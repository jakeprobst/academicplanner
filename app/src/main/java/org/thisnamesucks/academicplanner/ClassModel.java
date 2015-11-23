package org.thisnamesucks.academicplanner;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jake on 10/29/15.  Updated by Carlos 11/21/15
 */
public class ClassModel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name = "";
    @SerializedName("shortName")
    private String shortName = "";
    @SerializedName("currentScore")
    private int currentScore = 0;
    @SerializedName("currentScoreWeighted")
    private double currentScoreWeighted = 0;
    @SerializedName("totalScore")
    private int totalScore= 0;
    @SerializedName("totalScoreWeighted")
    private double totalScoreWeighted = 0;
    @SerializedName("rubric")
    private RubricModel rubric = new RubricModel();
    @SerializedName("units")
    private double units = 0;
    @SerializedName("letterGrade")
    private boolean letterGrade = true;
    @SerializedName("instructor")
    private String instructor = "";
    @SerializedName("location")
    private String location = "";
    @SerializedName("meetTimes")
    private String meetTimes = "";
    @SerializedName("notes")
    private String notes = "";

    @SerializedName("assignmentList")
    private ArrayList<Integer> assignmentList = new ArrayList<>();

    public ArrayList<Integer> getAssignments() {
        return assignmentList;
    }

    public void setAssignments(ArrayList<Integer> assignmentList) { this.assignmentList = assignmentList; }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public double getCurrentScoreWeighted() { return currentScoreWeighted; }

    public void setCurrentScoreWeighted(double currentScoreWeighted) { this.currentScoreWeighted = currentScoreWeighted; }

    public double getTotalScoreWeighted() { return totalScoreWeighted; }

    public void setTotalScoreWeighted(double totalScoreWeighted) { this.totalScoreWeighted = totalScoreWeighted; }

    public RubricModel getRubric() {
        return rubric;
    }

    public void setRubric(RubricModel rubric) {
        this.rubric = rubric;
    }

    public double getUnits() { return units; }

    public void setUnits(double units) { this.units = units; }

    public boolean isLetterGrade() { return letterGrade; }

    public void setLetterGrade(boolean letterGrade) { this.letterGrade = letterGrade; }

    public String getInstructor() { return instructor; }

    public void setInstructor(String instructor) { this.instructor = instructor; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public String getMeetTimes() { return meetTimes; }

    public void setMeetTimes(String meetTimes) { this.meetTimes = meetTimes; }
}
