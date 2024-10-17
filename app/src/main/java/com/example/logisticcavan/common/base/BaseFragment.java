package com.example.logisticcavan.common.base;

import static com.example.logisticcavan.common.utils.Constant.COURIER;
import static com.example.logisticcavan.common.utils.Constant.CUSTOMER;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.presentation.AuthActivity;
import com.example.logisticcavan.navigations.courierNav.CourierActivity;
import com.example.logisticcavan.navigations.commonui.MainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BaseFragment extends Fragment {

    Dialog progressDialog;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new Dialog(requireContext());
    }



    public void showProgressDialog() {
        if (progressDialog.getOwnerActivity() == null) {
            progressDialog = new Dialog(requireContext());
        }
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        progressDialog.setContentView(R.layout.dialog_progress);

        progressDialog.setCanceledOnTouchOutside(false);

        Window window = progressDialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.show();
    }


    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }



    public void navigateBasedOnUser(String typeUser) {
        if (typeUser.equals(CUSTOMER)) {
            startActivity(new Intent(getActivity(), MainActivity.class));
        }else if (typeUser.equals(COURIER)){
            startActivity(new Intent(getActivity(), CourierActivity.class));
        }

    }

    public  void showSuccess(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(view.getResources().getColor(android.R.color.holo_green_light));
        snackbar.show();
    }

    public static void showError(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(view.getResources().getColor(android.R.color.holo_red_light));
        snackbar.show();
    }


    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), AuthActivity.class));
        requireActivity().finish();
    }


}
