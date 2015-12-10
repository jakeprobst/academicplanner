package org.thisnamesucks.academicplanner;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by toogymonster on 12/6/15.
 */
public class ScheduleModel {

    @SerializedName("scheduleList")
    private ArrayList<Integer> scheduleList = new ArrayList<>();

    public ArrayList<Integer> getScheduleList()
    {
        return scheduleList;
    }

    @SerializedName("id")
    private int id;
    @SerializedName("season")
    private String season;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
