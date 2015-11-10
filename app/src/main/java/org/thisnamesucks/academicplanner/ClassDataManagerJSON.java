package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by jake on 11/9/15.
 */
public class ClassDataManagerJSON {
    private Context ctx;

    ClassDataManagerJSON(Context ctx) {
        this.ctx = ctx;
    }

    private String getFilePath(int id) {
        return Integer.toString(id);
    }

    /*public ArrayList<ClassModel> getClassData() {
        String data = Util.getFileContents(ctx, "semesterdata.json");
        Gson gson = new Gson();
        return gson.fromJson(data, new TypeToken<ArrayList<ClassModel>>() {}.getType());
    }*/
    public ClassModel getClassById(int id) {
        String data = Util.getFileContents(ctx, getFilePath(id));
        Gson gson = new Gson();
        Log.d("class from file", data);
        return gson.fromJson(data, ClassModel.class);
    }

    public void writeClassData(ClassModel classdata) {
        Gson gson = new Gson();
        String json = gson.toJson(classdata);
        Log.d("class to file", json);
        Util.writeToFile(ctx, getFilePath(classdata.getId()), json);
    }
}
