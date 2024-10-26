package com.example.logisticcavan.more.presentaion;

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
import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.databinding.FragmentProfileBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private NavController navController;

    @Inject
    SettingsViewModel settingsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        return binding.getRoot();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
         setUpData(settingsViewModel.getUserInfo());
        setUpClickListener();
    }

    private void setUpClickListener() {

        binding.iconBack.setOnClickListener(view -> {
            navController.popBackStack();
        });
        binding.changePass.setOnClickListener(view -> {
            navController.navigate(R.id.action_profileFragment_to_changePasswordFragment);
        });

    }

    private void setUpData(UserInfo userInfo) {
        binding.editTextFirstName.setText(userInfo.getName());
        binding.editTextSecondName.setText(userInfo.getLastName());
        binding.editTextTextEmail.setText(userInfo.getEmail());
        binding.editTextPhone.setText(userInfo.getPhone());
        binding.editTextAdress.setText(userInfo.getAddress());
    }
}