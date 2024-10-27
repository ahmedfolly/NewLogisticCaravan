package com.example.logisticcavan.cart.data;

import static kotlinx.coroutines.flow.FlowKt.observeOn;

import android.annotation.SuppressLint;

import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.domain.repos.CartRepo;
import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class CartRepoImp implements CartRepo {
    private final CartDao cartDao;

    public CartRepoImp(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    /**
     * @noinspection ResultOfMethodCallIgnored
     */
    @SuppressLint("CheckResult")
    @Override
    public Single<Boolean> insert(CartItem cartItem) {
        return cartDao.insert(cartItem)
                .map(id -> id != -1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<MyResult<List<CartItem>>> getAllCartItems() {
        return cartDao.getAllCartItems()
                .map(MyResult::success)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }

    //getAllCartItems().execute(
    @Override
    public Completable deleteAll() {
        return cartDao.deleteAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Boolean> deleteItemById(int id) {
        return Single.fromCallable(() -> {
                    cartDao.deleteItemById(id);
                    return true;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Boolean> updateQuantity(int id, int quantity, double price) {
        return Single.fromCallable(() -> {
                    cartDao.updateQuantity(id, quantity, price);
                    return true;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void updatePrice(int id, double price) {
        cartDao.updatePrice(id, price);
    }

    @Override
    public Single<Double> getTotalCartPrice() {
        return Single.fromCallable(cartDao::getTotalCartPrice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Boolean> getRestaurantIdOfFirstItem(String restaurantId) {
        return cartDao.getRestaurantId(restaurantId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Integer> getCartItemsCount() {
        return cartDao.getCartItemsCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}