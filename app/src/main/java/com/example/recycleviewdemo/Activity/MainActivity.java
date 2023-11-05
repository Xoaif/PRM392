package com.example.recycleviewdemo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.recycleviewdemo.Adapter.ViewPagerAdapter;
import com.example.recycleviewdemo.Entity.Product;
import com.example.recycleviewdemo.Adapter.ProductAdapter;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.Interface.IClickItemProductListener;
import com.example.recycleviewdemo.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvData;
    private ProductAdapter productAdapter;
    private BottomNavigationView bottomNavigationView;
    private ViewPager mViewPager;
    private ActivityMainBinding binding;
    SharedPreferences.Editor editor;
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
        eventChangeListener();
        rcvData.setAdapter(productAdapter);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        mViewPager = findViewById(R.id.view_pager);
//        setUpViewPager();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_home){
                    Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.action_cart){
                    Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.action_my_page){
                    Toast.makeText(MainActivity.this, "My Page", Toast.LENGTH_SHORT).show();
                } else if (item.getItemId() == R.id.action_logout) {
                    editor.clear();
                    editor.apply();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
                return true;
            }
        });

    }
//    private void setUpViewPager(){
//        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager().getPrimaryNavigationFragment());
//        mViewPager.setAdapter(viewPagerAdapter);
//    }

    private void eventChangeListener(){
        dr = FirebaseDatabase.getInstance().getReference().child("Price");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListProduct.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Product product = snapshot.getValue(Product.class);
                    if (product != null) {
                        mListProduct.add(product);
                    }
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onClickMoveToDetail(Product product){
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}