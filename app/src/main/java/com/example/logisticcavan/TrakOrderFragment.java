package com.example.logisticcavan;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.logisticcavan.common.utils.Constant;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;

public class TrakOrderFragment extends Fragment {

    private LinearProgressIndicator statusProgress;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TrakOrderFragmentArgs args = TrakOrderFragmentArgs.fromBundle(getArguments());
        TextView statusDesc = view.findViewById(R.id.status_desc);
        String flag = args.getFlag();
        if (flag.equals(Constant.flagFromPlaceOrderScreen)) {
            listenForUploadStatus();
            statusDesc.setText("Your order is currently being prepared and will be on its way soon! You will be notified once it’s ready and on the way to you.");
        }
        statusProgress = view.findViewById(R.id.status_progress);
        setProgressSmoothly();
    }

    private void setProgressSmoothly() {
        ValueAnimator animator = ValueAnimator.ofInt(statusProgress.getProgress(), 50);
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
}