package com.example.logisticcavan.chatting.presentaion;

import com.example.logisticcavan.chatting.domain.GetMessagesUseCase;
import com.example.logisticcavan.chatting.domain.Message;
import com.example.logisticcavan.chatting.domain.SendMessageUseCase;
import com.example.logisticcavan.common.base.BaseViewModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChattingViewModel extends BaseViewModel {

   private GetMessagesUseCase getMessagesUseCase;
   private SendMessageUseCase sendMessageUseCase;

   @Inject
   public ChattingViewModel(GetMessagesUseCase getMessagesUseCase, SendMessageUseCase sendMessageUseCase){
       this.getMessagesUseCase = getMessagesUseCase;
       this.sendMessageUseCase = sendMessageUseCase;
   }

   public CompletableFuture<List<Message>> getMessages(String chatId){
       return getMessagesUseCase.execute(chatId);
   }


    public CompletableFuture<Void> sendMessage(Message message, String chatId) {
        return sendMessageUseCase.execute(message, chatId);
    }
}
