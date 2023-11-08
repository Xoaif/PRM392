package com.example.recycleviewdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    TextView signUp;
    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        firebaseAuth = FirebaseAuth.getInstance();


        signUp = findViewById(R.id.sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpIntent);
                finish();
            }
        });

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, password;
                username = String.valueOf(binding.username.getText());
                password = String.valueOf(binding.password.getText());

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                Query checkUserDatabase = databaseReference.orderByChild("username").equalTo(username);

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "All field are mandatory", Toast.LENGTH_SHORT).show();
                }else{
                    //login with firebase authentication using email
//                    firebaseAuth.signInWithEmailAndPassword(username, password)
//                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                                        @Override
//                                        public void onSuccess(AuthResult authResult) {
//                                            Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
//                                            Intent successIntent = new Intent(getApplicationContext(), MainActivity.class);
//                                            startActivity(successIntent);
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
                    //login with remote firebase database
                    checkUser();

                }
            }
        });
    }
    private void checkUser(){
        String username, password;
        username = String.valueOf(binding.username.getText());
        password = String.valueOf(binding.password.getText());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://prm-project-3e2cb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Users");
        Query checkUserDatabase = databaseReference.orderByChild("username").equalTo(username);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    binding.username.setError(null);
                    String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);
                    if(Objects.equals(passwordFromDB, password)){
                        binding.username.setError(null);
                        Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else {
                        binding.password.setError("invalid credential");
                        binding.password.requestFocus();
                    }
                }else{
                    binding.username.setError("User do not exist");
                    binding.username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}