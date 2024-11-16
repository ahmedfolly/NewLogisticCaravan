package com.example.logisticcavan.sharedcart.presentation;

import android.annotation.SuppressLint;
import android.util.Log;
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
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.model.SharedCartItem;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;

import java.util.Objects;

public class SharedCartItemsAdapter extends ListAdapter<SharedCartItem,SharedCartItemsAdapter.ViewHolder> {
    private final AuthViewModel authViewModel;
    private final GetSharedCartViewModel getSharedCartViewModel;
    private final DeleteBtnListener deleteBtnListener;
    public SharedCartItemsAdapter(AuthViewModel authViewModel,
                                  GetSharedCartViewModel getSharedCartViewModel,
                                  DeleteBtnListener deleteBtnListener) {
        super(new SharedCartItemsDiffUtil());
        this.authViewModel = authViewModel;
        this.getSharedCartViewModel = getSharedCartViewModel;
        this.deleteBtnListener = deleteBtnListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_cart_item,parent,false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SharedCartItem product = getItem(position);
        // Reset views to avoid leftover data from the previous item
        holder.sharedProductName.setText("");
        holder.sharedProductOwner.setText("");
        holder.sharedProductQuantity.setText("");


        if (product.getProduct() != null) {
            holder.sharedProductName.setText(product.getProduct().getProductName());
            Glide.with(holder.itemView.getContext())
                    .load(product.getProduct().getProductImageLink())
                    .into(holder.sharedProductImage);
        }

        // Bind shared product details
        if (product.getSharedProduct() != null) {
            String ownerText = "Added by: " + product.getSharedProduct().getAddedBy();
            if (Objects.equals(getUserName(),product.getSharedProduct().getAddedBy())) {
                ownerText = "Added by: You";
            }
            holder.sharedProductOwner.setText(ownerText);
            holder.sharedProductQuantity.setText(String.valueOf(product.getSharedProduct().getQuantity()));
            deleteProduct(holder,product);
        }
        showEditOrderBox(holder.itemView);
        increaseOrderItemQuantity(holder.increaseOrderQuantityBtn, holder.sharedProductQuantity, product.getSharedProduct());
        decreaseOrderItemQuantity(holder.decreaseOrderQuantityBtn, holder.sharedProductQuantity, product.getSharedProduct());

//        DetectQuantityUtil.increaseOrderItemQuantity("", null, holder.increaseOrderQuantityBtn,null, holder.sharedProductQuantity, 0.0);
//        DetectQuantityUtil.decreaseOrderItemQuantity("", null, holder.decreaseOrderQuantityBtn, null, holder.sharedProductQuantity, 0.0);
    }
    private void deleteProduct(ViewHolder holder,SharedCartItem product){
        holder.itemView.setOnClickListener(v->{
            String sharedCartProductId = product.getSharedProduct().getSharedProductId();
            deleteBtnListener.onDeleteBtnClicked(sharedCartProductId);
        });
    }
    public void increaseOrderItemQuantity(ImageButton increaseOrderQuantityBtn,
                                          TextView orderQuantity,
                                          SharedProduct sharedProduct) {
        increaseOrderQuantityBtn.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(orderQuantity.getText().toString());
            currentQuantity += 1;
            orderQuantity.setText(String.valueOf(currentQuantity));
            sharedProduct.setQuantity(currentQuantity);
        });

    }

    public static void decreaseOrderItemQuantity(ImageButton decreaseOrderQuantityBtn, TextView orderQuantity,
                                                 SharedProduct sharedProduct) {
        decreaseOrderQuantityBtn.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(orderQuantity.getText().toString());
            if (currentQuantity > 1) {
                currentQuantity -= 1;
                sharedProduct.setQuantity(currentQuantity);
                orderQuantity.setText(String.valueOf(currentQuantity));
            }
        });
    }
    private void showEditOrderBox(View view){
        View editOrderBox = view.findViewById(R.id.edit_shared_order_box);
        getSharedCartViewModel.getSharedCart(new GetSharedCartViewModel.SharedCartCallback() {
            @Override
            public void onSuccess(SharedCart sharedCart) {
                String adminEmail = sharedCart.getAdminId();
                if (adminEmail.equals(getUserEmail())){
                    editOrderBox.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
    private String getUserName(){
        return authViewModel.getUserInfoLocally().getName();
    }
    private String getUserEmail(){
        return authViewModel.getUserInfoLocally().getEmail();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView sharedProductImage;
        TextView sharedProductName;
        TextView sharedProductOwner,sharedProductQuantity;
        ImageButton decreaseOrderQuantityBtn, increaseOrderQuantityBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sharedProductImage = itemView.findViewById(R.id.cart_item_image);
            sharedProductName = itemView.findViewById(R.id.sharted_cart_item_name);
            sharedProductOwner = itemView.findViewById(R.id.shared_cart_item_owner);
            decreaseOrderQuantityBtn = itemView.findViewById(R.id.decrease_shared_item_quantity_btn_cart_id);
            increaseOrderQuantityBtn = itemView.findViewById(R.id.increase_shared_item_quantity_btn_cart_id);
            sharedProductQuantity = itemView.findViewById(R.id.shared_cart_item_quantity);
        }
    }
    static class SharedCartItemsDiffUtil extends DiffUtil.ItemCallback<SharedCartItem>{

        @Override
        public boolean areItemsTheSame(@NonNull SharedCartItem oldItem, @NonNull SharedCartItem newItem) {
            return oldItem.getProduct().getProductName().equals(newItem.getProduct().getProductName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SharedCartItem oldItem, @NonNull SharedCartItem newItem) {
            // Check for content changes (e.g., quantity, productName, etc.)
            if (!oldItem.getProduct().getProductID().equals(newItem.getProduct().getProductID())) {
                return false; // If product names are different, contents have changed
            }
            return oldItem.getSharedProduct().getQuantity() == newItem.getSharedProduct().getQuantity(); // If quantity is different, contents have changed
            // Add any other fields you want to track here, e.g., price, owner, etc.
//
        }
    }
    public interface DeleteBtnListener{
        void onDeleteBtnClicked(String productId);
    }
}
