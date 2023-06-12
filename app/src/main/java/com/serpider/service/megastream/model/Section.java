package com.serpider.service.megastream.model;

public class Section {

    private String serial_season_id, serial_section_title, serial_section_value ,serial_section_quality, serial_section_size, serial_section_url;

    public Section(String serial_season_id, String serial_section_title, String serial_section_value, String serial_section_quality, String serial_section_size, String serial_section_url) {
        this.serial_season_id = serial_season_id;
        this.serial_section_title = serial_section_title;
        this.serial_section_value = serial_section_value;
        this.serial_section_quality = serial_section_quality;
        this.serial_section_size = serial_section_size;
        this.serial_section_url = serial_section_url;
    }

    public String getSerial_season_id() {
        return serial_season_id;
    }

    public void setSerial_season_id(String serial_season_id) {
        this.serial_season_id = serial_season_id;
    }

    public String getSerial_section_title() {
        return serial_section_title;
    }

    public void setSerial_section_title(String serial_section_title) {
        this.serial_section_title = serial_section_title;
    }

    public String getSerial_section_value() {
        return serial_section_value;
    }

    public void setSerial_section_value(String serial_section_value) {
        this.serial_section_value = serial_section_value;
    }

    public String getSerial_section_quality() {
        return serial_section_quality;
    }

    public void setSerial_section_quality(String serial_section_quality) {
        this.serial_section_quality = serial_section_quality;
    }

    public String getSerial_section_size() {
        return serial_section_size;
    }

    public void setSerial_section_size(String serial_section_size) {
        this.serial_section_size = serial_section_size;
    }

    public String getSerial_section_url() {
        return serial_section_url;
    }

    public void setSerial_section_url(String serial_section_url) {
        this.serial_section_url = serial_section_url;
    }
}
