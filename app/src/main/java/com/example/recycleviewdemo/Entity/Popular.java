package com.example.recycleviewdemo.Entity;

import java.io.Serializable;

public class Popular implements Serializable {
    private String name;
    private String price;

    private String picurl;
    private double score;
    private int numberinCart;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getNumberinCart() {
        return numberinCart;
    }

    public void setNumberinCart(int numberinCart) {
        this.numberinCart = numberinCart;
    }

    public Popular(String name, String price, String picurl, double score, int numberinCart) {
        this.name = name;
        this.price = price;
        this.picurl = picurl;
        this.score = score;
        this.numberinCart = numberinCart;
    }
}
