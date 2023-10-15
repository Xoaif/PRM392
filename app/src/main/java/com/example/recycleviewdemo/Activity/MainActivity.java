package com.example.recycleviewdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.recycleviewdemo.Entity.Product;
import com.example.recycleviewdemo.Adapter.ProductAdapter;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.Interface.IClickItemProductListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvData;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvData = findViewById(R.id.rcv_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvData.setLayoutManager(linearLayoutManager);

        productAdapter = new ProductAdapter(getListProduct(), new IClickItemProductListener() {
            @Override
            public void onClickItemProduct(Product product) {
                onClickMoveToDetail(product);
            }
        });
        rcvData.setAdapter(productAdapter);
    }

    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(R.drawable.nb2, "Osaka", "20", "青空をバックに凛としてそびえ立つ大阪城天守閣。大坂城は天下統一をめざす豊臣秀吉によって天正11年（1583）、大坂（石山）本願寺跡で築造が開始された。城の本丸のなかで最も中心の建物である天守閣はその2年後に完成したが、元和（げんな）元年（1615）の大坂夏の陣で豊臣氏の滅亡とともに天守閣も焼失。徳川時代になって再建されたものの寛文5年（1665）、落雷によって再び焼失。以来、大坂城は天守閣のないままだった。現在の天守閣は昭和6年（1931）、当時の関市長の呼びかけで市民らの寄付により建造されたもの。266年ぶりに甦った天守閣は、地上55m、5層8階。屋根の鯱、勾欄下の伏虎など、いたるところに施された黄金の装飾が燦然と輝いている（国の登録有形文化財）。平成7～9年（1995-97）にかけて大改修が行われた。8階の展望台からは大阪を一望できる。"));
        list.add(new Product(R.drawable.nb1, "Nagoya", "30", "rat vui"));
        list.add(new Product(R.drawable.nb3, "Nagoya", "30", "rat vui"));
        list.add(new Product(R.drawable.nb1, "Nagoya", "30", "rat vui"));
        list.add(new Product(R.drawable.nb2, "Nagoya", "30", "rat vui"));

        return list;
    }

    private void onClickMoveToDetail(Product product){
        Intent intent = new Intent(this, DetailActicity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}