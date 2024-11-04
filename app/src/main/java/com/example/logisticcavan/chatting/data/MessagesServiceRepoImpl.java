package com.example.logisticcavan.chatting.data;

import com.example.logisticcavan.chatting.domain.Message;
import com.example.logisticcavan.chatting.domain.Messages;
import com.example.logisticcavan.chatting.domain.MessagesServiceRepo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class MessagesServiceRepoImpl implements MessagesServiceRepo {

    private FirebaseFirestore firebaseFirestore;
    private Messages messages;

    @Inject
    public MessagesServiceRepoImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public CompletableFuture<Void> sendMessage(Message message, String chatId) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        messages.setMessage(message);
        firebaseFirestore
                .collection("chats")
                .document(chatId).set(messages)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        future.complete(null);
                    } else {
                        future.completeExceptionally(task.getException());
                    }
                });
        return future;
    }

    @Override
    public CompletableFuture<List<Message>> getMessages(String chatId) {
        CompletableFuture<List<Message>> future = new CompletableFuture<>();
        firebaseFirestore
                .collection("chats")
                .document(chatId)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        messages = new Messages();
                        messages.setMessages(new ArrayList<>());
                        future.completeExceptionally(error);
                    } else {
                        if (value != null) {
                            messages = value.toObject(Messages.class);
                            if (messages != null) {
                                future.complete(messages.getMessages());
                            } else {
                                initMessages(future);
                            }
                        } else {
                            initMessages(future);
                        }
                    }
                });
        return future;

    }

    private void initMessages(CompletableFuture<List<Message>> future) {
        messages = new Messages();
        messages.setMessages(new ArrayList<>());
        future.complete(messages.getMessages());
    }

}
