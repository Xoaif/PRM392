package com.example.recycleviewdemo.fragment;

import com.example.recycleviewdemo.entity.Product;

@FunctionalInterface
public interface CartChange {
    void execute(Product product);
}
