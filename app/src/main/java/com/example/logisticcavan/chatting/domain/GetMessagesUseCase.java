package com.example.logisticcavan.chatting.domain;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class GetMessagesUseCase {


    private MessagesServiceRepo messagesServiceRepo;

    @Inject
    public GetMessagesUseCase(MessagesServiceRepo messagesServiceRepo){
        this.messagesServiceRepo = messagesServiceRepo;
    }

    public CompletableFuture<List<Message>> execute(String  chatId){
        return messagesServiceRepo.getMessages(chatId);
    }


}
