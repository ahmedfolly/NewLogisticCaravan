package com.example.logisticcavan.cart;

import android.content.Context;

import com.example.logisticcavan.cart.data.AppDatabase;
import com.example.logisticcavan.cart.data.CartDao;
import com.example.logisticcavan.cart.data.CartRepoImp;
import com.example.logisticcavan.cart.domain.repos.CartRepo;
import com.example.logisticcavan.cart.domain.usecases.AddToCartUseCase;
import com.example.logisticcavan.cart.domain.usecases.DeleteCartItemByIdUseCase;
import com.example.logisticcavan.cart.domain.usecases.EmptyCartUseCase;
import com.example.logisticcavan.cart.domain.usecases.GetCartCountUseCase;
import com.example.logisticcavan.cart.domain.usecases.GetCartProductsUseCase;
import com.example.logisticcavan.cart.domain.usecases.GetRestaurantIdOfFirstItemUseCase;
import com.example.logisticcavan.cart.domain.usecases.GetTotalPriceUseCase;
import com.example.logisticcavan.cart.domain.usecases.UpdateQuantityUseCase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class CartDI {
    @Provides
    public GetTotalPriceUseCase provideGetTotalPriceUseCase(CartRepo cartRepo) {
        return new GetTotalPriceUseCase(cartRepo);
    }

    @Provides
    public DeleteCartItemByIdUseCase provideDeleteCartItemByIdUseCase(CartRepo cartRepo) {
        return new DeleteCartItemByIdUseCase(cartRepo);
    }

    @Provides
    public UpdateQuantityUseCase provideUpdateQuantityUseCase(CartRepo cartRepo) {
        return new UpdateQuantityUseCase(cartRepo);
    }

    @Provides
    public GetRestaurantIdOfFirstItemUseCase provideGetRestaurantIdOfFirstItemUseCase(CartRepo cartRepo) {
        return new GetRestaurantIdOfFirstItemUseCase(cartRepo);
    }

    @Provides
    public AddToCartUseCase provideAddToCartUseCase(CartRepo cartRepo) {
        return new AddToCartUseCase(cartRepo);
    }
    @Provides
    public EmptyCartUseCase provideEmptyCartUseCase(CartRepo cartRepo) {
        return new EmptyCartUseCase(cartRepo);
    }

    @Provides
    public GetCartCountUseCase provideGetCartCountUseCase(CartRepo cartRepo) {
        return new GetCartCountUseCase(cartRepo);
    }
    @Provides
    public GetCartProductsUseCase providesGetCartProductsUseCase(CartRepo cartRepo){
        return new GetCartProductsUseCase(cartRepo);
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
