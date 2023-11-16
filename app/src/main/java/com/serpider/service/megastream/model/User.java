package com.serpider.service.megastream.model;

public class User {

    /*Result*/
    private boolean result;
    private String message;

    /*END Result*/

    /*User Model*/
    private int id, gender, email_verfy, phone_verfy, account_verfy, status, login_activity, android, ios, windows, otp;
    private String username, nickname, email, phone, password, vector;

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getVector() {
        return vector;
    }

    public void setVector(String vector) {
        this.vector = vector;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getEmail_verfy() {
        return email_verfy;
    }

    public void setEmail_verfy(int email_verfy) {
        this.email_verfy = email_verfy;
    }

    public int getPhone_verfy() {
        return phone_verfy;
    }

    public void setPhone_verfy(int phone_verfy) {
        this.phone_verfy = phone_verfy;
    }

    public int getAccount_verfy() {
        return account_verfy;
    }

    public void setAccount_verfy(int account_verfy) {
        this.account_verfy = account_verfy;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLogin_activity() {
        return login_activity;
    }

    public void setLogin_activity(int login_activity) {
        this.login_activity = login_activity;
    }

    public int getAndroid() {
        return android;
    }

    public void setAndroid(int android) {
        this.android = android;
    }

    public int getIos() {
        return ios;
    }

    public void setIos(int ios) {
        this.ios = ios;
    }

    public int getWindows() {
        return windows;
    }

    public void setWindows(int windows) {
        this.windows = windows;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
