package com.serpider.service.megastream.model;

public class Country {

    private int id;
    private String fa_name, en_name, vector;

    public int getId() {
        return id;
    }

    public String getFa_name() {
        return fa_name;
    }

    public void setFa_name(String fa_name) {
        this.fa_name = fa_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getVector() {
        return vector;
    }

    public void setVector(String vector) {
        this.vector = vector;
    }
}
