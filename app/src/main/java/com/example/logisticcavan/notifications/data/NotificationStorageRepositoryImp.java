package com.example.logisticcavan.notifications.data;


import static com.example.logisticcavan.common.utils.Constant.NOTIFICATIONS_List;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.logisticcavan.common.utils.Constant;
import com.example.logisticcavan.notifications.domain.entity.Notification;
import com.example.logisticcavan.notifications.domain.repo.NotificationStorageRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class NotificationStorageRepositoryImp implements NotificationStorageRepository {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    @Inject
    public NotificationStorageRepositoryImp(FirebaseFirestore firebaseFirestore, FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = firebaseFirestore;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public CompletableFuture<Void> storeNotificationRemotely(Notification notification, String email) {

        Log.e("TAG", "storeNotificationRemotely: " + email);

        CompletableFuture<Void> future = new CompletableFuture<>();
        firebaseFirestore
                .collection(Constant.NOTIFICATIONS)
                .document(email)
                .update(NOTIFICATIONS_List, FieldValue.arrayUnion(notification))
                .addOnSuccessListener(aVoid -> {
                    Log.e("TAG", "storeNotificationRemotely:2 " + aVoid);
                    future.complete(null);

                }).addOnFailureListener(ex -> {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    List<Notification> newNotification = new ArrayList<>();
                    newNotification.add(notification);
                    hashMap.put(NOTIFICATIONS_List, newNotification);
                    firebaseFirestore.collection(Constant.NOTIFICATIONS)
                            .document(email)
                            .set(hashMap)
                            .addOnSuccessListener(aVoid -> {
                                Log.e("TAG", "storeNotificationRemotely:3 " + aVoid);
                                future.complete(null);
                            }).addOnFailureListener(future::completeExceptionally);
                });

        return future;
    }

    @Override
    public CompletableFuture<List<Notification>> getNotifications() {

        CompletableFuture<List<Notification>> future = new CompletableFuture<>();
        firebaseFirestore
                .collection(Constant.NOTIFICATIONS)
                .document(Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()))
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<HashMap<String, Object>> notificationMaps = (List<HashMap<String, Object>>) documentSnapshot.get(NOTIFICATIONS_List);
                        List<Notification> notifications = new ArrayList<>();

                        if (notificationMaps != null) {
                            for (HashMap<String, Object> map : notificationMaps) {
                                notifications.add(getNotification(map));
                            }
                            future.complete(notifications);
                        }
                    } else {
                        future.complete(new ArrayList<>());
                    }
                })
                .addOnFailureListener(ex -> {
                    future.completeExceptionally(ex);
                });

        return future;
    }

    @NonNull
    private Notification getNotification(HashMap<String, Object> map) {
        String message = (String) map.get("message");
        String timeStamp = (String) map.get("timestamp");
        Notification notification = new Notification(message);
        notification.setTimestamp(timeStamp);
        return notification;
    }


}
