package com.example.logisticcavan.notifications.domain.repo;

import com.example.logisticcavan.notifications.domain.entity.Notification;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface NotificationStorageRepository {

   CompletableFuture<Void> storeNotificationRemotely(Notification notification, String email);
   CompletableFuture<List<Notification>> getNotifications();

}
