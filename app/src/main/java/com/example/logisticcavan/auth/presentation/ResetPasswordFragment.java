package com.example.logisticcavan.auth.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentResetPasswordBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;



@AndroidEntryPoint

public class ResetPasswordFragment extends BaseFragment {

    private FragmentResetPasswordBinding binding;
    private NavController navController;
    String email;

    @Inject
    AuthViewModel authViewModel;
    @Inject

    FirebaseAuth firebaseAuth;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


         binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reset_password, container, false);
         return binding.getRoot();

     }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setUpClickListener();
    }

    private void setUpClickListener() {


        binding.send.setOnClickListener(view -> {

            if (validateInputs()) {

                showProgressDialog();
                resultOfRetrievingUserInfo(authViewModel.getUserRemotely(email));
            }

        });


    }

    private boolean validateInputs() {

        email = binding.editEmail.getText().toString().trim();

        // Email validation
        if (email.isEmpty()) {
            binding.textInputLayoutEmail.setError(getString(R.string.email_required));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.setError(getString(R.string.invalid_email));
            return false;
        } else {
            binding.textInputLayoutEmail.setError(null);
        }
        return true;
    }

    private void resultOfRetrievingUserInfo(CompletableFuture<UserInfo> result) {

        result.thenAccept(userInfo -> {
           sendResetEmail(userInfo.getEmail());
            Log.e("TAG", "resultOfRetrievingUserInfo: 77 " + userInfo.getType());

        }).exceptionally(ex -> {
            Log.e("TAG", "resultOfRetrievingUserInfo:66 " + ex.getMessage());
            dismissProgressDialog();
            showError(binding.getRoot(), ex.getMessage());
            return null;

        });

    }

    private void sendResetEmail(String email) {
        Log.e("TAG", "sendResetEmail  ->  " + email);
         firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
             Log.e("TAG", "resultOfSendingResetEmail: suc ");

             dismissProgressDialog();
            showSuccess(binding.getRoot(), "Email sent successfully");
            navController.popBackStack();
         }).addOnFailureListener(e -> {
             Log.e("TAG", "resultOKfSendingResetEmail:  -> "+e.getMessage());

             dismissProgressDialog();
             showError(binding.getRoot(), e.getMessage());
         });


    }


}