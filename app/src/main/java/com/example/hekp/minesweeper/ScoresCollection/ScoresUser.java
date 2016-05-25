package com.example.hekp.minesweeper.ScoresCollection;

/**
 * Created by HEKP on 17-Jan-16.
 */
public class ScoresUser {
    private String mName;
    private int mScore;
    private double mLng;
    private double mLat;

    public ScoresUser(String name, double lat, double lng, int score){
        mName = name;
        mScore = score;
        mLng = lng;
        mLat = lat;
    }

    public double getLat() {
        return mLat;
    }

    public double getLng() {
        return mLng;
    }

    public int getScore() {
        return mScore;
    }

    public String getUserName() {
        return mName;
    }

    @Override
    public String toString(){
        return mName+"\t\t"+mScore;
    }
}
