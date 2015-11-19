package org.thisnamesucks.academicplanner;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jake on 11/17/15.
 */
public class RubricModel {
    @SerializedName("rubricitems")
    ArrayList<RubricItem> rubricItems;
}
