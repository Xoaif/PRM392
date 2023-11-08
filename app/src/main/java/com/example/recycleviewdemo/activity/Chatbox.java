package com.example.recycleviewdemo.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Chatbox extends AppCompatActivity {

    ListView lvDiscussionTopics;
    ArrayList<String> listOfDiscussion = new ArrayList<String>();
    ArrayAdapter arrayAdpt;

    private DatabaseReference dbr = FirebaseDatabase.getInstance().getReference().getRoot();
    private DatabaseReference dbrChat = FirebaseDatabase.getInstance().getReference("Discuss");

    String UserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox);


        UserName = getIntent().getExtras().get("username").toString();

        if(UserName.equals("lam")){
            lvDiscussionTopics = (ListView) findViewById(R.id.lvDiscussions);
            arrayAdpt = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_1, listOfDiscussion);
            lvDiscussionTopics.setAdapter(arrayAdpt);



            dbrChat.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Set<String> set = new HashSet<String>();
                    Iterator i = dataSnapshot.getChildren().iterator();

                    while(i.hasNext()){
                        set.add(((DataSnapshot)i.next()).getKey());
                    }

                    arrayAdpt.clear();
                    arrayAdpt.addAll(set);
                    arrayAdpt.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            lvDiscussionTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i = new Intent(getApplicationContext(), DiscussionActivity.class);
                    i.putExtra("selected_topic", ((TextView)view).getText().toString());
                    i.putExtra("username", UserName);
                    startActivity(i);
                }
            });
        }else{
            Intent i = new Intent(getApplicationContext(), DiscussionActivity.class);
            i.putExtra("username", UserName);
            startActivity(i);
        }

    }

}