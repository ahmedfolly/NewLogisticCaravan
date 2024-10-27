package com.example.logisticcavan.notifications.domain.usecase;

import com.example.logisticcavan.notifications.domain.entity.Notification;
import com.example.logisticcavan.notifications.domain.repo.NotificationStorageRepository;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class StoreNotificationRemotelyUseCase {

    private NotificationStorageRepository notificationStorageRepository;

    @Inject
    public StoreNotificationRemotelyUseCase(NotificationStorageRepository notificationStorageRepository) {
        this.notificationStorageRepository = notificationStorageRepository;
    }
    public CompletableFuture<Void> execute(Notification notification, String email) {
        return notificationStorageRepository.storeNotificationRemotely(notification, email);
    }
}
