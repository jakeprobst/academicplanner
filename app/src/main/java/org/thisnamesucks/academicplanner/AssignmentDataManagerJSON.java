package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by jake on 11/10/15.
 */
public class AssignmentDataManagerJSON {
    private Context ctx;

    AssignmentDataManagerJSON(Context ctx) {
        this.ctx = ctx;
    }

    private String getFilePath(int id) {
        return Integer.toString(id);
    }

    public AssignmentModel getAssignmentById(int id) {
        String data = Util.getFileContents(ctx, getFilePath(id));
        Gson gson = new Gson();
        Log.d("from file", data);
        return gson.fromJson(data, AssignmentModel.class);
    }

    public void writeAssignmentData(AssignmentModel assignment) {
        Gson gson = new Gson();
        String json = gson.toJson(assignment);
        Log.d("to file", json);
        Util.writeToFile(ctx, getFilePath(assignment.getId()), json);
    }
}
