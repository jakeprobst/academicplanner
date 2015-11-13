package org.thisnamesucks.academicplanner;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jake on 10/29/15.
 */
public class ClassModel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("shortname")
    private String shortName;
    @SerializedName("currentscore")
    private int currentScore;
    @SerializedName("totalscore")
    private int totalScore;
    @SerializedName("assignmentList")
    private ArrayList<Integer> assignmentList = new ArrayList<>();

    public ArrayList<Integer> getAssignments() {
        return assignmentList;
    }

    public void setAssignments(ArrayList<Integer> assignmentList) {
        this.assignmentList = assignmentList;
    }

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
}
