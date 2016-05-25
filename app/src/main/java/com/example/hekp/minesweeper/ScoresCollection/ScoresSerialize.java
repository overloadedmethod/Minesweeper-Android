package com.example.hekp.minesweeper.ScoresCollection;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by HEKP on 17-Jan-16.
 */
public class ScoresSerialize {

    private Context mContext;

    public ScoresSerialize(Context context){
        mContext = context;
    }

    public boolean serializeScoresCollection(ScoresCollection collection){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(mContext.openFileOutput("scores.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(new Gson().toJson(collection.getScores().toArray()));
            outputStreamWriter.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public  ScoresCollection deserializeScoresCollection(){

        try {
            InputStream inputStream = mContext.openFileInput("scores.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();

                ScoresCollection collection = new ScoresCollection();
                Gson gson = new Gson();
                ScoresUser[] users = gson.fromJson(stringBuilder.toString(),ScoresUser[].class);
                collection.setScores(users);
                return collection;
            }
        }
        catch (Exception e) {
            return null;
        }
        return null;
    }
}
