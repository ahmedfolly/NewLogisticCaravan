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
import com.example.logisticcavan.auth.domain.entity.RegistrationData;
import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentLoginBinding;
import com.google.firebase.auth.AuthResult;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;
    private NavController navController;

    String email,password;

    @Inject
    AuthViewModel authViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setUpClickListener();
    }


    private void setUpClickListener() {


        binding.forgetPass.setOnClickListener(view -> {

            navController.navigate(R.id.action_loginFragment_to_restorPasswordFragment);

        });


        binding.signUp.setOnClickListener(view -> {

            navController.navigate(R.id.action_loginFragment_to_chooseFragment);

        });
        binding.logIn.setOnClickListener(view -> {

            if (validateInputs()) {
                showProgressDialog();
                CompletableFuture<AuthResult> result = authViewModel.login(getRegistrationData(email, password));
                resultOfLogIn(result);
            }

        });


    }

    private void resultOfLogIn(CompletableFuture<AuthResult> result) {
        result.thenAccept(authResult -> {
            resultOfRetrievingUserInfo(authViewModel.getUserRemotely(email));
        }).exceptionally(ex -> {
            Log.d("TAG", "resultOfLogIn: "+ex.getMessage());
            dismissProgressDialog();
            showError(binding.getRoot(), ex.getMessage());
            return null;
        });

    }

    private void resultOfRetrievingUserInfo(CompletableFuture<UserInfo> result) {
        result.thenAccept(userInfo -> {
            authViewModel.storeUserInfoLocally(userInfo);
            navigateBasedOnUser(userInfo.getType());
            dismissProgressDialog();

        }).exceptionally(ex -> {
            dismissProgressDialog();

            showError(binding.getRoot(), ex.getMessage());

            return null;

        });
    }

    private RegistrationData getRegistrationData(String email, String password) {

        return new RegistrationData(email, password);

    }
    private boolean validateInputs() {

        email = binding.editEmail.getText().toString().trim();
        password = binding.editPassword.getText().toString().trim();

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

        // Password validation
        if (password.isEmpty()) {
            binding.textInputLayoutPassword.setError(getString(R.string.password_required));
            return false;
        } else if (password.length() < 6) {
            binding.textInputLayoutPassword.setError(getString(R.string.password_length));
            return false;
        } else {
            binding.textInputLayoutPassword.setError(null);
        }

        return true;
    }

}