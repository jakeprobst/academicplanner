package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jake on 11/9/15.
 */

public class SemesterDataManagerJSON {
    private Context ctx;

    SemesterDataManagerJSON(Context ctx) {
        this.ctx = ctx;
    }

    private String getFilePath(int id) {
        return Integer.toString(id);
    }

    public SemesterModel getSemesterById(int id)
    {
        String data = Util.getFileContents(ctx, getFilePath(id));
        Gson gson = new Gson();
        Log.d("from file", data);
        return gson.fromJson(data, SemesterModel.class);
    }

    public void writeSemesterData(SemesterModel semester) {
        Gson gson = new Gson();
        String json = gson.toJson(semester);
        Log.d("to file", json);
        Util.writeToFile(ctx, getFilePath(semester.getId()), json);
    }
}
