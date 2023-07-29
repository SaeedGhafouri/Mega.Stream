package com.serpider.service.megastream.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Favorites implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "unique_id")
    private String unique_item;

    @ColumnInfo(name = "item_title")
    private String title_item;

    @ColumnInfo(name = "item_year")
    private String year_item;

    @ColumnInfo(name = "item_country")
    private String country_item;

    @ColumnInfo(name = "item_poster")
    private String poster_item;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnique_item() {
        return unique_item;
    }

    public void setUnique_item(String unique_item) {
        this.unique_item = unique_item;
    }

    public String getTitle_item() {
        return title_item;
    }

    public void setTitle_item(String title_item) {
        this.title_item = title_item;
    }

    public String getYear_item() {
        return year_item;
    }

    public void setYear_item(String year_item) {
        this.year_item = year_item;
    }

    public String getCountry_item() {
        return country_item;
    }

    public void setCountry_item(String country_item) {
        this.country_item = country_item;
    }

    public String getPoster_item() {
        return poster_item;
    }

    public void setPoster_item(String poster_item) {
        this.poster_item = poster_item;
    }
}