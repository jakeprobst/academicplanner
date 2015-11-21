package org.thisnamesucks.academicplanner;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jake on 11/17/15.
 */
public class RubricModel {
    @SerializedName("rubricitems")
    private ArrayList<RubricItem> rubricItems = new ArrayList<>();

    public ArrayList<RubricItem> getRubricItems() {
        return rubricItems;
    }

    public void setRubricItems(ArrayList<RubricItem> rubricItems) {
        this.rubricItems = rubricItems;
    }
}
