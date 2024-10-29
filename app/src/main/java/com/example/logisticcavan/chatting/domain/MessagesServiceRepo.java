package com.example.logisticcavan.chatting.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MessagesServiceRepo {

    CompletableFuture<Void> sendMessage(String message, String chatId);

    CompletableFuture<List<Message>> getMessages(String chatId);

}