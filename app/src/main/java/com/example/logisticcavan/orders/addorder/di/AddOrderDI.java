package com.example.logisticcavan.orders.addorder.di;

import com.example.logisticcavan.orders.addorder.data.AddOrderRepoImp;
import com.example.logisticcavan.orders.addorder.domain.AddOrderRepo;
import com.example.logisticcavan.orders.addorder.domain.AddOrderUseCase;
import com.google.firebase.firestore.FirebaseFirestore;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AddOrderDI {
    @Provides
    AddOrderUseCase providesAddOrderUseCase(AddOrderRepo repo) {
        return new AddOrderUseCase(repo);
    }
    @Provides
    AddOrderRepo providesAddOrderRepo(FirebaseFirestore firestore) {
        return new AddOrderRepoImp(firestore);
    }
}
