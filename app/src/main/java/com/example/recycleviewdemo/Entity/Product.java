package com.example.recycleviewdemo.Entity;


import java.io.Serializable;

public class Product implements Serializable {

    private int resourceId;
    private String name;
    private String price;
    private String desciption;

    public Product(int resourceId, String name, String price, String desciption) {
        this.resourceId = resourceId;
        this.name = name;
        this.price = price;
        this.desciption = desciption;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

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

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}
