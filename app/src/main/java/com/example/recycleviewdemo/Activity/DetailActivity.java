package com.example.recycleviewdemo.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recycleviewdemo.Entity.Product;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {
    private TextView tvName, tvPrice, tvDescription;

    private Button btncart;
    private ImageView imgProduct;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_detail_acticity);


        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Product product = (Product) bundle.get("object_product");
        imgProduct = findViewById(R.id.img_product);
        imgProduct.setImageResource(product.getResourceId());
        tvName = findViewById(R.id.tvname);
        tvName.setText(product.getName());
        tvPrice = findViewById(R.id.tvprice);
        tvPrice.setText(product.getPrice());
        tvDescription = findViewById(R.id.tvdescription);
        tvDescription.setText(product.getDesciption());
        btncart = findViewById(R.id.buttoncart);
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                addToCart();
                String name = tvName.getText().toString();
                String price = tvPrice.getText().toString();
                String des = tvDescription.getText().toString();
                Product products = new Product(name, price, des);
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Product Cart");
                reference.child(String.valueOf(name)).setValue(products).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        tvName.setText("");
                        tvPrice.setText("");
                        tvDescription.setText("");
                        Product product = (Product) bundle.get("object_product");
                        tvName = findViewById(R.id.tvname);
                        tvName.setText(product.getName());
                        tvPrice = findViewById(R.id.tvprice);
                        tvPrice.setText(product.getPrice());
                        tvDescription = findViewById(R.id.tvdescription);
                        tvDescription.setText(product.getDesciption());
                        Toast.makeText(DetailActivity.this, "Add to cart successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

}