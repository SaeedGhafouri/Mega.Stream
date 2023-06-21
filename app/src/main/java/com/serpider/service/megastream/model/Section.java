package com.serpider.service.megastream.model;

public class Section {

    private String section_unique, section_id_season, section_title, section_value;

    public Section(String section_unique, String section_id_season, String section_title, String section_value) {
        this.section_unique = section_unique;
        this.section_id_season = section_id_season;
        this.section_title = section_title;
        this.section_value = section_value;
    }

    public String getSection_unique() {
        return section_unique;
    }

    public void setSection_unique(String section_unique) {
        this.section_unique = section_unique;
    }

    public String getSection_id_season() {
        return section_id_season;
    }

    public void setSection_id_season(String section_id_season) {
        this.section_id_season = section_id_season;
    }

    public String getSection_title() {
        return section_title;
    }

    public void setSection_title(String section_title) {
        this.section_title = section_title;
    }

    public String getSection_value() {
        return section_value;
    }

    public void setSection_value(String section_value) {
        this.section_value = section_value;
    }
}
