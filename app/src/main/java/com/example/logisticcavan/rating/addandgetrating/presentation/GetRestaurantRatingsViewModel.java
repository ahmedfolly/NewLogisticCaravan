package com.example.logisticcavan.rating.addandgetrating.presentation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.rating.addandgetrating.domain.model.Rating;
import com.example.logisticcavan.rating.addandgetrating.domain.usecase.GetRestaurantRatingsUseCase;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class GetRestaurantRatingsViewModel extends ViewModel {
    private final GetRestaurantRatingsUseCase getRestaurantRatingsUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<MyResult<List<Rating>>> restaurantRatingsLiveData = new MutableLiveData<>();

    @Inject
    public GetRestaurantRatingsViewModel(GetRestaurantRatingsUseCase getRestaurantRatingsUseCase) {
        this.getRestaurantRatingsUseCase = getRestaurantRatingsUseCase;
    }
    public void getAllRatings(String restaurantId){
        disposable.add(getRestaurantRatingsUseCase.execute(restaurantId)
                .subscribe(restaurantRatingsLiveData::postValue));
    }

    public MutableLiveData<MyResult<List<Rating>>> getRestaurantRatingsLiveData() {
        return restaurantRatingsLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }

}
