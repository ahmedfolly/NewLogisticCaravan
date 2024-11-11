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

    private MutableLiveData<List<Product>> _expiredProducts = new MutableLiveData<List<Product>>();
    LiveData<List<Product>> expiredProducts = _expiredProducts;


    @Inject
    public  ExpiredProductsViewModel(GetCaravanProductsUseCase getCaravanProductsUseCase) {
        this.getCaravanProductsUseCase = getCaravanProductsUseCase;
    }


    public void getCaravanProducts() {
        getCaravanProductsUseCase.execute()

                .thenAccept(products -> {
                   getExpiredProducts(products);
                   getWhatWillExpiredSoon(products);

                })



                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;

                });

    }

    private void getWhatWillExpiredSoon(List<Product> products) {
        List<Product> filteredProducts = products;

    }

    private void getExpiredProducts(List<Product> products) {
        List<Product> filteredProducts = products;
        filteredProducts = filteredProducts.stream().filter( product -> product.getExpirationData() <= System.currentTimeMillis()).collect(Collectors.toList());
        Log.e("TAG", "getExpiredProducts: " + filteredProducts.size());
        _expiredProducts.setValue(filteredProducts);
    }

}