package com.example.recycleviewdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.entity.Product;
import com.example.recycleviewdemo.fragment.CartChange;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListHolder>{
    List<Product> products;
    CartChange cartExecute;

    public CartListAdapter(List<Product> categoryDomains , CartChange cartExecute) {
        this.products = categoryDomains;
        this.cartExecute = cartExecute;
    }


    @NonNull
    @Override
    public CartListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);
        return new CartListHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListHolder holder, int position) {
        Product product = products.get(position);
        double fee =Double.parseDouble(product.getPrice()) ;

        holder.title.setText(product.getProductName());
        holder.feeEachItem.setText((int)fee + "K");
        holder.totalEachItem.setText(((int)fee * product.getNumberInCart())  + "K");
        holder.num.setText(product.getNumberInCart() + "");
        int drawableResourceId = holder.itemView.getContext()
                .getResources().getIdentifier(product.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);

        holder.plusBtn.setOnClickListener(v ->{
            product.setNumberInCart(product.getNumberInCart() + 1);
            cartExecute.execute(product);
            notifyDataSetChanged();
        });

        holder.minusBtn.setOnClickListener(v ->{
            product.setNumberInCart(product.getNumberInCart() - 1);
            if (product.getNumberInCart() == 0){
                products.remove(position);
            }
            cartExecute.execute(product);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class CartListHolder extends RecyclerView.ViewHolder{
        TextView title,feeEachItem;
        ImageView pic,minusBtn,plusBtn;
        TextView totalEachItem,num;
        public CartListHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.cartItemPic);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            minusBtn = itemView.findViewById(R.id.minusCartBtn);
            plusBtn = itemView.findViewById(R.id.plusCartBtn);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
        }
    }
}
