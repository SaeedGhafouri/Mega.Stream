package com.serpider.service.megastream.model;

public class Serial_Play {

    String serial_play_unique, serial_title, serial_section_title, serial_section_quality, serial_section_language, serial_section_size, serial_section_url;

    public Serial_Play(String serial_play_unique, String serial_title, String serial_section_title, String serial_section_quality, String serial_section_language, String serial_section_size, String serial_section_url) {
        this.serial_play_unique = serial_play_unique;
        this.serial_title = serial_title;
        this.serial_section_title = serial_section_title;
        this.serial_section_quality = serial_section_quality;
        this.serial_section_language = serial_section_language;
        this.serial_section_size = serial_section_size;
        this.serial_section_url = serial_section_url;
    }

    public String getSerial_play_unique() {
        return serial_play_unique;
    }

    public void setSerial_play_unique(String serial_play_unique) {
        this.serial_play_unique = serial_play_unique;
    }

    public String getSerial_title() {
        return serial_title;
    }

    public void setSerial_title(String serial_title) {
        this.serial_title = serial_title;
    }

    public String getSerial_section_title() {
        return serial_section_title;
    }

    public void setSerial_section_title(String serial_section_title) {
        this.serial_section_title = serial_section_title;
    }

    public String getSerial_section_quality() {
        return serial_section_quality;
    }

    public void setSerial_section_quality(String serial_section_quality) {
        this.serial_section_quality = serial_section_quality;
    }

    public String getSerial_section_language() {
        return serial_section_language;
    }

    public void setSerial_section_language(String serial_section_language) {
        this.serial_section_language = serial_section_language;
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
