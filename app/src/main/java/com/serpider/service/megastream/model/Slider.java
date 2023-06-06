package com.serpider.service.megastream.model;

public class Slider {

    private String slider_type, slider_title, slider_desc, slider_btn, slider_btn_color, slider_link, slider_image;

    public Slider(String slider_type, String slider_title, String slider_desc, String slider_btn, String slider_btn_color, String slider_link, String slider_image) {
        this.slider_type = slider_type;
        this.slider_title = slider_title;
        this.slider_desc = slider_desc;
        this.slider_btn = slider_btn;
        this.slider_btn_color = slider_btn_color;
        this.slider_link = slider_link;
        this.slider_image = slider_image;
    }

    public String getSlider_type() {
        return slider_type;
    }

    public void setSlider_type(String slider_type) {
        this.slider_type = slider_type;
    }

    public String getSlider_title() {
        return slider_title;
    }

    public void setSlider_title(String slider_title) {
        this.slider_title = slider_title;
    }

    public String getSlider_desc() {
        return slider_desc;
    }

    public void setSlider_desc(String slider_desc) {
        this.slider_desc = slider_desc;
    }

    public String getSlider_btn() {
        return slider_btn;
    }

    public void setSlider_btn(String slider_btn) {
        this.slider_btn = slider_btn;
    }

    public String getSlider_btn_color() {
        return slider_btn_color;
    }

    public void setSlider_btn_color(String slider_btn_color) {
        this.slider_btn_color = slider_btn_color;
    }

    public String getSlider_link() {
        return slider_link;
    }

    public void setSlider_link(String slider_link) {
        this.slider_link = slider_link;
    }

    public String getSlider_image() {
        return slider_image;
    }

    public void setSlider_image(String slider_image) {
        this.slider_image = slider_image;
    }
}
