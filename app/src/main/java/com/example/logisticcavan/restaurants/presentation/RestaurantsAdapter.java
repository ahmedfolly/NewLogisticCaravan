package com.example.logisticcavan.restaurants.presentation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.RoundedCorner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.logisticcavan.R;
import com.example.logisticcavan.navigations.commonui.HomeFragmentDirections;
import com.example.logisticcavan.restaurants.domain.Restaurant;

public class RestaurantsAdapter extends ListAdapter<Restaurant, RestaurantsAdapter.RestaurantViewHolder> {
    private NavController navigationController;
    public RestaurantsAdapter() {
        super(new RestaurantsDiffUtil());
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = getItem(position);

        // Bind data to the view holder
        if (restaurant != null) {
            holder.restaurantName.setText(restaurant.getRestaurantName());
            Glide.with(holder.itemView)
                    .asBitmap()
                    .load(restaurant.getRestaurantImageLink())
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .into(holder.restaurantImage);
            holder.itemView.setOnClickListener(v->{
                navigationController = Navigation.findNavController(holder.itemView);
                NavDirections action = HomeFragmentDirections.actionHomeFragmentToRestaurantDetailsFragment(restaurant);
                navigationController.navigate(action);
            });
        }
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        ImageView restaurantImage;
        TextView restaurantName;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = itemView.findViewById(R.id.restaurant_image_home_id);
            restaurantName = itemView.findViewById(R.id.restaurant_name_home_id);
        }
    }
    static class RestaurantsDiffUtil extends DiffUtil.ItemCallback<Restaurant> {

        @Override
        public boolean areItemsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.getRestaurantId().equals(newItem.getRestaurantId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem == newItem;
        }
    }
}
