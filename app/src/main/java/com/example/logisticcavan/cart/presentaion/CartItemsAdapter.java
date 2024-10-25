package com.example.logisticcavan.cart.presentaion;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logisticcavan.R;
import com.example.logisticcavan.cart.domain.models.CartItem;

public class CartItemsAdapter extends ListAdapter<CartItem, CartItemsAdapter.CartItemViewHolder> {
    private OnDeleteButtonClicked onDeleteButtonClicked;

    public CartItemsAdapter(OnDeleteButtonClicked onDeleteButtonClicked) {
        super(new CartItemsDiffUtil());
        this.onDeleteButtonClicked = onDeleteButtonClicked;
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
        double cartItemTotalPrice = cartItem.getPrice();
        int cartItemQuantity = cartItem.getQuantity();
        double cartItemPrice = cartItemTotalPrice / cartItemQuantity;
        String cartItemImageLink = cartItem.getProductImageLink();

        Glide.with(holder.itemView)
                .load(cartItemImageLink)
                .into(holder.cartItemImage);
        holder.cartItemName.setText(cartItemName);
        holder.cartItemQuantity.setText(String.valueOf(cartItemQuantity));
        holder.cartItemPrice.setText(String.valueOf(cartItemTotalPrice));
        DetectQuantityUtil.increaseOrderItemQuantity("cart", cartItem, holder.increaseOrderQuantityBtn, holder.cartItemPrice, holder.cartItemQuantity, cartItemPrice);
        DetectQuantityUtil.decreaseOrderItemQuantity("cart", cartItem, holder.decreaseOrderQuantityBtn, holder.cartItemPrice, holder.cartItemQuantity, cartItemPrice);
        deleteCartItem(cartItem.getId(), holder);
    }

    private void deleteCartItem(int id, CartItemViewHolder holder) {
        holder.deleteCartItem.setOnClickListener(v -> {
            onDeleteButtonClicked.onDeleteButtonClicked(id);
        });
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        ImageView cartItemImage;
        TextView cartItemName, cartItemPrice, cartItemQuantity, deleteCartItem;
        ImageButton decreaseOrderQuantityBtn, increaseOrderQuantityBtn;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cart_item_image);
            cartItemName = itemView.findViewById(R.id.cart_item_name);
            cartItemPrice = itemView.findViewById(R.id.cart_item_price);
            cartItemQuantity = itemView.findViewById(R.id.cart_item_quantity);
            decreaseOrderQuantityBtn = itemView.findViewById(R.id.decrease_order_quantity_btn_cart_id);
            increaseOrderQuantityBtn = itemView.findViewById(R.id.increase_order_quantity_btn_cart_id);
            deleteCartItem = itemView.findViewById(R.id.delete_cart_item_btn);
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

    public interface OnDeleteButtonClicked {
        void onDeleteButtonClicked(int id);
    }
}
