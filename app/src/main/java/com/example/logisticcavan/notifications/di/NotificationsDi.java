package com.example.logisticcavan.notifications.di;

import com.example.logisticcavan.notifications.data.NotificationStorageRepositoryImp;
import com.example.logisticcavan.notifications.domain.repo.NotificationStorageRepository;
import com.example.logisticcavan.notifications.domain.usecase.GetNotificationsUseCase;
import com.example.logisticcavan.notifications.domain.usecase.StoreNotificationRemotelyUseCase;
import com.example.logisticcavan.notifications.presentaion.NotificationsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class NotificationsDi {



    @Provides
    @Singleton
    NotificationsViewModel provideNotificationsViewModel
            ( StoreNotificationRemotelyUseCase storeNotificationRemotelyUseCase,
              GetNotificationsUseCase getNotificationsUseCase
           ) {
        return new NotificationsViewModel(storeNotificationRemotelyUseCase, getNotificationsUseCase);
    }

    @Provides
    GetNotificationsUseCase provideGetNotificationsUseCase(NotificationStorageRepository notificationStorageRepository) {
        return new GetNotificationsUseCase(notificationStorageRepository);
    }

    @Provides
    StoreNotificationRemotelyUseCase provideStoreNotificationRemotelyUseCase(NotificationStorageRepository notificationStorageRepository) {
        return new StoreNotificationRemotelyUseCase(notificationStorageRepository);
    }

    @Provides
    NotificationStorageRepository provideNotificationStorageRepository(FirebaseFirestore firebaseFirestore , FirebaseAuth firebaseAuth) {
        return new NotificationStorageRepositoryImp(firebaseFirestore,firebaseAuth);
    }

}
