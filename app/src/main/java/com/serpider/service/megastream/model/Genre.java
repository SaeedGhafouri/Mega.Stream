package com.serpider.service.megastream.model;

public class Genre {

    private int genre_id;
    private String genre_name, genre_value, genre_icon, genre_banner;

    public Genre(int genre_id, String genre_name, String genre_value, String genre_icon, String genre_banner) {
        this.genre_id = genre_id;
        this.genre_name = genre_name;
        this.genre_value = genre_value;
        this.genre_icon = genre_icon;
        this.genre_banner = genre_banner;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public String getGenre_name() {
        return genre_name;
    }

    public void setGenre_name(String genre_name) {
        this.genre_name = genre_name;
    }

    public String getGenre_value() {
        return genre_value;
    }

    public void setGenre_value(String genre_value) {
        this.genre_value = genre_value;
    }

    public String getGenre_icon() {
        return genre_icon;
    }

    public void setGenre_icon(String genre_icon) {
        this.genre_icon = genre_icon;
    }

    public String getGenre_banner() {
        return genre_banner;
    }

    public void setGenre_banner(String genre_banner) {
        this.genre_banner = genre_banner;
    }
}
