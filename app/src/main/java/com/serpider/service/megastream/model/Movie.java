package com.serpider.service.megastream.model;

import java.util.List;

public class Movie{

    private String movie_play_unique, movie_id, movie_title, movie_quality, movie_language, movie_size, movie_status, movie_date, movie_play_url;

    public Movie(String movie_play_unique, String movie_id, String movie_title, String movie_quality, String movie_language, String movie_size, String movie_status, String movie_date, String movie_play_url) {
        this.movie_play_unique = movie_play_unique;
        this.movie_id = movie_id;
        this.movie_title = movie_title;
        this.movie_quality = movie_quality;
        this.movie_language = movie_language;
        this.movie_size = movie_size;
        this.movie_status = movie_status;
        this.movie_date = movie_date;
        this.movie_play_url = movie_play_url;
    }

    public String getMovie_play_unique() {
        return movie_play_unique;
    }

    public void setMovie_play_unique(String movie_play_unique) {
        this.movie_play_unique = movie_play_unique;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_quality() {
        return movie_quality;
    }

    public void setMovie_quality(String movie_quality) {
        this.movie_quality = movie_quality;
    }

    public String getMovie_language() {
        return movie_language;
    }

    public void setMovie_language(String movie_language) {
        this.movie_language = movie_language;
    }

    public String getMovie_size() {
        return movie_size;
    }

    public void setMovie_size(String movie_size) {
        this.movie_size = movie_size;
    }

    public String getMovie_status() {
        return movie_status;
    }

    public void setMovie_status(String movie_status) {
        this.movie_status = movie_status;
    }

    public String getMovie_date() {
        return movie_date;
    }

    public void setMovie_date(String movie_date) {
        this.movie_date = movie_date;
    }

    public String getMovie_play_url() {
        return movie_play_url;
    }

    public void setMovie_play_url(String movie_play_url) {
        this.movie_play_url = movie_play_url;
    }
}
