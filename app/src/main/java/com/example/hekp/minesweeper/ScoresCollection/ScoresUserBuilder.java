package com.example.hekp.minesweeper.ScoresCollection;

public class ScoresUserBuilder {
    private String name;
    private int score;
    private double lng;
    private double lat;

    public ScoresUserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ScoresUserBuilder setScore(int score) {
        this.score = score;
        return this;
    }

    public ScoresUserBuilder setLng(double lng) {
        this.lng = lng;
        return this;
    }

    public ScoresUserBuilder setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public ScoresUser build() {
        return new ScoresUser(name, lat, lng, score);
    }
}