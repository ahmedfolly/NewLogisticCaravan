package com.example.logisticcavan.restaurants.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.restaurants.domain.GetRestaurantUseCase;
import com.example.logisticcavan.restaurants.domain.GetRestaurantsByIdsUseCase;
import com.example.logisticcavan.restaurants.domain.Restaurant;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class GetRestaurantViewModel extends ViewModel {

    private final GetRestaurantsByIdsUseCase getRestaurantsByIds;
    private final MutableLiveData<MyResult<List<Restaurant>>> _restaurantLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public GetRestaurantViewModel( GetRestaurantsByIdsUseCase getRestaurantsByIds) {
        this.getRestaurantsByIds = getRestaurantsByIds;

    }

    public LiveData<MyResult<List<Restaurant>>> getRestaurant() {
        return _restaurantLiveData;
    }

    public void fetchRestaurantsIds(List<String> restaurantIds) {
        disposable.add(getRestaurantsByIds.execute(restaurantIds).subscribe(_restaurantLiveData::postValue, error -> {
            _restaurantLiveData.postValue(MyResult.error(new Exception(error.getMessage())));
        }));
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}

