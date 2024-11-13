package com.example.logisticcavan.getExpiredProducts.presentaion;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.caravan.domain.useCase.GetCaravanProductsUseCase;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public
class ExpiredProductsViewModel extends ViewModel {
    private GetCaravanProductsUseCase getCaravanProductsUseCase;

    private MutableLiveData<List<Product>> _expiredProducts = new MutableLiveData<>();
    LiveData<List<Product>> expiredProducts = _expiredProducts;

    private MutableLiveData<List<Product>> _expireSoon = new MutableLiveData<>();
    LiveData<List<Product>> expireSoon = _expireSoon;

    @Inject
    public  ExpiredProductsViewModel(GetCaravanProductsUseCase getCaravanProductsUseCase) {
        this.getCaravanProductsUseCase = getCaravanProductsUseCase;
    }


    public void getCaravanProducts() {
        getCaravanProductsUseCase.execute()

                .thenAccept(products -> {
                   getExpiredProducts(products);
                   getWhatWillExpireSoon(products);

                })



                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;

                });

    }

    private void getWhatWillExpireSoon(List<Product> products) {
        Log.e("TAG", "getWhatWillExpireSoon: ");
        List<Product> filteredProducts = products;
        filteredProducts = filteredProducts.stream().filter(this::willExpireSoon).collect(Collectors.toList());
        Log.e("TAG", "getWhatWillExpireSoon: " + filteredProducts.size());
        _expireSoon.setValue(filteredProducts);
    }

    private void getExpiredProducts(List<Product> products) {
        Log.e("TAG", "getExpiredProducts: ");

        List<Product> filteredProducts = products;
        filteredProducts = filteredProducts.stream().filter( product -> product.getExpirationData() <= System.currentTimeMillis()).collect(Collectors.toList());
        Log.e("TAG", "getExpiredProducts: " + filteredProducts.size());
        _expiredProducts.setValue(filteredProducts);
    }

    private boolean willExpireSoon(Product product) {
        long currentTime = System.currentTimeMillis();
        long oneDayInMillis = 24 * 60 * 60 * 1000;

        long afterTomorr = currentTime + (oneDayInMillis * 2);

        long fivee = currentTime + (oneDayInMillis * 5);
        Log.e("TAG", "currentTime: " + currentTime);
        Log.e("TAG", "afterTomorr: " + afterTomorr);
        Log.e("TAG", "fivee: " + fivee);
        Log.e("TAG", "oneDayInMillis: " + oneDayInMillis);
        long expirationDate = product.getExpirationData();

        long rangeStart = expirationDate - oneDayInMillis ;
//        Log.e("TAG", "rangeStart: " + rangeStart);

        long rangeEnd = expirationDate - (5 * oneDayInMillis) ;
//        Log.e("TAG", "rangeEnd: " + rangeEnd);

        return currentTime >= rangeStart && currentTime <= rangeEnd;

    }

}