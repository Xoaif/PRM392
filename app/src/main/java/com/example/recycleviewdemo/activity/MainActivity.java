package com.example.recycleviewdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.adapter.ProductAdapter;

import com.example.recycleviewdemo.databinding.ActivityMainBinding;
import com.example.recycleviewdemo.entity.Product;
import com.example.recycleviewdemo.fragment.HomeFragment;
import com.example.recycleviewdemo.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<Product> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    ProductAdapter adapter;

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

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        SearchView searchView = findViewById(R.id.search);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new ProductAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("product");
        dialog.show();

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

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren()){
                    Product product = itemSnapshot.getValue(Product.class);
                    dataList.add(product);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UploadActivity.class);
                startActivity(intent);
            }
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
    public void searchList(String text){
        ArrayList<Product> searchList = new ArrayList<>();
        for(Product product: dataList){
            if(product.getProductName().toLowerCase().contains(text.toLowerCase())){
                searchList.add(product);
            }
        }
        adapter.searchDataList(searchList);
    }
}