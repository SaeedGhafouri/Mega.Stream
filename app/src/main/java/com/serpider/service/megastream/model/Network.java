package com.serpider.service.megastream.model;

public class Network {

    private String network_unique, network_name, network_desc, network_image;

    public Network(String network_unique, String network_name, String network_desc, String network_image) {
        this.network_unique = network_unique;
        this.network_name = network_name;
        this.network_desc = network_desc;
        this.network_image = network_image;
    }

    public String getNetwork_unique() {
        return network_unique;
    }

    public void setNetwork_unique(String network_unique) {
        this.network_unique = network_unique;
    }

    public String getNetwork_name() {
        return network_name;
    }

    public void setNetwork_name(String network_name) {
        this.network_name = network_name;
    }

    public String getNetwork_desc() {
        return network_desc;
    }

    public void setNetwork_desc(String network_desc) {
        this.network_desc = network_desc;
    }

    public String getNetwork_image() {
        return network_image;
    }

    public void setNetwork_image(String network_image) {
        this.network_image = network_image;
    }
}
