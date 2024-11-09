package com.example.logisticcavan.caravan.di;

import com.example.logisticcavan.caravan.data.CaravanRepoImp;
import com.example.logisticcavan.caravan.domain.CaravanRepository;
import com.example.logisticcavan.caravan.domain.useCase.GetCaravanProductsUseCase;
import com.example.logisticcavan.caravan.presentation.CaravanViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class CaravanModel {


    @Provides
    @Singleton
    CaravanViewModel provideCViewModel(GetCaravanProductsUseCase getCaravanProductsUseCase){
        return new CaravanViewModel(getCaravanProductsUseCase);
    }

    @Provides
    GetCaravanProductsUseCase provideGetCaravanProductsUseCase(CaravanRepository caravanRepository){
        return new GetCaravanProductsUseCase(caravanRepository);
    }


    @Provides
    @Singleton
    CaravanRepository provideCaravanRepository(FirebaseFirestore firestore){
        return new CaravanRepoImp(firestore);
    }
}
