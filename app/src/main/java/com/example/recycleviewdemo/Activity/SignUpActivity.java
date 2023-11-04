package com.example.recycleviewdemo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recycleviewdemo.Entity.User;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //using firebase authentication with email and password
        firebaseAuth = FirebaseAuth.getInstance();




        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password, confirmPass;
                username = String.valueOf(binding.signUpEmail.getText());
                password = String.valueOf(binding.signUpPassword.getText());
                confirmPass = String.valueOf(binding.signUpConfirmPassword.getText());
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    Toast.makeText(SignUpActivity.this, "All field must be mandatory", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.equals(confirmPass)){
                        //register with firebase authentication using email
//                         firebaseAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()){
//                                    Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
//                                    Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class );
//                                    startActivity(loginIntent);
//                                    finish();
//                                }else{
//                                    Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                            }
//                        });

                        //register with remote firebase database
                        //using firebase real time database
                        firebaseDatabase = FirebaseDatabase.getInstance("https://prm-project-3e2cb-default-rtdb.asia-southeast1.firebasedatabase.app");
                        //get   in remote database with path Users (table)
                        databaseReference = firebaseDatabase.getReference("Users");

                        User user = new User(username, password);
                        databaseReference.child(username).setValue(user);

                        Toast.makeText(SignUpActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class );
                        startActivity(loginIntent);
                        finish();
                    }else{
                        Toast.makeText(SignUpActivity.this, "Password not match, please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.singUpLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class );
                startActivity(loginIntent);
                finish();
            }
        });
    }


}