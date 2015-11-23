package org.thisnamesucks.academicplanner;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jake on 11/17/15.  Updated by Carlos 11/21/15
 */
public class RubricItem {
    @SerializedName("Type")
    private AssignmentType type = AssignmentType.Homework;
    @SerializedName("weight")
    private int weight = 0;

    public RubricItem(AssignmentType type, int weight)
    {
        this.type = type;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public AssignmentType getType() {
        return type;
    }

    public void setType(AssignmentType type) {
        this.type = type;
    }

}
