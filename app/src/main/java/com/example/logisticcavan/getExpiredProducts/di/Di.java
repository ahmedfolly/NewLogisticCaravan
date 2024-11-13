package com.example.logisticcavan.getExpiredProducts.di;

import com.example.logisticcavan.caravan.domain.useCase.GetCaravanProductsUseCase;
import com.example.logisticcavan.getExpiredProducts.presentaion.ExpiredProductsViewModel;
import com.example.logisticcavan.updateStatusCaravanProduct.domain.UpdateStatusCaravanProductUseCase;

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
    ExpiredProductsViewModel provideViewModel(GetCaravanProductsUseCase getCaravanProductsUseCase, UpdateStatusCaravanProductUseCase updateStatusCaravanProductUseCase){
        return new ExpiredProductsViewModel(getCaravanProductsUseCase,updateStatusCaravanProductUseCase);
    }


}
