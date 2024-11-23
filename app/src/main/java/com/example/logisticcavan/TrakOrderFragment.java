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
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.logisticcavan.auth.presentation.AuthViewModel;
import com.example.logisticcavan.common.utils.Constant;
import com.example.logisticcavan.navigations.commonui.MainActivity;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class TrakOrderFragment extends Fragment {

    private LinearProgressIndicator statusProgress;
    private NavController navController;
    private AuthViewModel authViewModel;

    public TrakOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trak_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = findNavController(view);

        TrakOrderFragmentArgs args = TrakOrderFragmentArgs.fromBundle(getArguments());
        TextView statusDesc = view.findViewById(R.id.status_desc);
        String flag = args.getFlag();
        String courierName = args.getCourierName();
        statusProgress = view.findViewById(R.id.status_progress);
        ImageView shippedImage = view.findViewById(R.id.shipped_image);
        ImageView deliveredImage = view.findViewById(R.id.delivered_status);
        TextView rateText= view.findViewById(R.id.user_rate_text);
        if (flag.equals(Constant.flagFromPlaceOrderScreen)) {
            listenForUploadStatus();
            statusDesc.setText(R.string.pending_text);
            setProgressSmoothly(50);
        }
        if (flag.equals(Constant.PENDING)){
            setProgressSmoothly(50);
            statusDesc.setText(R.string.pending_text);
        }
        if (flag.equals(Constant.SHIPPED)){
            setProgressSmoothly(100);
            changeBackground(shippedImage,1250);
            statusDesc.setText(R.string.shipped_text);
        }
        if (flag.equals(Constant.DELIVERED)){
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
            statusDesc.setText(R.string.delieverd_text);
        }
        ImageView backToHome = view.findViewById(R.id.back_to_home);
        backToHome.setOnClickListener(v->{
            navController.navigate(R.id.action_trakOrderFragment_to_homeFragment);
        });
        ImageView chatCourier = view.findViewById(R.id.chat_courier);
        chatCourier.setOnClickListener(v->{
            NavDirections action = TrakOrderFragmentDirections.actionTrakOrderFragmentToChattingFragment(courierName,args.getOrderId());
            navController.navigate(action);
        });

        onBackBtnPressed(flag);
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
        setupSnackbarView(snackbar, customSnackView, snackbarView);
        //setup snackbar text
        setupSnackbarText(message, customSnackView);
        //setup snackbar settings
        setupSnackbarSettings(snackbar, snackbarView);
    }

    private void setupSnackbarView(Snackbar snackbar, View customSnackView, View snackbarView) {
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

    void onBackBtnPressed(String flag){
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController =findNavController(requireView());
                if (flag.equals(Constant.flagFromPlaceOrderScreen)){
                  navController.navigate(R.id.action_trakOrderFragment_to_homeFragment); // Replace with your target destination ID
              }
                else{
                    navController.popBackStack();
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.disappearBottomNav();
        }
    }
}