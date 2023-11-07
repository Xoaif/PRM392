package com.example.recycleviewdemo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.example.recycleviewdemo.Fragment.HomeFragment;
import com.example.recycleviewdemo.Fragment.SettingFragment;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.Interface.IClickItemProductListener;
import com.example.recycleviewdemo.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvData;
    private ProductAdapter productAdapter;
    private BottomNavigationView bottomNavigationView;
    private ViewPager mViewPager;
     ActivityMainBinding binding;
    SharedPreferences.Editor editor;
    private DatabaseReference dr;
    private List<Product> mListProduct;

    private ProgressDialog progressDialog;
    SettingFragment settingFragment;
    HomeFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createFragment();

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//
//        rcvData = findViewById(R.id.rcv_data);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        rcvData.setLayoutManager(linearLayoutManager);
//
//        mListProduct = new ArrayList<Product>();
//        productAdapter = new ProductAdapter(mListProduct, new IClickItemProductListener() {
//            @Override
//            public void onClickItemProduct(Product product) {
//                onClickMoveToDetail(product);
//            }
//        });
//        eventChangeListener();
//        rcvData.setAdapter(productAdapter);
//
//        bottomNavigationView = findViewById(R.id.bottom_nav);
//        mViewPager = findViewById(R.id.view_pager);
//        setUpViewPager();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(homeFragment);
        binding.bottomNav.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.action_home){
                    replaceFragment(homeFragment);
            }else if(item.getItemId() == R.id.action_cart){
//                    replaceFragment(cartFragment);
            }else if(item.getItemId() == R.id.action_chat){
//                    replaceFragment(chatFragment);
            } else if (item.getItemId() == R.id.action_setting) {
                replaceFragment(settingFragment);
            }
            return true;
        });
    }
    private void createFragment(){
        settingFragment = new SettingFragment();
        homeFragment = new HomeFragment();
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.commit();
    }

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