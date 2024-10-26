package com.example.logisticcavan.firebasemessaging;
import android.util.Log;

import com.example.logisticcavan.firebasemessaging.domain.StoreTokenRemotelyUseCase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingService";

    @Inject
    StoreTokenRemotelyUseCase storeTokenUseCase;

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("TAG", "Refreshed token: " + token);
        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            String notificationTitle = remoteMessage.getNotification().getTitle();
            String notificationBody = remoteMessage.getNotification().getBody();

            Log.e("TAG","Notification Title: " + notificationTitle);
            Log.e("TAG", "Notification Body: " + notificationBody);
        }

        if (remoteMessage.getData().size() > 0) {
            Log.e("TAG","Message data payload: " + remoteMessage.getData());
        }
    }

    private void sendRegistrationToServer(String token) {
        Log.e("TAG","sendRegistrationToServer ");

        storeTokenUseCase.execute(token);
    }
}