package com.example.logisticcavan.restaurants.presentation;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.restaurants.domain.ProductWithRestaurant;
import com.example.logisticcavan.restaurants.domain.Restaurant;

import java.util.List;
import java.util.stream.Collectors;

public class CombinedProductsWithRestaurantsViewModel extends ViewModel {

    private final MediatorLiveData<MyResult<List<ProductWithRestaurant>>> combinedLiveData;

    public CombinedProductsWithRestaurantsViewModel() {
        combinedLiveData = new MediatorLiveData<>();
    }

    public MediatorLiveData<MyResult<List<ProductWithRestaurant>>> getCombinedLiveData() {
        return combinedLiveData;
    }

    public void combineSources(LiveData<MyResult<List<Product>>> productsLiveData, LiveData<MyResult<List<Restaurant>>> restaurantsLiveData) {
        combinedLiveData.addSource(productsLiveData, productsResult -> {
            combineLists(productsResult, restaurantsLiveData.getValue());
        });
        combinedLiveData.addSource(restaurantsLiveData, restaurantsResult -> {
            combineLists(productsLiveData.getValue(), restaurantsResult);
        });
    }

    public void combineLists(MyResult<List<Product>> productResult, MyResult<List<Restaurant>> restaurantResult) {
        Log.d("TAG", "combineLists: "+productResult.toString());
        if (productResult != null &&restaurantResult != null) {
            if (productResult instanceof MyResult.Success && restaurantResult instanceof MyResult.Success){
                List<Product> products = ((MyResult.Success<List<Product>>) productResult).getData();
                for (Product product : products) {
                    Log.d("TAG", "combineLists: " + product.toString());
                }
                List<Restaurant> restaurants = ((MyResult.Success<List<Restaurant>>) restaurantResult).getData();
                for (Restaurant restaurant : restaurants) {
                    Log.d("TAG", "combineLists: " + restaurant.toString());
                }
                List<ProductWithRestaurant> combinedList = products.stream().map(product -> {
                    Restaurant restaurant = restaurants.stream()
                            .filter(r -> r.getRestaurantId().equals(product.getResId()))
                            .findFirst()
                            .orElse(null);
                    return new ProductWithRestaurant(product, restaurant);
                }).collect(Collectors.toList());
                combinedLiveData.setValue(MyResult.success(combinedList));  // Update the MediatorLiveData with the combined result
            }
        }else{
            Log.d("TAG", "combineLists: null values ");
            combinedLiveData.setValue(MyResult.error(new Exception("Data is null")));
        }
    }

}
