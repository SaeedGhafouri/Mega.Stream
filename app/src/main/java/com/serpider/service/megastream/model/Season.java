package com.serpider.service.megastream.model;

public class Season {

    private int id;
    private String season_unique, season_title, season_value;
    public Season(int id, String season_unique, String season_title, String season_value) {
        this.id = id;
        this.season_unique = season_unique;
        this.season_title = season_title;
        this.season_value = season_value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSeason_unique() {
        return season_unique;
    }

    public void setSeason_unique(String season_unique) {
        this.season_unique = season_unique;
    }

    public String getSeason_title() {
        return season_title;
    }

    public void setSeason_title(String season_title) {
        this.season_title = season_title;
    }

    public String getSeason_value() {
        return season_value;
    }

    public void setSeason_value(String season_value) {
        this.season_value = season_value;
    }
}
