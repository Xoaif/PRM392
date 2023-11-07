package com.example.recycleviewdemo.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.recycleviewdemo.Entity.Product;
import com.example.recycleviewdemo.Adapter.ProductAdapter;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.Interface.IClickItemProductListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvData;
    private ProductAdapter productAdapter;
    private DatabaseReference dr;
    private List<Product> mListProduct;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();

        rcvData = findViewById(R.id.rcv_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvData.setLayoutManager(linearLayoutManager);

        mListProduct = new ArrayList<Product>();
        productAdapter = new ProductAdapter(mListProduct, new IClickItemProductListener() {
            @Override
            public void onClickItemProduct(Product product) {
                onClickMoveToDetail(product);
            }
        });
        rcvData.setAdapter(productAdapter);
        eventChangeListener();
    }

    private void eventChangeListener(){
        dr = FirebaseDatabase
                .getInstance("https://prm-project-3e2cb-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Product");
        dr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListProduct = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Product product = snapshot.getValue(Product.class);
                    mListProduct.add(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

    }

    private void onClickMoveToDetail(Product product){
        Intent intent = new Intent(this, DetailActicity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}