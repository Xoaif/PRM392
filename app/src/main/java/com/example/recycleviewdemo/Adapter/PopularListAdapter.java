package com.example.recycleviewdemo.Adapter;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewdemo.Entity.Popular;

import java.util.ArrayList;

public class PopularListAdapter extends RecyclerView.Adapter<PopularListAdapter.Viewholder> {
    @NonNull
    ArrayList<Popular> items;
    Context context;

    public PopularListAdapter(Context context) {
        this.context = context;
    }

    public PopularListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularListAdapter.Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView titletxt,timetxt,scoretxt;
        ImageView pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
