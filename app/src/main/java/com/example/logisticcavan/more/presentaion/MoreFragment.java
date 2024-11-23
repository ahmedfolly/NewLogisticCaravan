package com.example.logisticcavan.more.presentaion;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.logisticcavan.auth.presentation.AuthActivity;
import com.example.logisticcavan.databinding.FragmentMoreBinding;
import com.example.logisticcavan.navigations.commonui.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MoreFragment extends Fragment {
    private FragmentMoreBinding binding;
    private NavController navController;

    @Inject
    public FirebaseAuth firebaseAuth;

    @Inject
    public SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setUpClickListener();
    }


    private void setUpClickListener() {

        binding.iconProfile.setOnClickListener(view -> {
            navController.navigate(R.id.action_moreFragment_to_profileFragment);
        });

        binding.iconAboutApp.setOnClickListener(view -> {
            navController.navigate(R.id.action_moreFragment_to_aboutAppFragment);
        });
        binding.logOut.setOnClickListener(view -> {
            signOut();
        });
    }

    private void signOut() {
        sharedPreferences.edit().clear().apply();
        firebaseAuth.signOut();
        startActivity(new Intent(getActivity(), AuthActivity.class));
        getActivity().finish();
    }
}