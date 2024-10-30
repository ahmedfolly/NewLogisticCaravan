package com.example.logisticcavan.rating.addandgetrating.presentation;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.rating.addandgetrating.domain.model.Rating;
import com.example.logisticcavan.rating.addandgetrating.domain.usecase.AddRateUseCase;
import com.example.logisticcavan.rating.addandgetrating.domain.usecase.UpdateRateVarsUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class AddRatingViewModel extends ViewModel {
    private final AddRateUseCase addRateUseCase;
    private final UpdateRateVarsUseCase updateRateVarsUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public AddRatingViewModel(AddRateUseCase addRateUseCase,
                              UpdateRateVarsUseCase updateRateVarsUseCase) {
        this.addRateUseCase = addRateUseCase;
        this.updateRateVarsUseCase = updateRateVarsUseCase;
    }

    public void addRate(String restaurantId,Rating rating, AddRateListener rateListener) {
        disposable.add(addRateUseCase.addRate(restaurantId,rating).subscribe(rateListener::onSuccess, rateListener::onError));
    }

    public void updateRateVars(String restaurantId, int totalStars) {
        updateRateVarsUseCase.updateRateVars(restaurantId, totalStars);
    }

    public interface AddRateListener {
        void onSuccess(MyResult<Boolean> result);

        void onError(Throwable e);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
