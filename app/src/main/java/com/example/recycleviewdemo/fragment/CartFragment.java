package com.example.recycleviewdemo.fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.activity.CheckoutActivity;
import com.example.recycleviewdemo.adapter.CartListAdapter;
import com.example.recycleviewdemo.entity.Cart;
import com.example.recycleviewdemo.entity.Data;
import com.example.recycleviewdemo.entity.Product;
import com.example.recycleviewdemo.helper.JsonHelper;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CartFragment extends Fragment implements CartChange {
    private RecyclerView recyclerViewList;
    private TextView totalFeeTxt,taxTxt,deliveryFeeTxt,totalTxt,emptyTxt;
    private double tax,delivery,total,itemTotal;
    private ConstraintLayout bill;
    private Cart myCart;


    @Override
    public void execute(Product product) {

    }

    public CartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initList(view.getContext());
    }

    private void initList(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        //get cart from system
        Cart cart = Data.myCart;
        if (cart == null || cart.cartSize() == 0) {
            RecyclerView.Adapter adapter = new CartListAdapter(Collections.emptyList(), CartFragment.this);
            recyclerViewList.setAdapter(adapter);
            emptyTxt.setVisibility(LinearLayout.VISIBLE);
            bill.setVisibility(LinearLayout.GONE);
        }else {
            myCart =cart;
            List<Product> listFood = JsonHelper.parseJsonToList(cart.getListProduct(), Product.class);
            RecyclerView.Adapter adapter = new CartListAdapter(listFood, CartFragment.this);
            calculateCart(cart);
            recyclerViewList.setAdapter(adapter);
            emptyTxt.setVisibility(LinearLayout.GONE);
            bill.setVisibility(LinearLayout.VISIBLE);
        }
    }

    private void calculateCart(Cart cart) {
        if(cart == null) return;
        int ship = 10;
        int totalFee = (int) cart.totalFee();

        totalFeeTxt.setText(totalFee +"K");
        deliveryFeeTxt.setText(ship + "K");
        totalTxt.setText((ship + totalFee) +"K");
    }



    private void initView(View view) {
        totalFeeTxt = view.findViewById(R.id.totalFeeTxt);
        deliveryFeeTxt = view.findViewById(R.id.deliveryFeeTxt);
        totalTxt = view.findViewById(R.id.totalTxt);
        recyclerViewList = view.findViewById(R.id.view);
        bill = view.findViewById(R.id.Bill);
        emptyTxt = view.findViewById(R.id.emptyTxt);
        view.findViewById(R.id.btn_checkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                intent.putExtra("total price", myCart.totalFee());
                startActivity(intent);
            }
        });
    }



}
