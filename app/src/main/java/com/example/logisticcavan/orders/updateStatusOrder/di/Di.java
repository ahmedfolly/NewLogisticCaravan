package com.example.logisticcavan.orders.updateStatusOrder.di;

import com.example.logisticcavan.orders.updateStatusOrder.data.UpdateOrderStatusRepo;
import com.example.logisticcavan.orders.updateStatusOrder.domain.UpdateOrderStatusUseCase;
import com.example.logisticcavan.orders.updateStatusOrder.presentaion.UpdateOrderStatusViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class Di {

    @Provides
    @Singleton
    UpdateOrderStatusViewModel provideUpdateOrderStatusViewModel(UpdateOrderStatusUseCase updateOrderStatusUseCase){
        return new UpdateOrderStatusViewModel(updateOrderStatusUseCase);
    }


    @Provides
    UpdateOrderStatusUseCase provideUpdateOrderStatusUseCase(UpdateOrderStatusRepo updateOrderStatusRepo){
        return new UpdateOrderStatusUseCase(updateOrderStatusRepo);
    }


    @Provides
    @Singleton
    UpdateOrderStatusRepo provideUpdateOrderStatusRepo(FirebaseFirestore firebaseFirestore){
        return new UpdateOrderStatusRepo(firebaseFirestore);
    }


}
