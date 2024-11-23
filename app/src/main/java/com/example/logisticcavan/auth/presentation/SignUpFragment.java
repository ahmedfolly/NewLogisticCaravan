package com.example.logisticcavan.auth.presentation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.R;
import com.example.logisticcavan.auth.domain.entity.RegistrationData;
import com.example.logisticcavan.auth.domain.entity.UserInfo;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentSignUpBinding;
import com.google.firebase.auth.AuthResult;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class SignUpFragment extends BaseFragment {

    private static final String TAG = "SignUpFragment";
    String firstName, lastName, address, phone, name, email, password, confirmPassword;    @Inject
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
            if (validateInputs()) {
                showProgressDialog();
                CompletableFuture<AuthResult> result = authViewModel.signUp(getRegistrationData(email, password));
                resultOfSigning(result);
            }
        });

    }

    private void resultOfSigning(CompletableFuture<AuthResult> result) {
        result.thenAccept(this::storeUserInfo)
              .exceptionally(
                ex -> {
            dismissProgressDialog();
            showError(binding.getRoot(),ex.getMessage());
            return null;
        }
        );
    }

    private void storeUserInfo(AuthResult authResult) {
        String userId =   authResult.getUser().getUid();
        UserInfo userInfo = getUserInfo(userId,firstName,lastName,phone,address,authViewModel.getTypeUser(),email,"Token",password);
        authViewModel.storeUserInfoLocally(userInfo);
        CompletableFuture<Void> result = authViewModel.storeUserInfoRemotely(userInfo);
        resultOfStoring(result);
    }

    private void resultOfStoring(CompletableFuture<Void> result) {
       result.thenAccept(aVoid -> {

           dismissProgressDialog();
           navigateBasedOnUser(authViewModel.getTypeUser());

       }).exceptionally(ex -> {
           dismissProgressDialog();
           showError(binding.getRoot(),ex.getMessage());
           return null;
       });


    }

    private boolean validateInputs() {
        firstName = binding.editFirstName.getText().toString().trim();
        lastName = binding.editLastName.getText().toString().trim();
        address = binding.editAdress.getText().toString().trim();
        phone = binding.editPhone.getText().toString().trim();
        name = firstName + " " + lastName;
        email = binding.editEmail.getText().toString().trim();
        password = binding.editPassword.getText().toString().trim();
        confirmPassword = binding.editPasswordConfirme.getText().toString().trim();

        if (firstName.isEmpty()) {
            binding.textInputFirstName.setError(getString(R.string.first_name_required));
            return false;
        }

        if (lastName.isEmpty()) {
            binding.textInputLayoutLastName.setError(getString(R.string.last_name_required));
            return false;
        }

        if (address.isEmpty()) {
            binding.textInputLayoutAdress.setError(getString(R.string.address_required));
            return false;
        }

        if (phone.isEmpty()) {
            binding.textInputLayoutPhone.setError(getString(R.string.phone_required));
            return false;
        } else if (!android.util.Patterns.PHONE.matcher(phone).matches()) {
            binding.textInputLayoutPhone.setError(getString(R.string.invalid_phone));
            return false;
        }

        if (email.isEmpty()) {
            binding.textInputLayoutEmail.setError(getString(R.string.email_required));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.setError(getString(R.string.invalid_email));
            return false;
        }

        if (password.isEmpty()) {
            binding.textInputLayoutPassword.setError(getString(R.string.password_required));
            return false;
        } else if (password.length() < 6) {
            binding.textInputLayoutPassword.setError(getString(R.string.password_length));
            return false;
        }

        if (confirmPassword.isEmpty()) {
            binding.textInputLayoutConfirmPassworde.setError(getString(R.string.confirm_password_required));
            return false;
        } else if (!confirmPassword.equals(password)) {
            binding.textInputLayoutConfirmPassworde.setError(getString(R.string.password_mismatch));
            return false;
        }

        return true;
    }

    private UserInfo getUserInfo(String id,
                                 String firstName ,
                                 String lastName ,
                                 String phone ,
                                 String address ,
                                 String type,
                                 String email,
                                 String token, String password) {
        return  new UserInfo(id,firstName,lastName,phone,address,type,email,token,password);
    }

    private RegistrationData getRegistrationData(String email, String password) {

   return new RegistrationData(email ,password) ;

    }






}