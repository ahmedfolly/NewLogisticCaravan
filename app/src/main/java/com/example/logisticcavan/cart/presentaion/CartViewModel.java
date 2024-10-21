package com.example.logisticcavan.cart.presentaion;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.domain.usecases.AddToCartUseCase;
import com.example.logisticcavan.cart.domain.usecases.EmptyCartUseCase;
import com.example.logisticcavan.cart.domain.usecases.GetCartCountUseCase;
import com.example.logisticcavan.cart.domain.usecases.GetCartProductsUseCase;
import com.example.logisticcavan.cart.domain.usecases.GetRestaurantIdOfFirstItemUseCase;
import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class CartViewModel extends ViewModel {
    private final AddToCartUseCase addToCartUseCase;
    private final GetRestaurantIdOfFirstItemUseCase getRestaurantIdOfFirstItemUseCase;
    private final EmptyCartUseCase emptyCartUseCase;
    private final GetCartCountUseCase getCartCountUseCase;
    private final GetCartProductsUseCase getCartProductsUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<MyResult<List<CartItem>>> cartItemsLiveData = new MutableLiveData<>();

    @Inject
    public CartViewModel(AddToCartUseCase addToCartUseCase,
                         GetRestaurantIdOfFirstItemUseCase getRestaurantIdOfFirstItemUseCase,
                         EmptyCartUseCase emptyCartUseCase,
                         GetCartCountUseCase getCartCountUseCase,
                         GetCartProductsUseCase getCartProductsUseCase) {
        this.addToCartUseCase = addToCartUseCase;
        this.getRestaurantIdOfFirstItemUseCase = getRestaurantIdOfFirstItemUseCase;
        this.emptyCartUseCase = emptyCartUseCase;
        this.getCartCountUseCase = getCartCountUseCase;
        this.getCartProductsUseCase = getCartProductsUseCase;
    }

    public void addToCart(CartItem cartItem, AddToCartResultCallback itemCallback) {
        disposable.add(addToCartUseCase.execute(cartItem).subscribe(itemCallback::onSuccess, itemCallback::onError));
    }

    public void getRestaurantIdOfFirstItem(String restaurantId, GetRestaurantIdCallback itemCallback) {
        disposable.add(getRestaurantIdOfFirstItemUseCase.execute(restaurantId).subscribe(itemCallback::onSuccess, itemCallback::onError));
    }

    public void emptyCart(EmptyCartResultCallback emptyCartResultCallback) {
        disposable.add(emptyCartUseCase.execute().subscribe(()->{emptyCartResultCallback.onSuccess(true);}, emptyCartResultCallback::onError));
    }

    public void getCartCount(CartCountCallback cartCountCallback) {
        disposable.add(
                getCartCountUseCase.execute().subscribe(cartCountCallback::onSuccess,
                        cartCountCallback::onError
                )
        );
    }
    public void getCartItems(){
        disposable.add(getCartProductsUseCase.execute().subscribe(cartItemsLiveData::postValue));
    }
    public LiveData<MyResult<List<CartItem>>> getCartItemsData(){
        return cartItemsLiveData;
    }

    public interface GetRestaurantIdCallback {
        void onSuccess(boolean isExist);

        void onError(Throwable e);
    }

    public interface AddToCartResultCallback {
        void onSuccess(boolean isAdded);

        void onError(Throwable e);
    }

    public interface EmptyCartResultCallback {
        void onSuccess(boolean isDeleted);

        void onError(Throwable e);
    }

    public interface CartCountCallback {
        void onSuccess(int count);

        void onError(Throwable e);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
