package com.example.recycleviewdemo.entity;


public class Product {
    private String id;

    private String productName;
    private String price;
    private String description;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    private int numberInCart;

    public Product() {
    }

    public Product(String id, String productName, String price, String description, String image, int numberInCart) {
        this.productName = productName;
        this.id = id;
        this.price = price;
        this.description = description;
        this.image = image;
        this.numberInCart = numberInCart;
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