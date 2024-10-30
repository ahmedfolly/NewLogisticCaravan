package com.example.logisticcavan.rating.addandgetrating.presentation.ui;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.R;
import com.example.logisticcavan.SharedViewModel;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.rating.addandgetrating.domain.model.Rating;
import com.example.logisticcavan.rating.addandgetrating.presentation.AddRatingViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import per.wsj.library.AndRatingBar;

@AndroidEntryPoint
public class AddRatingFragment extends Fragment {

    private AddRatingViewModel addRatingViewModel;
    private NavController navController;
    private SharedViewModel sharedViewModel;
    private AuthViewModel authViewModel;

    public AddRatingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addRatingViewModel = new ViewModelProvider(this).get(AddRatingViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rating, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndRatingBar ratingBar = view.findViewById(R.id.rating_bar);
        AddRatingFragmentArgs args = AddRatingFragmentArgs.fromBundle(getArguments());
        MaterialButton submitRateBtn = view.findViewById(R.id.submit_rate_btn);
        TextInputEditText reviewText = view.findViewById(R.id.review_text);
        String restaurantId = args.getRestaurantId();
        String orderId = args.getOrderId();
        Log.d("TAG", "onViewCreated: " + restaurantId);
        submitRateBtn.setOnClickListener(v -> {
            Log.d("TAG", "onViewCreated: " + ratingBar.getRating());
            addRating(restaurantId, orderId, (int) ratingBar.getRating(), reviewText.getText().toString());
            updateRatingVars(restaurantId, (int) ratingBar.getRating());
            navigateToHomeScreen();
        });
    }
    private Rating createRating(String orderId, int stars, String review) {
        Rating rating = new Rating();
        rating.setStars(stars);
        rating.setReview(review);
        rating.setCustomerName(getUserName());
        rating.setOrderId(orderId);
        rating.setDate(getCurrentDate());
        return rating;
    }

    private void addRating(String restaurantId, String orderId, int stars, String review) {
        addRatingViewModel.addRate(restaurantId, createRating(orderId, stars, review), new AddRatingViewModel.AddRateListener() {
            @Override
            public void onSuccess(MyResult<Boolean> result) {
                Log.d("TAG", "onSuccess: your feedback added successfully");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("TAG", "onError: there is a problem in adding your feedback");
            }
        });
    }

    private void updateRatingVars(String restaurantId, int totalStars) {
        addRatingViewModel.updateRateVars(restaurantId, totalStars);
    }

    private void navigateToHomeScreen() {
        sharedViewModel.submitRating();
        navController = findNavController(this);
        NavDirections action = AddRatingFragmentDirections.actionRatingFragmentToHomeFragment();
        navController.navigate(action);
    }

    private String getUserName() {
        return authViewModel.getUserInfoLocally().getName();
    }
    private String getCurrentDate(){
        Date dateOfNow = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy",new Locale("en"));
        return simpleDateFormat.format(dateOfNow);
    }
}