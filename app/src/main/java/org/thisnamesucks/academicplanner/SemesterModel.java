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
import java.util.ArrayList;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by jake on 10/29/15.
 */
public class SemesterModel {
    @SerializedName("name")
    private String name;

    @SerializedName("classes")
    private ArrayList<ClassModel> classes;

    public ArrayList<ClassModel> getClasses() {
        return classes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void makeTestFile(Context ctx) {

        String out = "{`name`: `Fall 2015`, `classes` : [" +
                "{`name`:`Software Development`, `id`: `CS370`, `currentscore`: 280, `totalscore`: 300}, " +
                "{`name`:`Data Structures`, `id`: `CS315`, `currentscore`: 350, `totalscore`: 450}, " +
                "{`name`:`Computer Architecture`, `id`: `CS351`, `currentscore`: 20, `totalscore`: 110} " +
                "] }".replace('`', '"');

        try {
            FileOutputStream file = ctx.openFileOutput("semesterinfo.json", Context.MODE_PRIVATE);
            file.write(out.getBytes());
            file.close();
        }
        catch(Exception e) {
            Log.d("exception", "makeTestFile: " + e.toString());
        }

    }

    SemesterModel(Context ctx) {
        classes = new ArrayList<ClassModel>();

        Log.d("something", "something more");
        makeTestFile(ctx);

        String s = "";
        try {
            // TODO: make this shit its own function
            FileInputStream file = ctx.openFileInput("semesterinfo.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(file));
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            s = sb.toString();
        }
        catch (Exception e) {
            Log.d("exception", "stringbuilding: " + e.toString());
        }


        try {
            JsonParser parser = new JsonParser();
            JsonObject semester = parser.parse(s).getAsJsonObject();
            name = semester.get("name").getAsString();

            Gson gson = new Gson();
            for(JsonElement e : semester.getAsJsonArray("classes")) {
                classes.add(gson.fromJson(e, ClassModel.class));
            }

            Log.d("classes", classes.toString());
        }
        catch (Exception e) {
            Log.d("exception", "jsoning: " + e.toString());
        }
    }
}
