package com.example.recycleviewdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recycleviewdemo.R;

public class DetailActivity extends AppCompatActivity {

    TextView detailDescription;
    TextView detailName;
    TextView detailPrice;
    ImageView detailImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDescription = findViewById(R.id.detailDescription);
        detailImage = findViewById(R.id.detailImage);
        detailName = findViewById(R.id.detailName);
        detailPrice = findViewById(R.id.detailPrice);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            detailDescription.setText(bundle.getString("Description"));
            detailName.setText(bundle.getString("Name"));
            detailPrice.setText("￥" + bundle.getString("Price"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
    }
}