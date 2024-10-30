package com.example.logisticcavan;

import static androidx.navigation.Navigation.findNavController;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.logisticcavan.common.utils.Constant;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

public class TrakOrderFragment extends Fragment {

    private LinearProgressIndicator statusProgress;
    private NavController navController;

    public TrakOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trak_order, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TrakOrderFragmentArgs args = TrakOrderFragmentArgs.fromBundle(getArguments());
        TextView statusDesc = view.findViewById(R.id.status_desc);
        String flag = args.getFlag();
        statusProgress = view.findViewById(R.id.status_progress);
        ImageView shippedImage = view.findViewById(R.id.shipped_image);
        ImageView deliveredImage = view.findViewById(R.id.delivered_status);
        TextView rateText= view.findViewById(R.id.user_rate_text);
        if (flag.equals(Constant.flagFromPlaceOrderScreen)) {
            listenForUploadStatus();
            statusDesc.setText("Your order is currently being prepared and will be on its way soon! You will be notified once it’s ready and on the way to you.");
            setProgressSmoothly(50);
        }
        if (flag.equals(Constant.PENDING)){
            setProgressSmoothly(50);
            statusDesc.setText("Your order is currently being prepared and will be on its way soon! You will be notified once it’s ready and on the way to you.");
        }
        if (flag.equals(Constant.SHIPPED)){
            setProgressSmoothly(100);
            changeBackground(shippedImage,1250);
            statusDesc.setText("Your order is shipped! The order is on the road to you.");
        }if (flag.equals(Constant.DELIVERED)){
            rateText.setVisibility(View.VISIBLE);
            rateText.setOnClickListener(v->{
                String restaurantId = args.getRestaurantId();
                String orderId = args.getOrderId();
                //here open rate fragment
                navController = findNavController(view);
                NavDirections action = TrakOrderFragmentDirections.actionTrakOrderFragmentToRatingFragment(restaurantId,orderId);
                navController.navigate(action);
            });
            setProgressSmoothly(100);
            changeBackground(shippedImage,1250);
            changeBackground(deliveredImage,2000);
            statusDesc.setText("Your order is already delivered!\n We hope you enjoyed your meal!");
        }
        ImageView backToHome = view.findViewById(R.id.back_to_home);
        backToHome.setOnClickListener(v->{
            navController = findNavController(view);
            navController.navigate(R.id.action_trakOrderFragment_to_homeFragment);
        });
        onBackBtnPressed();
    }

    private void changeBackground(ImageView imageView,int duration){
        new Handler().postDelayed(()->{
            imageView.setBackgroundResource(R.drawable.status_background);
        },duration);
    }
    private void setProgressSmoothly(int progress) {
        ValueAnimator animator = ValueAnimator.ofInt(statusProgress.getProgress(), progress);
        animator.setDuration(2500); // Duration of the animation in milliseconds
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                int animatedValue = (int) valueAnimator.getAnimatedValue();
                statusProgress.setProgress(animatedValue);
            }
        });
        animator.start();
    }

    private void listenForUploadStatus() {

        Log.d("TAG", "listenForUploadStatus: ");
        new Handler().postDelayed(() -> showTopSnackbar("Order placed successfully"), 100);
    }

    public void showTopSnackbar(String message) {
        CoordinatorLayout coordinatorLayout = requireActivity().findViewById(R.id.track_order_screen);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_LONG);
        View customSnackView = getLayoutInflater().inflate(R.layout.snackbar_layout, null);
        View snackbarView = snackbar.getView();

        //setup snackbar view
        setupSnackbarSiew(snackbar, customSnackView, snackbarView);
        //setup snackbar text
        setupSnackbarText(message, customSnackView);
        //setup snackbar settings
        setupSnackbarSettings(snackbar, snackbarView);
    }

    private void setupSnackbarSiew(Snackbar snackbar, View customSnackView, View snackbarView) {
        @SuppressLint("RestrictedApi")
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarView;
        snackbarLayout.addView(customSnackView, 0);
    }

    private void setupSnackbarText(String message, View customSnackView) {
        TextView snackbarText = customSnackView.findViewById(R.id.snackbar_text);
        snackbarText.setText(message);
    }

    private void setupSnackbarSettings(Snackbar snackbar, View snackbarView) {
        snackbar.setBackgroundTint(getResources().getColor(R.color.white, null));
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();
        params.gravity = Gravity.TOP;
        params.topMargin = 64;
        snackbarView.setElevation(8);
        snackbarView.setBackgroundResource(R.drawable.snackbar_background);
        snackbarView.setLayoutParams(params);
        snackbar.show();
    }

    void onBackBtnPressed(){
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Get the NavController
                NavController navController =findNavController(requireView());

                // Navigate to the specific destination
                navController.navigate(R.id.action_trakOrderFragment_to_homeFragment); // Replace with your target destination ID
            }
        });
    }
}