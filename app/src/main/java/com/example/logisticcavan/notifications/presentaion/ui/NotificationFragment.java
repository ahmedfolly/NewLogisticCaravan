package com.example.logisticcavan.notifications.presentaion.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.logisticcavan.R;
import com.example.logisticcavan.common.base.BaseFragment;
import com.example.logisticcavan.databinding.FragmentNotificationBinding;
import com.example.logisticcavan.notifications.domain.entity.Notification;
import com.example.logisticcavan.notifications.presentaion.NotificationsAdapter;
import com.example.logisticcavan.notifications.presentaion.NotificationsViewModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultOfGettingNotifications(viewModel.getNotifications());

    }

    private void resultOfGettingNotifications(CompletableFuture<List<Notification>> notifications) {
        notifications.thenAccept(this::updateUi).exceptionally(throwable -> {
            showError(binding.getRoot(), throwable.getMessage());
            return null;
        });
    }

    private void updateUi(List<Notification> notificationList) {
        if (notificationList.isEmpty()) {
            binding.progressBar.setVisibility(View.GONE);
            binding.noNotificationImage.setVisibility(View.VISIBLE);
        } else {

            binding.notifications.setAdapter(new NotificationsAdapter(notificationList));
            binding.progressBar.setVisibility(View.GONE);
            binding.notifications.setVisibility(View.VISIBLE);
        }

    }
}