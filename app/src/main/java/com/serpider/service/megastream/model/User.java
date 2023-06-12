package com.serpider.service.megastream.model;

public class User {

    /*Result*/
    private boolean STATUS;
    private String MESSAGE;
    private String UNIQUE_ID;

    public boolean isSTATUS() {
        return STATUS;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public String getUNIQUE_ID() {
        return UNIQUE_ID;
    }
    /*END Result*/

    /*User Model*/
    private String user_unique ,user_name , user_nickname, user_phone, user_email, user_password, user_gender, user_vector, user_time, user_date, user_status, user_level, user_club_level, user_club_score;

    public User(boolean STATUS, String MESSAGE, String UNIQUE_ID, String user_unique, String user_name, String user_nickname, String user_phone, String user_email, String user_password, String user_gender, String user_vector, String user_time, String user_date, String user_status, String user_level, String user_club_level, String user_club_score) {
        this.STATUS = STATUS;
        this.MESSAGE = MESSAGE;
        this.UNIQUE_ID = UNIQUE_ID;
        this.user_unique = user_unique;
        this.user_name = user_name;
        this.user_nickname = user_nickname;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_gender = user_gender;
        this.user_vector = user_vector;
        this.user_time = user_time;
        this.user_date = user_date;
        this.user_status = user_status;
        this.user_level = user_level;
        this.user_club_level = user_club_level;
        this.user_club_score = user_club_score;
    }

    public String getUser_unique() {
        return user_unique;
    }

    public void setUser_unique(String user_unique) {
        this.user_unique = user_unique;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_vector() {
        return user_vector;
    }

    public void setUser_vector(String user_vector) {
        this.user_vector = user_vector;
    }

    public String getUser_time() {
        return user_time;
    }

    public void setUser_time(String user_time) {
        this.user_time = user_time;
    }

    public String getUser_date() {
        return user_date;
    }

    public void setUser_date(String user_date) {
        this.user_date = user_date;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
        this.user_level = user_level;
    }

    public String getUser_club_level() {
        return user_club_level;
    }

    public void setUser_club_level(String user_club_level) {
        this.user_club_level = user_club_level;
    }

    public String getUser_club_score() {
        return user_club_score;
    }

    public void setUser_club_score(String user_club_score) {
        this.user_club_score = user_club_score;
    }
}
