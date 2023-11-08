package com.example.recycleviewdemo.service;

import android.content.Context;
import android.widget.Toast;

import com.example.recycleviewdemo.entity.Cart;
import com.example.recycleviewdemo.entity.Data;
import com.example.recycleviewdemo.entity.Product;
import com.example.recycleviewdemo.helper.JsonHelper;
import java.util.List;

public class CartService {
    public static void AddToCart(Product product, Context context) {
        Cart cart = Data.myCart;
        List<Product> products ;
        products = JsonHelper.parseJsonToList(cart.getListProduct(),Product.class);
        boolean flag = false;
        for(Product f : products) {
            if(f.getId().equals(product.getId())) {
                f.setNumberInCart(f.getNumberInCart() + product.getNumberInCart());
                flag = true;
                break;
            }
        }
        if(!flag) {
            products.add(product);
        }
        String listFoodJson = JsonHelper.parseListToJson(products);
        cart.setListFood(listFoodJson);
        Toast.makeText(context, "Product has been add to your cart", Toast.LENGTH_SHORT).show();
    }

}
