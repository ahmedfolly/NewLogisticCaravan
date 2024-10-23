package com.example.logisticcavan.restaurants.presentation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.restaurants.domain.GetRestaurantProductsUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class GetRestaurantProductsViewModel extends ViewModel {
    private final GetRestaurantProductsUseCase getRestaurantProductsUseCase;

    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<MyResult<List<String>>> productsLiveData = new MutableLiveData<>();

    public MutableLiveData<MyResult<List<String>>> getProductsLiveData() {
        return productsLiveData;
    }

    @Inject
    public GetRestaurantProductsViewModel(GetRestaurantProductsUseCase getRestaurantProductsUseCase) {
        this.getRestaurantProductsUseCase = getRestaurantProductsUseCase;
    }

    public void getRestaurantProducts(String restaurantId) {
        disposable.add(getRestaurantProductsUseCase.execute(restaurantId).subscribe(productsLiveData::setValue));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
