package com.example.recycleviewdemo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recycleviewdemo.Entity.Product;
import com.example.recycleviewdemo.Interface.IClickItemProductListener;
import com.example.recycleviewdemo.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.UserViewHolder>{

    private List<Product> mListProduct;
    private IClickItemProductListener iClickItemProductListener;

    public ProductAdapter(List<Product> mListProduct, IClickItemProductListener listener) {
        this.mListProduct = mListProduct;
        this.iClickItemProductListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_product, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final Product product = mListProduct.get(position);
        if (product == null){
            return;
        }
        holder.imgProduct.setImageResource(product.getResourceId());
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemProductListener.onClickItemProduct(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListProduct != null){
            return mListProduct.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout layoutItem;
        private ImageView imgProduct;
        private TextView tvName;
        private TextView tvPrice;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
