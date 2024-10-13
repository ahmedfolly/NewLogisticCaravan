package com.example.logisticcavan.auth.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.R;
import com.example.logisticcavan.databinding.FragmentChooseBinding;
import com.example.logisticcavan.databinding.FragmentSignUpBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class SignUpFragment extends Fragment {
    private static final String TAG = "SignUpFragment";

    String email,password,confirmPassword;
    @Inject
    AuthViewModel authViewModel;

    private FragmentSignUpBinding binding;
    private NavController navController;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setUpClickListener();

    }

    private void setUpClickListener() {

        binding.signIn.setOnClickListener(view -> {
            navController.navigate(R.id.action_signUpFragment_to_loginFragment);

        });

        binding.signUp.setOnClickListener(view -> {
          if (validateInputs()){

          }
        });
    }

    private boolean validateInputs() {

         email = binding.editEmail.getText().toString().trim();
         password = binding.editPassword.getText().toString().trim();
         confirmPassword = binding.editPasswordConfirme.getText().toString().trim();

        // Email validation
        if (email.isEmpty()) {
            binding.textInputLayoutEmail.setError("Email is required");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.setError("Enter a valid email");
            return false;
        } else {
            binding.textInputLayoutEmail.setError(null);
        }

        // Password validation
        if (password.isEmpty()) {
            binding.textInputLayoutPassword.setError("Password is required");
            return false;
        } else if (password.length() < 6) {
            binding.textInputLayoutPassword.setError("Password must be at least 6 characters");
            return false;
        } else {
            binding.textInputLayoutPassword.setError(null);
        }

        // Confirm password validation
        if (confirmPassword.isEmpty()) {
            binding.textInputLayoutConfirmPassworde.setError("Please confirm your password");
            return false;
        } else if (!confirmPassword.equals(password)) {
            binding.textInputLayoutConfirmPassworde.setError("Passwords do not match");
            return false;
        } else {
            binding.textInputLayoutConfirmPassworde.setError(null);
        }


        return true;
    }



}