package com.serpider.service.megastream.adapter;

public class Replay {

    private int replay_id;
    private String replay_unique, replay_comment_id, replay_to_username, user_id, user_name, user_nickname, user_vector, replay_user_id, replay_msg, replay_date;

    public Replay(int replay_id, String replay_unique, String replay_comment_id, String replay_to_username, String user_id, String user_name, String user_nickname, String user_vector, String replay_user_id, String replay_msg, String replay_date) {
        this.replay_id = replay_id;
        this.replay_unique = replay_unique;
        this.replay_comment_id = replay_comment_id;
        this.replay_to_username = replay_to_username;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_nickname = user_nickname;
        this.user_vector = user_vector;
        this.replay_user_id = replay_user_id;
        this.replay_msg = replay_msg;
        this.replay_date = replay_date;
    }

    public int getReplay_id() {
        return replay_id;
    }

    public void setReplay_id(int replay_id) {
        this.replay_id = replay_id;
    }

    public String getReplay_unique() {
        return replay_unique;
    }

    public void setReplay_unique(String replay_unique) {
        this.replay_unique = replay_unique;
    }

    public String getReplay_comment_id() {
        return replay_comment_id;
    }

    public void setReplay_comment_id(String replay_comment_id) {
        this.replay_comment_id = replay_comment_id;
    }

    public String getReplay_to_username() {
        return replay_to_username;
    }

    public void setReplay_to_username(String replay_to_username) {
        this.replay_to_username = replay_to_username;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getUser_vector() {
        return user_vector;
    }

    public void setUser_vector(String user_vector) {
        this.user_vector = user_vector;
    }

    public String getReplay_user_id() {
        return replay_user_id;
    }

    public void setReplay_user_id(String replay_user_id) {
        this.replay_user_id = replay_user_id;
    }

    public String getReplay_msg() {
        return replay_msg;
    }

    public void setReplay_msg(String replay_msg) {
        this.replay_msg = replay_msg;
    }

    public String getReplay_date() {
        return replay_date;
    }

    public void setReplay_date(String replay_date) {
        this.replay_date = replay_date;
    }
}
