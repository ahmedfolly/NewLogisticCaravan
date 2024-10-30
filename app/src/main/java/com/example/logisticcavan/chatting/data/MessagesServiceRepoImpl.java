package com.example.logisticcavan.chatting.data;

import android.util.Log;

import com.example.logisticcavan.chatting.domain.Message;
import com.example.logisticcavan.chatting.domain.Messages;
import com.example.logisticcavan.chatting.domain.MessagesServiceRepo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
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
        messages.setMessage(message);
        firebaseFirestore
                .collection("chats")
                .document(chatId).set(messages)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.e("TAG", "sendMessage: success");
                    } else {
                        Log.e("TAG", "sendMessage: failed");
                    }
                });
        return null;
    }


    @Override
    public CompletableFuture<List<Message>> getMessages(String chatId) {
        CompletableFuture<List<Message>> future = new CompletableFuture<>();
        firebaseFirestore.collection("chats").document(chatId)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                messages = task.getResult().toObject(Messages.class);
                if (messages != null) {
                    Log.e("TAG", "getMessages: not null");
                    future.complete(messages.getMessages());
                } else {
                    Log.e("TAG", "getMessages: null");
                    messages = new Messages();
                    messages.setMessages(new ArrayList<>());
                    future.complete(messages.getMessages());
                }
            } else {
                Log.e("TAG", "getMessages: " + task.getException());
                future.completeExceptionally(task.getException());
            }
        });
        return future;

    }

}
