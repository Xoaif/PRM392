package com.example.recycleviewdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.activity.DetailActivity;
import com.example.recycleviewdemo.entity.Product;
import com.example.recycleviewdemo.helper.JsonHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private final Context context;
    private List<Product> dataList;

    public ProductAdapter(Context context, List<Product> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(dataList.get(position).getImage()).into(holder.recImage);
        holder.recName.setText(dataList.get(position).getProductName());
        holder.recPrice.setText("￥" + dataList.get(position).getPrice());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product item = new Product(
                        dataList.get(holder.getAdapterPosition()).getId(),
                        dataList.get(holder.getAdapterPosition()).getProductName(),
                        dataList.get(holder.getAdapterPosition()).getPrice(),
                        dataList.get(holder.getAdapterPosition()).getDescription(),
                        dataList.get(holder.getAdapterPosition()).getImage(), 1);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("product", JsonHelper.parseObjectToJson(item));
                intent.putExtra("Image", dataList.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("Price", dataList.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("Description", dataList.get(holder.getAdapterPosition()).getDescription());
                intent.putExtra("Name", dataList.get(holder.getAdapterPosition()).getProductName());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<Product> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recName, recPrice;
    CardView recCard;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recPrice = itemView.findViewById(R.id.recPrice);
        recName = itemView.findViewById(R.id.recName);

    }
}

