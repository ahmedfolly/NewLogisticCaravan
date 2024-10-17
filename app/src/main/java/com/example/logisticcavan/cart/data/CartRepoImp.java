package com.example.logisticcavan.cart.data;

import static kotlinx.coroutines.flow.FlowKt.observeOn;

import android.annotation.SuppressLint;

import com.example.logisticcavan.cart.domain.models.CartItem;
import com.example.logisticcavan.cart.domain.repos.CartRepo;
import com.example.logisticcavan.common.utils.MyResult;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class CartRepoImp implements CartRepo {
    private final CartDao cartDao;
    private final BehaviorSubject<String> subject = BehaviorSubject.create();

    public CartRepoImp(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    /** @noinspection ResultOfMethodCallIgnored*/
    @SuppressLint("CheckResult")
    @Override
    public void insert(CartItem cartItem) {
         Observable.fromCallable(() -> {
                    cartDao.insert(cartItem);
                    return true;
                })
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
    public void deleteAll() {
        cartDao.deleteAll();
    }

    @Override
    public void deleteItemById(int id) {
        cartDao.deleteItemById(id);
    }

    @Override
    public void updateQuantity(int id, int quantity) {
        cartDao.updateQuantity(id, quantity);
    }

    @Override
    public void updatePrice(int id, double price) {
        cartDao.updatePrice(id, price);
    }

    @Override
    public Observable<MyResult<Double>> getTotalCartPrice() {
        return cartDao.getTotalCartPrice()
                .map(MyResult::success)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io());
    }
}