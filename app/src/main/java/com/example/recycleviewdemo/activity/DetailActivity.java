package com.example.recycleviewdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.entity.Product;
import com.example.recycleviewdemo.fragment.CartFragment;
import com.example.recycleviewdemo.helper.JsonHelper;
import com.example.recycleviewdemo.service.CartService;

public class DetailActivity extends AppCompatActivity {

    TextView detailDescription;
    TextView detailName;
    TextView detailPrice;
    ImageView detailImage;

    private TextView addToCartBtn;
    private TextView feeTxt,numberOrderTxt,totalPriceTxt;
    private ImageView plusBtn,minusBtn,picFood;
    private Product product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            detailDescription.setText(bundle.getString("Description"));
            detailName.setText(bundle.getString("Name"));
            detailPrice.setText("$" + bundle.getString("Price"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }


        product = JsonHelper.parseJsonToObject(getIntent().getStringExtra("product"),Product.class);
        double price =Double.parseDouble(product.getPrice());
        feeTxt.setText((int)price +"K");
        numberOrderTxt.setText(product.getNumberInCart()+"");
        totalPriceTxt.setText((int)price * product.getNumberInCart() + "K");

        plusBtn.setOnClickListener(v -> {
            product.setNumberInCart(product.getNumberInCart()+1);
            numberOrderTxt.setText(product.getNumberInCart()+"");
            totalPriceTxt.setText(price * product.getNumberInCart() + "K");
        });

        minusBtn.setOnClickListener(v -> {
            if(product.getNumberInCart() > 1){
                product.setNumberInCart(product.getNumberInCart()-1);
                numberOrderTxt.setText(product.getNumberInCart()+"");
                totalPriceTxt.setText(price * product.getNumberInCart() + "K");
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(DetailActivity.this,
                    android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DetailActivity.this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
        addToCartBtn.setOnClickListener(v -> {
            CartService.AddToCart(product,DetailActivity.this);
            makeNotification();
        });
    }
    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        detailDescription = findViewById(R.id.detailDescription);
        detailImage = findViewById(R.id.detailImage);
        detailName = findViewById(R.id.detailName);
        detailPrice = findViewById(R.id.detailPrice);
        plusBtn = findViewById(R.id.plusCartBtn);
        minusBtn = findViewById(R.id.minusCartBtn);
        feeTxt = findViewById(R.id.priceTxt);
        numberOrderTxt= findViewById(R.id.numberItemTxt);
        totalPriceTxt = findViewById(R.id.totalPriceTxt);
    }

    public void makeNotification() {
        String channelID = "CHANNEL_ID_NOTIFICATION";
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), channelID);
        Intent intent = new Intent(getApplicationContext(), CartFragment.class);

        //Create pending intent to handle event when user click to the notification
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);

        // setting up notification using notification compat builder
        builder.setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle("Add into your cart successful")
                .setContentText("Click here to view your cart")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //init notification manager
        NotificationManager notificationManager =
                (NotificationManager) getSystemService((Context.NOTIFICATION_SERVICE));

        //Create notification channel using notification manager
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    notificationManager.getNotificationChannel(channelID);
            if (notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                //important: init notification channel
                notificationChannel = new NotificationChannel(channelID, "Some description, channel 1", importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);

                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        notificationManager.notify(0, builder.build());
    }
}