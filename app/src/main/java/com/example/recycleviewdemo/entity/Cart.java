package com.example.recycleviewdemo.entity;


import com.example.recycleviewdemo.helper.JsonHelper;

import java.util.List;


public class Cart {
    private String id;

    private String userId;

    private String listProduct;


    public Cart(String id, String userId, String listProduct) {
        this.id = id;
        this.userId = userId;
        this.listProduct = listProduct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getListProduct() {
        return listProduct;
    }

    public void setListProduct(String listProduct) {
        this.listProduct = listProduct;
    }

    public Cart() {
    }


    public void setListFood(String listFood) {
        this.listProduct = listFood;
    }

    public double totalFee(){
        List<Product> list = JsonHelper.parseJsonToList(listProduct, Product.class);
        double total = 0.0;
        double price;
        for (int i = 0; i < list.size(); i++){
            price = Double.parseDouble(list.get(i).getPrice());
            total += list.get(i).getNumberInCart() * price;
        }
        return total;
    }
    public int cartSize(){
        List<Product> list = JsonHelper.parseJsonToList(listProduct, Product.class);
        return list.size();
    }
}
