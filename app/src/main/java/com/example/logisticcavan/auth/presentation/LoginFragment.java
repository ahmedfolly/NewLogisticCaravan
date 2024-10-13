package com.example.logisticcavan.auth.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.R;
import com.example.logisticcavan.databinding.FragmentLoginBinding;
import com.example.logisticcavan.databinding.FragmentMainAuthBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private NavController navController;
    String email,password,confirmPassword;

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
    }


    private boolean validateInputs() {

        email = binding.editEmail.getText().toString().trim();
        password = binding.editPassword.getText().toString().trim();

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

        return true;
    }

}