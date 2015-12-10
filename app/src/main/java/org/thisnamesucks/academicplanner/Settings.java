package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by student on 12/10/15.
 */
public class Settings {
    class SettingsModel {
        @SerializedName("semesters")
        private ArrayList<Integer> semesters = new ArrayList<>();

        public ArrayList<Integer> getSemesters() {
            return semesters;
        }

        public void setSemesters(ArrayList<Integer> semesters) {
            this.semesters = semesters;
        }
    }
    final static String SETTINGSFILE = "assignmentmanager.json";
    private static Settings settings = null;

    private static SettingsModel model;
    private static Context context;

    protected Settings(Context ctx) {
        context = ctx;

        String data = Util.getFileContents(context, SETTINGSFILE);
        if (data == "") {
            model = new SettingsModel();
        }
        else {
            Gson gson = new Gson();
            model = gson.fromJson(data, SettingsModel.class);
        }
    }

    public static void save() {
        Gson gson = new Gson();
        String json = gson.toJson(model);
        Log.d("to file", json);
        Util.writeToFile(context, SETTINGSFILE, json);

    }
    public static SettingsModel getInstance(Context ctx) {
        if (settings == null) {
            settings = new Settings(ctx);
        }

        return settings.model;
    }

    public static SettingsModel getInstance() {
        return settings.model;
    }
}
