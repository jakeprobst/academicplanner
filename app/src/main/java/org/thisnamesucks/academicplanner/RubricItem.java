package org.thisnamesucks.academicplanner;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jake on 11/17/15.
 */
public class RubricItem {
    @SerializedName("Type")
    AssignmentType type = AssignmentType.Homework;
    @SerializedName("weight")
    int weight = 0;
}
