package com.serpider.service.megastream.model;

public class Subscription {

    private int id, status, type, discount_percent;
    private long price, discount;
    private String name, period;

    public Subscription(int type, int discount_percent, long price, long discount, String name, String period) {
        this.type = type;
        this.discount_percent = discount_percent;
        this.price = price;
        this.discount = discount;
        this.name = name;
        this.period = period;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getType() {
        return type;
    }

    public int getDiscount_percent() {
        return discount_percent;
    }

    public long getPrice() {
        return price;
    }

    public long getDiscount() {
        return discount;
    }

    public String getName() {
        return name;
    }

    public String getPeriod() {
        return period;
    }
}
