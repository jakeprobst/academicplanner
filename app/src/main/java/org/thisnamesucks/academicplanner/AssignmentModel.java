package org.thisnamesucks.academicplanner;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by jake on 11/10/15. Updated by Carlos 11/21/15
 */
public class AssignmentModel implements Cloneable {
    @SerializedName("id")
    private int id = 0;
    @SerializedName("totalScore")
    private int totalScore = 0;
    @SerializedName("currentScore")
    private int currentScore = 0;
    //@SerializedName("totalWeightedScore") //In case we decide to display weighted scores for assignments too
    //private double totalWeightedScore = 0;
    //@SerializedName("currentWeightedScore")
    //private int currentWeightedScore = 0;
    @SerializedName("name")
    private String name = "";
    @SerializedName("due")
    ArrayList<Integer> due; // [year, month, day]
    //private String due = "";
    @SerializedName("type")
    AssignmentType type = AssignmentType.Homework;
    @SerializedName("extraCredit")
    private boolean extraCredit = false;
    @SerializedName("description")
    private String description = "";
    @SerializedName("notes")
    private String notes = "";

    public AssignmentModel clone() {
        AssignmentModel copy = new AssignmentModel();
        copy.id = id;
        copy.totalScore = totalScore;
        copy.currentScore = currentScore;
        copy.name = name;
        copy.due = due;
        copy.type = type;
        copy.extraCredit = extraCredit;
        copy.description = description;
        copy.notes = notes;
        return copy;
    }


    public ArrayList<Integer> getDue() { return this.due; }

    public void setDue(ArrayList<Integer> due) { this.due = due; }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getId() {return this.id;}

    public void setId(int id) {this.id = id;}

    public int getTotalScore() { return totalScore; }

    public void setTotalScore(int totalScore) { this.totalScore = totalScore; }

    public int getCurrentScore() { return this.currentScore; }

    public void setCurrentScore(int currentScore) { this.currentScore = currentScore; }
/*
    public double getTotlalWeightedScore() { return totalWeightedScore; }

    public void setTotlalWeightedScore(double totlalWeightedScore) { this.totalWeightedScore = totlalWeightedScore; }

    public int getCurrentWeightedScore() { return currentWeightedScore; }

    public void setCurrentWeightedScore(int currentWeightedScore) { this.currentWeightedScore = currentWeightedScore; }*/

    public AssignmentType getType() {
        return type;
    }

    public void setType(AssignmentType type) {
        this.type = type;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getNotes() { return notes; }

    public void setNotes(String notes) { this.notes = notes; }

    public boolean isExtraCredit() { return extraCredit; }

    public void setExtraCredit(boolean extraCredit) { this.extraCredit = extraCredit; }
}
