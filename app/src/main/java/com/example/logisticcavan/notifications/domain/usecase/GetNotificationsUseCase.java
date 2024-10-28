package com.example.logisticcavan.notifications.domain.usecase;

import com.example.logisticcavan.notifications.data.NotificationStorageRepositoryImp;
import com.example.logisticcavan.notifications.domain.entity.Notification;
import com.example.logisticcavan.notifications.domain.repo.NotificationStorageRepository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class GetNotificationsUseCase {

    private NotificationStorageRepository notificationStorageRepository;

    @Inject
    public GetNotificationsUseCase(NotificationStorageRepository notificationStorageRepository) {
        this.notificationStorageRepository = notificationStorageRepository;
    }

    public CompletableFuture<List<Notification>> execute() {
        return notificationStorageRepository.getNotifications();
    }
}
