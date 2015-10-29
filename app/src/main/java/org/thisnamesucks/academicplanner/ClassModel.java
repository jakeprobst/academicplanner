package org.thisnamesucks.academicplanner;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jake on 10/29/15.
 */
public class ClassModel {
    @SerializedName("name")
    public String name;
    @SerializedName("id")
    public String id;
    @SerializedName("currentscore")
    public int currentScore;
    @SerializedName("totalscore")
    public int totalScore;
}
