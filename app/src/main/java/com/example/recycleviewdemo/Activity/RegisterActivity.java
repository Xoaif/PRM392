package com.example.recycleviewdemo.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextMail, editTextPassword, editTextConfirmPassword;
    ActivityRegisterBinding binding;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);

        editTextMail = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);
//        buttonRegister = findViewById(R.id.registerBtn);


//        loginNow = findViewById(R.id.login_now);
        binding.loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class );
                startActivity(loginIntent);
                finish();
            }
        });

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, password, confirmPass;
                username = String.valueOf(binding.username.getText());
                password = String.valueOf(binding.password.getText());
                confirmPass = String.valueOf((binding.confirmPassword .getText()));

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this, "All field must be mandatory", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(password.equals(confirmPass)){
                        boolean checkUsername = databaseHelper.checkUsername(username);
                        if(!checkUsername) {
                            boolean insert = databaseHelper.insertData(username,password);
                            if(insert){
                                Toast.makeText(RegisterActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                                Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class );
                                startActivity(loginIntent);
                            }else {
                                Toast.makeText(RegisterActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(RegisterActivity.this, "User already exist, please login", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(RegisterActivity.this, "Password not match, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}