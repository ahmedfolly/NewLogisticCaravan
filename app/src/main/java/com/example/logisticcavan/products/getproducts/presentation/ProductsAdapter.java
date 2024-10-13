package com.example.logisticcavan.products.getproducts.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logisticcavan.ProductWithRestaurant;
import com.example.logisticcavan.R;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.restaurants.domain.Restaurant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsVH> {
    List<ProductWithRestaurant> productsWithRestaurants;
    Map<String, Restaurant> resturantsMap = new HashMap<>();

    public ProductsAdapter(List<ProductWithRestaurant> productsWithRestaurant) {
        this.productsWithRestaurants = productsWithRestaurant;
    }

    @NonNull
    @Override
    public ProductsAdapter.ProductsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new ProductsVH(productsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ProductsVH holder, int position) {
        Product product = productsWithRestaurants.get(position).getProduct();
        Restaurant restaurant = productsWithRestaurants.get(position).getRestaurant();

        Glide.with(holder.itemView)
                .load(product.getProductImageLink())
                .override(800, 800)
                .into(holder.productImage);
        holder.foodName.setText(product.getProductName());
        holder.productPrice.setText("" + (int) product.getProductPrice() + " US");
        holder.restaurantName.setText(restaurant.getRestaurantName());
    }

    @Override
    public int getItemCount() {
        return productsWithRestaurants.size();
    }

    public class ProductsVH extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView foodName;
        TextView productPrice, restaurantName;

        public ProductsVH(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.food_image_id);
            foodName = itemView.findViewById(R.id.product_name_id);
            productPrice = itemView.findViewById(R.id.product_price_id);
            restaurantName = itemView.findViewById(R.id.restaurant_name_id);
        }
    }
}
