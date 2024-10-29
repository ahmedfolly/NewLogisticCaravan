package com.example.logisticcavan.chatting.data;

import android.util.Log;

import com.example.logisticcavan.chatting.domain.Message;
import com.example.logisticcavan.chatting.domain.Messages;
import com.example.logisticcavan.chatting.domain.MessagesServiceRepo;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class MessagesServiceRepoImpl implements MessagesServiceRepo {

    private FirebaseFirestore firebaseFirestore;

    @Inject
    public MessagesServiceRepoImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public CompletableFuture<Void> sendMessage(String message, String chatId) {
        return null;
    }

    @Override
    public CompletableFuture<List<Message>> getMessages(String chatId) {
        CompletableFuture<List<Message>> future = new CompletableFuture<>();
        firebaseFirestore.collection("chats").document(chatId)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.e("TAG", "getMessages: " + task.getResult().toObject(Messages.class).getMessages());
                List<Message> messages = task.getResult().toObject(Messages.class).getMessages();
                future.complete(messages);
            } else {
                Log.e("TAG", "getMessages: " + task.getException());
                future.completeExceptionally(task.getException());
            }
        });
        return future;

    }

}
