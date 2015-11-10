package org.thisnamesucks.academicplanner;


import android.content.Context;
import android.util.Log;
import android.util.StringBuilderPrinter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by jake on 10/29/15.
 */
public class SemesterModel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("classes")
    private ArrayList<Integer> classes = new ArrayList<>();


    public ArrayList<Integer> getClasses() {
        return classes;
    }

    /*@SerializedName("classes")
    private ArrayList<ClassModel> classes;

    public ArrayList<ClassModel> getClasses() {
        return classes;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
