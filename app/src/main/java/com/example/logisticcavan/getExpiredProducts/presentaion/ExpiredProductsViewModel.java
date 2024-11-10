package com.example.logisticcavan.getExpiredProducts.presentaion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.caravan.domain.useCase.GetCaravanProductsUseCase;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public
class ExpiredProductsViewModel extends ViewModel {
    private GetCaravanProductsUseCase getCaravanProductsUseCase;

    private MutableLiveData<List<Product>> _products = new MutableLiveData<List<Product>>();
    LiveData<List<Product>> products = _products;


    @Inject
    public  ExpiredProductsViewModel(GetCaravanProductsUseCase getCaravanProductsUseCase) {
        this.getCaravanProductsUseCase = getCaravanProductsUseCase;
    }


    public void getCaravanProducts() {
        getCaravanProductsUseCase.execute()
                .thenAccept(products -> _products.setValue(products))
                .exceptionally(throwable -> {
                    throwable.printStackTrace();
                    return null;

                });

    }

}