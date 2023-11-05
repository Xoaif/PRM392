package com.example.finalproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;

public class DetailActivity extends AppCompatActivity {

    TextView detailDescription;
    TextView detailName;
    ImageView detailImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDescription = findViewById(R.id.detailDescription);
        detailImage = findViewById(R.id.detailImage);
        detailName = findViewById(R.id.detailName);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            detailDescription.setText(bundle.getString("Description"));
            detailName.setText(bundle.getString("Name"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
    }
}