package com.example.logisticcavan.notifications.presentaion;

import com.example.logisticcavan.common.base.BaseViewModel;
import com.example.logisticcavan.notifications.domain.entity.Notification;
import com.example.logisticcavan.notifications.domain.usecase.GetNotificationsUseCase;
import com.example.logisticcavan.notifications.domain.usecase.StoreNotificationRemotelyUseCase;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class NotificationsViewModel extends BaseViewModel {

   private StoreNotificationRemotelyUseCase storeNotificationRemotelyUseCase;
   private GetNotificationsUseCase getNotificationsUseCase;

    @Inject
    public NotificationsViewModel
            (StoreNotificationRemotelyUseCase storeNotificationRemotelyUseCase,
             GetNotificationsUseCase getNotificationsUseCase) {

        this.storeNotificationRemotelyUseCase = storeNotificationRemotelyUseCase;
        this.getNotificationsUseCase = getNotificationsUseCase;
    }


    public void stor(Notification m, String mail) {
        storeNotificationRemotelyUseCase.execute(m, mail);
    }

    public CompletableFuture
            <List<Notification>> getNotifications() {
        return getNotificationsUseCase.execute();
    }
}
