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


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private NavController navController;

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
}