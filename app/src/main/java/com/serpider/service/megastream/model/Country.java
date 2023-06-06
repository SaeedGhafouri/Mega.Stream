package com.serpider.service.megastream.model;

public class Country {

    private String country_unique, country_title, country_value, country_image;

    public Country(String country_unique, String country_title, String country_value, String country_image) {
        this.country_unique = country_unique;
        this.country_title = country_title;
        this.country_value = country_value;
        this.country_image = country_image;
    }

    public String getCountry_unique() {
        return country_unique;
    }

    public void setCountry_unique(String country_unique) {
        this.country_unique = country_unique;
    }

    public String getCountry_title() {
        return country_title;
    }

    public void setCountry_title(String country_title) {
        this.country_title = country_title;
    }

    public String getCountry_value() {
        return country_value;
    }

    public void setCountry_value(String country_value) {
        this.country_value = country_value;
    }

    public String getCountry_image() {
        return country_image;
    }

    public void setCountry_image(String country_image) {
        this.country_image = country_image;
    }
}
