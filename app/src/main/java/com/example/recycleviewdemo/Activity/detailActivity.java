package com.example.recycleviewdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.recycleviewdemo.Entity.Product;
import com.example.recycleviewdemo.R;

public class detailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_acticity);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        Product product = (Product) bundle.get("object_product");
        TextView tvName = findViewById(R.id.tv_name);
        tvName.setText(product.getName());
        TextView tvPrice = findViewById(R.id.tv_price);
        tvPrice.setText(product.getPrice());
        TextView tvDescription = findViewById(R.id.tv_description);
        tvDescription.setText(product.getDesciption());
    }
}