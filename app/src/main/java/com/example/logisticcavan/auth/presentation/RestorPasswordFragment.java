package com.example.logisticcavan.auth.presentation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.logisticcavan.R;

import dagger.hilt.android.AndroidEntryPoint;



@AndroidEntryPoint

public class RestorPasswordFragment extends Fragment {

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_restor_password, container, false);
    }
}