package com.example.logisticcavan.notifications.presentaion.ui;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.logisticcavan.R;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentNotificationBinding;
import com.example.logisticcavan.notifications.domain.entity.Notification;
import com.example.logisticcavan.notifications.presentaion.NotificationsViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class NotificationFragment extends BaseFragment {

    @Inject
    public NotificationsViewModel viewModel;

    private FragmentNotificationBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);

        binding.textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification m = new Notification("fja;ldsf fdajf sdf dafsdjfd afjdf");
                viewModel.stor(m,"mnshat.dev@gmail.com");
            }
        });


        return binding.getRoot();

    }
}