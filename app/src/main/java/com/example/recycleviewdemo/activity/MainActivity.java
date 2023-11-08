package com.example.recycleviewdemo.activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import com.example.recycleviewdemo.databinding.ActivityMainBinding;
import com.example.recycleviewdemo.fragment.SettingFragment;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    SettingFragment settingFragment;
    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createFragment();

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
}