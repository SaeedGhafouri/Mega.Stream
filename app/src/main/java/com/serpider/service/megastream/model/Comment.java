package com.serpider.service.megastream.model;

public class Comment {

    /*Result*/
    private boolean STATUS;
    private String MESSAGE;
    public boolean isSTATUS() {
        return STATUS;
    }
    public String getMESSAGE() {
        return MESSAGE;
    }

    /*END Result*/
    private int id, user_id, status, cnt;
    private String msg, username, nickname, profile, date;

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getStatus() {
        return status;
    }

    public int getCnt() {
        return cnt;
    }

    public String getMsg() {
        return msg;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfile() {
        return profile;
    }

    public String getDate() {
        return date;
    }
}
