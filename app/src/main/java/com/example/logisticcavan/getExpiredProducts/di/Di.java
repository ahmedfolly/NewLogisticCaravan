package com.example.logisticcavan.getExpiredProducts.di;

import com.example.logisticcavan.caravan.domain.useCase.GetCaravanProductsUseCase;
import com.example.logisticcavan.getExpiredProducts.presentaion.ExpiredProductsViewModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class Di {



    @Provides
    @Singleton
    ExpiredProductsViewModel provideViewModel(GetCaravanProductsUseCase getCaravanProductsUseCase){
        return new ExpiredProductsViewModel(getCaravanProductsUseCase);
    }


}
