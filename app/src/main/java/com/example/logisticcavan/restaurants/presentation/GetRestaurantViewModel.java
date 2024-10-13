package com.example.logisticcavan.restaurants.presentation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.MyResult;
import com.example.logisticcavan.restaurants.domain.GetRestaurantUseCase;
import com.example.logisticcavan.restaurants.domain.Restaurant;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class GetRestaurantViewModel extends ViewModel {

    private final GetRestaurantUseCase getRestaurantUseCase;
    private final MutableLiveData<MyResult<Restaurant>> _restaurantLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public GetRestaurantViewModel(GetRestaurantUseCase getRestaurantUseCase) {
        this.getRestaurantUseCase = getRestaurantUseCase;
    }

    public MutableLiveData<MyResult<Restaurant>> getRestaurant() {
        return _restaurantLiveData;
    }

    public void fetchRestaurantData(String restaurantId) {
        disposable.add(getRestaurantUseCase.execute(restaurantId).subscribe(_restaurantLiveData::postValue, error -> {
            _restaurantLiveData.postValue(MyResult.error(new Exception(error.getMessage())));
        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
