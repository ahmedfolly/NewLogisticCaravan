package com.example.logisticcavan.chatting.domain;

import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class SendMessageUseCase {

    private MessagesServiceRepo messagesServiceRepo;

    @Inject
    public SendMessageUseCase(MessagesServiceRepo messagesServiceRepo){
        this.messagesServiceRepo = messagesServiceRepo;
    }

    public CompletableFuture<Void> execute(Message message, String chatId) {
      return   messagesServiceRepo.sendMessage(message, chatId);
    }
}
