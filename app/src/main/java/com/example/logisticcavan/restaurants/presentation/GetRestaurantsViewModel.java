package com.example.logisticcavan.restaurants.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.restaurants.domain.GetRestaurantsUseCase;
import com.example.logisticcavan.restaurants.domain.Restaurant;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class GetRestaurantsViewModel extends ViewModel {
    private GetRestaurantsUseCase getRestaurantsUseCase;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<MyResult<List<Restaurant>>> restaurantsLiveData = new MutableLiveData<>();

    public LiveData<MyResult<List<Restaurant>>> getRestaurants() {
        return restaurantsLiveData;
    }
    @Inject
    public GetRestaurantsViewModel(GetRestaurantsUseCase getRestaurantsUseCase) {
        this.getRestaurantsUseCase = getRestaurantsUseCase;
    }

    public void fetchRestaurants() {
        disposable.add(getRestaurantsUseCase.getRestaurants().subscribe(restaurantsLiveData::postValue, error -> {
            restaurantsLiveData.postValue(MyResult.error(new Exception(error.getMessage())));
        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
