package com.example.logisticcavan.cart;

import android.content.Context;

import com.example.logisticcavan.cart.data.AppDatabase;
import com.example.logisticcavan.cart.data.CartDao;
import com.example.logisticcavan.cart.data.CartRepoImp;
import com.example.logisticcavan.cart.domain.repos.CartRepo;
import com.example.logisticcavan.cart.domain.usecases.AddToCartUseCase;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class CartDI {
    @Provides
    public AddToCartUseCase provideAddToCartUseCase(CartRepo cartRepo) {
        return new AddToCartUseCase(cartRepo);
    }

    @Provides
    public CartRepo provideCartRepo(CartDao cartDao) {
        return new CartRepoImp(cartDao);
    }

    @Provides
    public AppDatabase providesAppDatabase(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context);
    }

    @Provides
    public CartDao providesCartDao(AppDatabase appDatabase) {
        return appDatabase.cartDao();
    }
}
