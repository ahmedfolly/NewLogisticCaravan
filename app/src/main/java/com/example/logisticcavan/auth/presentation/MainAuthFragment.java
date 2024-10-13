package com.example.logisticcavan.auth.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.logisticcavan.R;
import com.example.logisticcavan.databinding.FragmentMainAuthBinding;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint

public class MainAuthFragment extends Fragment {

    private FragmentMainAuthBinding binding;
    private NavController navController;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_auth, container, false);
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
            navController.navigate(R.id.action_mainAuthFragment_to_loginFragment);
        });

        binding.signUp.setOnClickListener(view -> {
            navController.navigate(R.id.action_mainAuthFragment_to_chooseFragment);
        });
    }
}