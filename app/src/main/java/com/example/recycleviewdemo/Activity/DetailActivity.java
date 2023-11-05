package com.example.recycleviewdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recycleviewdemo.Entity.Product;
import com.example.recycleviewdemo.R;

public class DetailActivity extends AppCompatActivity {

    private ImageView detailImage;
    private TextView detailName;
    private TextView detailPrice;
    private TextView detailDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_acticity);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        Product product = (Product) bundle.get("object_product");

        detailImage = findViewById(R.id.img_product);

        detailName = findViewById(R.id.tv_name);
        detailName.setText(product.getName());

        detailPrice = findViewById(R.id.tv_price);
        detailPrice.setText(product.getPrice());

        detailDescription = findViewById(R.id.tv_description);
        detailDescription.setText(product.getDesciption());
    }
}