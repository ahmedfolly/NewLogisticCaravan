package com.example.logisticcavan.notifications.domain.usecase;

import com.example.logisticcavan.notifications.data.NotificationStorageRepositoryImp;
import com.example.logisticcavan.notifications.domain.repo.NotificationStorageRepository;

import javax.inject.Inject;

public class GetNotificationsUseCase {

    private NotificationStorageRepository notificationStorageRepository;

    @Inject
    public GetNotificationsUseCase(NotificationStorageRepository notificationStorageRepository) {
        this.notificationStorageRepository = notificationStorageRepository;
    }
}
