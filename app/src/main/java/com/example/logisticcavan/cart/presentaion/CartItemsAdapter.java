package com.example.logisticcavan.cart.presentaion;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logisticcavan.R;
import com.example.logisticcavan.cart.domain.models.CartItem;

import java.util.List;

public class CartItemsAdapter extends ListAdapter<CartItem, CartItemsAdapter.CartItemViewHolder> {
    public CartItemsAdapter() {
        super(new CartItemsDiffUtil());
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = getItem(position);
        String cartItemName = cartItem.getProductName();
        double cartItemPrice = cartItem.getPrice();
        int cartItemQuantity = cartItem.getQuantity();
        String cartItemImageLink = cartItem.getProductImageLink();

        Glide.with(holder.itemView)
                .load(cartItemImageLink)
                .into(holder.cartItemImage);
        holder.cartItemName.setText(cartItemName);
        holder.cartItemQuantity.setText(String.valueOf(cartItemQuantity));
        holder.cartItemPrice.setText(String.valueOf(cartItemPrice));
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        ImageView cartItemImage;
        TextView cartItemName, cartItemPrice, cartItemQuantity;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cart_item_image);
            cartItemName = itemView.findViewById(R.id.cart_item_name);
            cartItemPrice = itemView.findViewById(R.id.cart_item_price);
            cartItemQuantity = itemView.findViewById(R.id.cart_item_quantity);
        }
    }

    private static class CartItemsDiffUtil extends DiffUtil.ItemCallback<CartItem> {

        @Override
        public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem == newItem;
        }
    }
}
