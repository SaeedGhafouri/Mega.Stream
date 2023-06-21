package com.serpider.service.megastream.model;

public class Comment {

    private String comment_unique, comment_type, comment_rep_user, comment_user_id, comment_user_nick, comment_user_name, comment_item_title, comment_msg, comment_time, comment_date;

    public Comment(String comment_unique, String comment_type, String comment_rep_user, String comment_user_id, String comment_user_nick, String comment_user_name, String comment_item_title, String comment_msg, String comment_time, String comment_date) {
        this.comment_unique = comment_unique;
        this.comment_type = comment_type;
        this.comment_rep_user = comment_rep_user;
        this.comment_user_id = comment_user_id;
        this.comment_user_nick = comment_user_nick;
        this.comment_user_name = comment_user_name;
        this.comment_item_title = comment_item_title;
        this.comment_msg = comment_msg;
        this.comment_time = comment_time;
        this.comment_date = comment_date;
    }

    public String getComment_unique() {
        return comment_unique;
    }

    public void setComment_unique(String comment_unique) {
        this.comment_unique = comment_unique;
    }

    public String getComment_type() {
        return comment_type;
    }

    public void setComment_type(String comment_type) {
        this.comment_type = comment_type;
    }

    public String getComment_rep_user() {
        return comment_rep_user;
    }

    public void setComment_rep_user(String comment_rep_user) {
        this.comment_rep_user = comment_rep_user;
    }

    public String getComment_user_id() {
        return comment_user_id;
    }

    public void setComment_user_id(String comment_user_id) {
        this.comment_user_id = comment_user_id;
    }

    public String getComment_user_nick() {
        return comment_user_nick;
    }

    public void setComment_user_nick(String comment_user_nick) {
        this.comment_user_nick = comment_user_nick;
    }

    public String getComment_user_name() {
        return comment_user_name;
    }

    public void setComment_user_name(String comment_user_name) {
        this.comment_user_name = comment_user_name;
    }

    public String getComment_item_title() {
        return comment_item_title;
    }

    public void setComment_item_title(String comment_item_title) {
        this.comment_item_title = comment_item_title;
    }

    public String getComment_msg() {
        return comment_msg;
    }

    public void setComment_msg(String comment_msg) {
        this.comment_msg = comment_msg;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }
}
