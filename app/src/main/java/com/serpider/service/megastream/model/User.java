package com.serpider.service.megastream.model;

public class User {

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

    /*User Model*/
    private int id, status;
    private String username ,nickname , password, password_2fa, profile, phone, email, mac_address, role, join_date;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword_2fa() {
        return password_2fa;
    }

    public String getProfile() {
        return profile;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getMac_address() {
        return mac_address;
    }

    public String getRole() {
        return role;
    }

    public int getStatus() {
        return status;
    }

    public String getJoin_date() {
        return join_date;
    }
}
