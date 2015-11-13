package org.thisnamesucks.academicplanner;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jake on 11/10/15.
 */
public class AssignmentModel {
    @SerializedName("id")
    private int id = 0;

    @SerializedName("totalScore")
    private int totalScore = 0;

    @SerializedName("currentScore")
    private int currentScore = 0;

    @SerializedName("name")
    private String name = "";

    @SerializedName("due")
    private String due = "";

    public String getDue() {
        return this.due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getId() {return this.id;}

    public void setId(int id) {this.id = id;}

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getCurrentScore() {
        return this.currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }
}
