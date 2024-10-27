package com.example.logisticcavan.notifications.data;


import static com.example.logisticcavan.common.utils.Constant.NOTIFICATIONS;
import static com.example.logisticcavan.common.utils.Constant.NOTIFICATIONS_List;

import com.example.logisticcavan.notifications.domain.entity.Notification;
import com.example.logisticcavan.notifications.domain.repo.NotificationStorageRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


        CompletableFuture<Void> future = new CompletableFuture<>();
        firebaseFirestore
                .collection(NOTIFICATIONS)
                .document(email)
                .update(NOTIFICATIONS_List, FieldValue.arrayUnion(notification))
                .addOnSuccessListener(aVoid -> {
                    future.complete(null);

                }).addOnFailureListener(ex -> {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    List<Notification> newNotification =new ArrayList<>();
                    newNotification.add(notification);
                    hashMap.put(NOTIFICATIONS_List, newNotification);
                    firebaseFirestore.collection(NOTIFICATIONS)
                            .document(email)
                            .set(hashMap)
                            .addOnSuccessListener(aVoid -> {
                                future.complete(null);
                            }).addOnFailureListener(future::completeExceptionally);
                });

        return future;
    }

    @Override
    public CompletableFuture<List<Notification>> getNotifications(Notification notification) {

        CompletableFuture<List<Notification>> future = new CompletableFuture<>();
//        firebaseFirestore.collection(NOTIFICATIONS)
//                .document(firebaseAuth.getCurrentUser().getEmail())
//                .get().addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        Map<String, Object> notificationsMap = (Map<String, Object>) documentSnapshot.get(NOTIFICATIONS_List);
//
//                        List<Notification> notifications = (List<Notification>) notificationsMap.get(NOTIFICATIONS_List);
//
////                        // Complete the future with the notifications list, or empty list if null
////                        future.complete(notifications != null ? notifications : Collections.emptyList()
//                    }
//        }).addOnFailureListener(future::completeExceptionally);
        return future;

    }
}
