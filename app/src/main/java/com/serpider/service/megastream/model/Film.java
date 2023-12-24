package com.serpider.service.megastream.model;

import java.util.List;

public class Film {

    /*private int id ,type , period, year, subtitle, dooble, like, dislike, comment_count;
    private float imdb;
    private String title_en, title_fa, genre, group, country, language, ages, synopsis, desc, network, volume, stars, poster, header, trailer, date;
*/
    private int id ,type , period, year, subtitle, dooble, like, dislike, comment_count, size, suggestion;
    private float imdb;
    private String title_en, title_fa, genre, group, country, country_flag, language, language_alpha, network, network_vector, ages, synopsis, desc, volume, stars, poster, header, trailer, date;

    private List<Comment> comments;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Comment> getListComment() {
        return comments;
    }

    public void setListComment(List<Comment> comments) {
        this.comments = comments;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(int subtitle) {
        this.subtitle = subtitle;
    }

    public int getDooble() {
        return dooble;
    }

    public void setDooble(int dooble) {
        this.dooble = dooble;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getImdb() {
        return imdb;
    }

    public String getTitle_en() {
        return title_en;
    }


    public String getTitle_fa() {
        return title_fa;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGroup() {
        return group;
    }

    public String getCountry() {
        return country;
    }

    public String getCountry_flag() {
        return country_flag;
    }


    public String getLanguage() {
        return language;
    }

    public String getLanguage_alpha() {
        return language_alpha;
    }

    public String getNetwork() {
        return network;
    }

    public String getNetwork_vector() {
        return network_vector;
    }

    public String getAges() {
        return ages;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getVolume() {
        return volume;
    }

    public String getStars() {
        return stars;
    }

    public String getPoster() {
        return poster;
    }

    public String getHeader() {
        return header;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getDate() {
        return date;
    }

    public int getSuggestion() {
        return suggestion;
    }

    public List<Comment> getComments() {
        return comments;
    }
}