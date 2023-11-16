package com.serpider.service.megastream.model;

public class Settings {
    private boolean power;
    private String sup_phone, sup_email, sup_telegram, sup_insta, sup_x;
    private int count_item;


    public int getCount_item() {
        return count_item;
    }

    public void setCount_item(int count_item) {
        this.count_item = count_item;
    }

    public String getSup_phone() {
        return sup_phone;
    }

    public void setSup_phone(String sup_phone) {
        this.sup_phone = sup_phone;
    }

    public String getSup_email() {
        return sup_email;
    }

    public void setSup_email(String sup_email) {
        this.sup_email = sup_email;
    }

    public String getSup_telegram() {
        return sup_telegram;
    }

    public void setSup_telegram(String sup_telegram) {
        this.sup_telegram = sup_telegram;
    }

    public String getSup_insta() {
        return sup_insta;
    }

    public void setSup_insta(String sup_insta) {
        this.sup_insta = sup_insta;
    }

    public String getSup_x() {
        return sup_x;
    }

    public void setSup_x(String sup_x) {
        this.sup_x = sup_x;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }
}
