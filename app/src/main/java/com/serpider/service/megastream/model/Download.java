package com.serpider.service.megastream.model;

import androidx.recyclerview.widget.RecyclerView;

import com.serpider.service.megastream.adapter.CommentAdapter;

public class Download {

    private String title, year, timer;
    private int progress;

    public Download(String title, String year, String timer, int progress) {
        this.title = title;
        this.year = year;
        this.timer = timer;
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
