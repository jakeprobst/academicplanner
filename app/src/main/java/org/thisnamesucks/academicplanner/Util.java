package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by jake on 11/9/15.
 */
public class Util {
    public static String getFileContents(Context ctx, String path) {
        String s = "";
        try {
            FileInputStream file = ctx.openFileInput(path);
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
        return s;
    }

    public static void removeFile(Context ctx, String path) {

        try {
            if(ctx.deleteFile(path)){
                boolean worked = true;
            }
        }
        catch (Exception e) {
            Log.d("exception", "stringbuilding: " + e.toString());
        }
    }

    public static void writeToFile(Context ctx, String path, String data) {
        try {
            FileOutputStream file = ctx.openFileOutput(path, Context.MODE_PRIVATE);
            file.write(data.getBytes());
            file.close();
        } catch (Exception e) {
            Log.d("exception", "writeToFile: " + e.toString());
        }
    }
}
