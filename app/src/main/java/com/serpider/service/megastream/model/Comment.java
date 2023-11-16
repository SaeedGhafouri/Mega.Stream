package com.serpider.service.megastream.model;

public class Comment {

    /*Result*/
    private boolean status;

    private String message_result;

    /*END Result*/
    private int id, u_id, reply_count;
    private String message, date, u_username, u_nickname, u_vector;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage_result() {
        return message_result;
    }

    public void setMessage_result(String message_result) {
        this.message_result = message_result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getReply_count() {
        return reply_count;
    }

    public void setReply_count(int reply_count) {
        this.reply_count = reply_count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getU_username() {
        return u_username;
    }

    public void setU_username(String u_username) {
        this.u_username = u_username;
    }

    public String getU_nickname() {
        return u_nickname;
    }

    public void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }

    public String getU_vector() {
        return u_vector;
    }

    public void setU_vector(String u_vector) {
        this.u_vector = u_vector;
    }
}
