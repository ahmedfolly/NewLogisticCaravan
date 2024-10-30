package com.example.logisticcavan.chatting.di;


import com.example.logisticcavan.chatting.data.MessagesServiceRepoImpl;
import com.example.logisticcavan.chatting.domain.GetMessagesUseCase;
import com.example.logisticcavan.chatting.domain.MessagesServiceRepo;
import com.example.logisticcavan.chatting.domain.SendMessageUseCase;
import com.example.logisticcavan.chatting.presentaion.ChattingViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ChattingDi {


    @Provides
    @Singleton
    ChattingViewModel provideChattingViewModel(GetMessagesUseCase getMessagesUseCase, SendMessageUseCase sendMessageUseCase) {
        return new ChattingViewModel(getMessagesUseCase, sendMessageUseCase);
    }


    @Provides
    GetMessagesUseCase provideGetMessagesUseCase(MessagesServiceRepo messagesServiceRepo) {
        return new GetMessagesUseCase(messagesServiceRepo);
    }

    @Provides
    SendMessageUseCase provideSendMessageUseCase(MessagesServiceRepo messagesServiceRepo) {
        return new SendMessageUseCase(messagesServiceRepo);
    }

    @Provides
    @Singleton
    MessagesServiceRepo provideMessagesServiceRepo(FirebaseFirestore firebaseFirestore) {
        return new MessagesServiceRepoImpl(firebaseFirestore);
    }

}
