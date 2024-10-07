package com.example.logisticcavan;

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

import com.example.logisticcavan.databinding.FragmentChooseBinding;
import com.example.logisticcavan.databinding.FragmentLoginBinding;


public class ChooseFragment extends Fragment {

    private FragmentChooseBinding binding;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setUpClickListener();
    }

    private void setUpClickListener() {

        binding.customer.setOnClickListener(view -> {
            navController.navigate(R.id.action_chooseFragment_to_signUpFragment);
        });

        binding.courier.setOnClickListener(view -> {
            navController.navigate(R.id.action_chooseFragment_to_signUpFragment);
        });
    }


}