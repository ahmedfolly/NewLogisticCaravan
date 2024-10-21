package com.example.logisticcavan.auth.presentation;

import static com.example.logisticcavan.common.utils.Constant.COURIER;
import static com.example.logisticcavan.common.utils.Constant.CUSTOMER;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.R;
import com.example.logisticcavan.databinding.FragmentChooseBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class ChooseFragment extends Fragment {
    private static final String TAG = "ChooseFragment";
    private FragmentChooseBinding binding;
    private NavController navController;

    @Inject
    AuthViewModel authViewModel;

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

            authViewModel.setTypeUser(CUSTOMER);
            Log.e(TAG, authViewModel.getTypeUser());
            navController.navigate(R.id.action_chooseFragment_to_signUpFragment);

        });


        binding.courier.setOnClickListener(view -> {

            authViewModel.setTypeUser(COURIER);
            navController.navigate(R.id.action_chooseFragment_to_signUpFragment);

        });
    }


}