package com.example.logisticcavan.more.presentaion;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.data.LocalStorageRepository;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentChangePasswordBinding;
import com.example.logisticcavan.navigations.commonui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChangePasswordFragment extends BaseFragment {

    private FragmentChangePasswordBinding binding;
    private NavController navController;

    @Inject
    FirebaseAuth firebaseAuth;

    @Inject
    SettingsViewModel settingsViewModel;

    @Inject
    LocalStorageRepository localStorageRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_password, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setUpClickListener();
        observeViewModel();
    }

    private void setUpClickListener() {

        binding.iconBack.setOnClickListener(view -> {
            navController.popBackStack();
        });

        binding.changePass.setOnClickListener(view -> {
            validateAndChangePassword();
        });
    }

    private void validateAndChangePassword() {
        String oldPassword = binding.editTextOldPass.getText().toString().trim();
        String newPassword = binding.editTextTextNewPass.getText().toString().trim();

        if (TextUtils.isEmpty(oldPassword)) {
            binding.editTextOldPass.setError("Please enter your old password");
            return;
        }
        if (!oldPassword.equals(localStorageRepository.getUserInfo().getPassword())) {
            Log.e("TAG", "validateAndChangePassword: " + localStorageRepository.getUserInfo().getPassword());
            binding.editTextOldPass.setError("Please enter a correct old password");
            return;
        }
        if (TextUtils.isEmpty(newPassword)) {
            binding.editTextTextNewPass.setError("Please enter a new password");
            return;
        }

        if (newPassword.length() < 6) {
            binding.editTextTextNewPass.setError("Password must be at least 6 characters");
            return;
        }
        showProgressDialog();
       settingsViewModel.changePassword(oldPassword, newPassword);
    }


    private void resultChangingPassword(CompletableFuture<Void> result) {
        result.thenAccept(aVoid -> {
            Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();

                    navController.popBackStack();
        }).exceptionally(throwable -> {
            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        });

    }

    private void observeViewModel() {

        settingsViewModel.showSuccessMessage.observe(getViewLifecycleOwner(), successMessage -> {
            if (successMessage != null) {
                Toast.makeText(getContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();

                settingsViewModel.resetShowSuccessMessage();
                navController.popBackStack();
                dismissProgressDialog();
            }
        });

        settingsViewModel.showErrorMessage.observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                settingsViewModel.resetShowErrorMessage();
                dismissProgressDialog();

            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.disappearBottomNav();
        }
    }

}
