package com.serpider.service.megastream.model;

public class Donate {
    private String donate_unique, donate_title, donate_desc, donate_image, donate_active, donate_time, donate_date;

    public Donate(String donate_unique, String donate_title, String donate_desc, String donate_image, String donate_active, String donate_time, String donate_date) {
        this.donate_unique = donate_unique;
        this.donate_title = donate_title;
        this.donate_desc = donate_desc;
        this.donate_image = donate_image;
        this.donate_active = donate_active;
        this.donate_time = donate_time;
        this.donate_date = donate_date;
    }

    public String getDonate_unique() {
        return donate_unique;
    }

    public void setDonate_unique(String donate_unique) {
        this.donate_unique = donate_unique;
    }

    public String getDonate_title() {
        return donate_title;
    }

    public void setDonate_title(String donate_title) {
        this.donate_title = donate_title;
    }

    public String getDonate_desc() {
        return donate_desc;
    }

    public void setDonate_desc(String donate_desc) {
        this.donate_desc = donate_desc;
    }

    public String getDonate_image() {
        return donate_image;
    }

    public void setDonate_image(String donate_image) {
        this.donate_image = donate_image;
    }

    public String getDonate_active() {
        return donate_active;
    }

    public void setDonate_active(String donate_active) {
        this.donate_active = donate_active;
    }

    public String getDonate_time() {
        return donate_time;
    }

    public void setDonate_time(String donate_time) {
        this.donate_time = donate_time;
    }

    public String getDonate_date() {
        return donate_date;
    }

    public void setDonate_date(String donate_date) {
        this.donate_date = donate_date;
    }
}
