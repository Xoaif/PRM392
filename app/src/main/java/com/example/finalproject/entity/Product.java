package com.example.finalproject.entity;

public class Product {

    private String productName;
    private String price;
    private String description;
    private String image;

    public Product(String productName, String price, String description, String image) {
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.image = image;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
