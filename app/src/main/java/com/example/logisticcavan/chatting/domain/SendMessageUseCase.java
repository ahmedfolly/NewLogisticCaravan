package com.example.logisticcavan.chatting.domain;

import javax.inject.Inject;

public class SendMessageUseCase {

    private MessagesServiceRepo messagesServiceRepo;

    @Inject
    public SendMessageUseCase(MessagesServiceRepo messagesServiceRepo){
        this.messagesServiceRepo = messagesServiceRepo;
    }

}
