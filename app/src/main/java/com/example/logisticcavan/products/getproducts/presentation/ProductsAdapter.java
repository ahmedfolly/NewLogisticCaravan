package com.example.logisticcavan.products.getproducts.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.logisticcavan.R;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.restaurants.domain.Restaurant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsVH> {
    List<Product> products;
    Map<String,Restaurant> resturantsMap = new HashMap<>();

    public ProductsAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductsAdapter.ProductsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productsView= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false);
        return new ProductsVH(productsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ProductsVH holder, int position) {
        Product product = products.get(position);
        Glide.with(holder.itemView)
                .load(product.getProductImageLink())
                .override(800,800)
                .into(holder.productImage);
        holder.foodName.setText(product.getProductName());
        holder.productPrice.setText(""+(int)product.getProductPrice()+" US");
        Restaurant restaurant = resturantsMap.get(product.getResId());
        if (restaurant != null) {
            holder.restaurantName.setText(restaurant.getRestaurantName());
        } else {
            // Placeholder while the restaurant data is loading or unavailable
            holder.restaurantName.setText("Loading...");
        }
    }

    public void updateRestaurant(String resId,Restaurant restaurant){
        resturantsMap.put(resId,restaurant);
        //notify data of changed item
        for (int i=0;i<products.size();i++){
            if (products.get(i).getResId().equals(resId)){
                notifyItemChanged(i);
                break;
            }
        }
    }
    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductsVH extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView foodName;
        TextView productPrice,restaurantName;
        public ProductsVH(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.food_image_id);
            foodName = itemView.findViewById(R.id.product_name_id);
            productPrice = itemView.findViewById(R.id.product_price_id);
            restaurantName = itemView.findViewById(R.id.restaurant_name_id);
        }
    }
}
