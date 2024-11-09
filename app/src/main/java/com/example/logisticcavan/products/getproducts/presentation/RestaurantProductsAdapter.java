package com.example.logisticcavan.products.getproducts.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.logisticcavan.R;
import com.example.logisticcavan.RestaurantDetailsFragmentArgs;
import com.example.logisticcavan.cart.presentaion.ui.AddOrderBottomSheet;
import com.example.logisticcavan.products.getproducts.domain.Product;

public class RestaurantProductsAdapter extends ListAdapter<Product, RestaurantProductsAdapter.ProductViewHolder> {
    private FragmentManager fragmentManager;
    private FoodItemClickListener foodItemClickListener;


    public RestaurantProductsAdapter(FragmentManager fragmentManager, FoodItemClickListener foodItemClickListener) {
        super(new RestaurantProductsDiffUtil());
        this.fragmentManager = fragmentManager;
        this.foodItemClickListener = foodItemClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = getItem(position);
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(product.getProductPrice()+" SR");
        Glide.with(holder.itemView)
                .load(product.getProductImageLink())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(18)))
                .into(holder.productImage);
//        holder.productGradients.setText(product.getProductGradients());
        holder.itemView.setOnClickListener(v->{
            foodItemClickListener.onFoodItemClick(product);
//
        });
    }
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName,productPrice,productGradients;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.food_image_details_id);
            productName = itemView.findViewById(R.id.food_name_details_id);
            productPrice = itemView.findViewById(R.id.food_price_details_id);
            productGradients = itemView.findViewById(R.id.food_gradients_details_id);
        }
    }
    static class RestaurantProductsDiffUtil extends DiffUtil.ItemCallback<Product>{

        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getProductName().equals(newItem.getProductName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem == newItem;
        }
    }
    public interface FoodItemClickListener{
        void onFoodItemClick(Product product);
    }
}
