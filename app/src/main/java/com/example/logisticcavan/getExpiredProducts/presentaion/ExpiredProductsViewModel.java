package com.example.logisticcavan.getExpiredProducts.presentaion;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.caravan.domain.useCase.GetCaravanProductsUseCase;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.updateStatusCaravanProduct.domain.UpdateStatusCaravanProductUseCase;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public
class ExpiredProductsViewModel extends ViewModel {

    private GetCaravanProductsUseCase getCaravanProductsUseCase;
    private UpdateStatusCaravanProductUseCase updateStatusCaravanProductUseCase;

    private MutableLiveData<List<Product>> _expiredProducts = new MutableLiveData<>();
    LiveData<List<Product>> expiredProducts = _expiredProducts;

    private MutableLiveData<List<Product>> _expireSoon = new MutableLiveData<>();
    LiveData<List<Product>> expireSoon = _expireSoon;

    @Inject
    public  ExpiredProductsViewModel(GetCaravanProductsUseCase getCaravanProductsUseCase, UpdateStatusCaravanProductUseCase updateStatusCaravanProductUseCase) {
        this.getCaravanProductsUseCase = getCaravanProductsUseCase;
        this.updateStatusCaravanProductUseCase = updateStatusCaravanProductUseCase;
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
        long expirationDate = product.getExpirationData();
        long currentTime = System.currentTimeMillis();

       if (currentTime >= expirationDate){
           Log.e("TAG" , "itr");
           return  false;
       }


        long oneDayInMillis = 24 * 60 * 60 * 1000;
        long day1 = currentTime + oneDayInMillis ;
        long day2 = currentTime + (oneDayInMillis * 2);
        long day3 = currentTime + (oneDayInMillis * 3);
        long day4 = currentTime + (oneDayInMillis * 4);
        long day5 = currentTime + (oneDayInMillis * 5);

        Log.e("TAG", "currentTime: " + currentTime);
        Log.e("TAG", "day1: " + day1);
        Log.e("TAG", "day2: " + day2);
        Log.e("TAG", "day3: " + day3);
        Log.e("TAG", "day4: " + day4);
        Log.e("TAG", "day5: " + day5);
        long  rangeStart = expirationDate - (5 * oneDayInMillis) ;
        long  rangeEnd = expirationDate - oneDayInMillis ;
 
//        Log.e("TAG", "currentTime: " + stringDate(currentTime));
//        Log.e("TAG", "expirationDate: " + stringDate(expirationDate));
//        Log.e("TAG", "rangeEnd: " + stringDate(rangeEnd));
//        Log.e("TAG", "rangeStart: " + stringDate(rangeStart));

        return currentTime >= rangeStart && currentTime <= rangeEnd;

    }

    private String stringDate(Long timestamp){
        LocalDateTime dateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.format(formatter);
    }


    public void updateProductStatus(String productID, long timeStamp) {
        CompletableFuture<Void> future = updateStatusCaravanProductUseCase.execute(productID,timeStamp);
        future.thenAccept(sd -> {
            getCaravanProducts();
        }).exceptionally( df ->{
            getCaravanProducts();
            return null;
        });
    }
}