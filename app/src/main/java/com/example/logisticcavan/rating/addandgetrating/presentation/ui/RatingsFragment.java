package com.example.logisticcavan.rating.addandgetrating.presentation.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.logisticcavan.R;
import com.example.logisticcavan.rating.addandgetrating.presentation.GetRestaurantRatingsViewModel;
import com.example.logisticcavan.rating.addandgetrating.presentation.RatingsAdapter;
import com.example.logisticcavan.restaurants.domain.Restaurant;

import org.w3c.dom.Text;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RatingsFragment extends Fragment {

    private RecyclerView ratingsContainer;
    private RatingsAdapter ratingsAdapter;

    private GetRestaurantRatingsViewModel getRestaurantRatingsViewModel;

    public RatingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRestaurantRatingsViewModel = new ViewModelProvider(this).get(GetRestaurantRatingsViewModel.class);
        ratingsAdapter = new RatingsAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ratings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RatingsFragmentArgs args = RatingsFragmentArgs.fromBundle(getArguments());
        Restaurant restaurant = args.getRestaurant();
        String restaurantId = restaurant.getRestaurantId();
        String restaurantLogoLink = restaurant.getRestaurantLogoLink();
        String restaurantName = restaurant.getRestaurantName();
        int totalStars = restaurant.getTotalStars();
        int totalReviewers = restaurant.getTotalReviewers();
        setupRestaurantDetails(restaurantLogoLink,restaurantName,totalStars,totalReviewers);
        getRatings(restaurantId);
    }

    private void getRatings(String restaurantId) {
        getRestaurantRatingsViewModel.getAllRatings(restaurantId);
        getRestaurantRatingsViewModel.getRestaurantRatingsLiveData().observe(getViewLifecycleOwner(), ratingsResult -> {
            ratingsResult.handle(ratings -> {
                ratingsAdapter.submitList(ratings);
                setupRecyclerView();
            }, error -> {
            }, () -> {
            });
        });
    }

    private void setupRecyclerView() {
        ratingsContainer = requireView().findViewById(R.id.ratings_container);
        ratingsContainer.setHasFixedSize(true);
        ratingsContainer.setLayoutManager(new LinearLayoutManager(requireContext()));
        ratingsContainer.setAdapter(ratingsAdapter);
    }

    private void setupRestaurantDetails(String restaurantLogoLink, String restaurantName,int totalStars,int totalReviewers) {
        ImageView restaurantLogo = requireView().findViewById(R.id.restaurant_image_ratings);
        Glide.with(this)
                .load(restaurantLogoLink)
                .circleCrop()
                .into(restaurantLogo);
        TextView restaurantNameTxt = requireView().findViewById(R.id.resaurant_name_ratings);
        restaurantNameTxt.setText(restaurantName);
        TextView totalRatingText = requireView().findViewById(R.id.total_rating_text);
        float totalRating = (float) totalStars/totalReviewers;
        totalRatingText.setText(String.valueOf(totalRating));
    }
}