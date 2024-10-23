package com.example.logisticcavan.products.getproducts.presentation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.logisticcavan.navigations.commonui.HomeFragmentDirections;
import com.example.logisticcavan.restaurants.domain.ProductWithRestaurant;
import com.example.logisticcavan.R;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.restaurants.domain.Restaurant;

public class ProductsAdapter extends ListAdapter<ProductWithRestaurant, ProductsAdapter.ProductsVH> {

    NavController navigationController;
    public ProductsAdapter() {
        super(new ProductsDiffUtil());
    }

    @NonNull
    @Override
    public ProductsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        navigationController = Navigation.findNavController(parent);
        return new ProductsVH(productsView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsVH holder, int position) {
        Product product = getItem(position).getProduct();
        Restaurant restaurant = getItem(position).getRestaurant();
        if (product != null ){
            Glide.with(holder.itemView)
                    .load(product.getProductImageLink())
                    .override(800, 800)
                    .into(holder.productImage);
            holder.foodName.setText(product.getProductName());
            holder.productPrice.setText((int) product.getProductPrice() + " US");
        }
        if (restaurant != null){
            holder.restaurantName.setText(restaurant.getRestaurantName());
        }else {
            holder.restaurantName.setText("Loading...");
        }
        holder.itemView.setOnClickListener(v->{

            NavDirections action = HomeFragmentDirections.actionHomeFragmentToFoodDetailFragment(getItem(position));
            navigationController.navigate(action);
//
        });
    }
    public static class ProductsVH extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView foodName;
        TextView productPrice, restaurantName;
        public ProductsVH(@NonNull View itemView) {
            super(itemView);
//            productImage = itemView.findViewById(R.id.food_image_id);
//            foodName = itemView.findViewById(R.id.product_name_id);
//            productPrice = itemView.findViewById(R.id.product_price_id);
//            restaurantName = itemView.findViewById(R.id.restaurant_name_id);
        }
    }
    static class ProductsDiffUtil extends DiffUtil.ItemCallback<ProductWithRestaurant> {

        @Override
        public boolean areItemsTheSame(@NonNull ProductWithRestaurant oldItem, @NonNull ProductWithRestaurant newItem) {
            return oldItem.getProduct().getProductName().equals(newItem.getProduct().getProductName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductWithRestaurant oldItem, @NonNull ProductWithRestaurant newItem) {
            return oldItem == newItem;
        }
    }
}
