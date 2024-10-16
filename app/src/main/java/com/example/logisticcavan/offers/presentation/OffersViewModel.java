package com.example.logisticcavan.offers.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.offers.domain.usecases.GetOffersUseCase;
import com.example.logisticcavan.offers.domain.Offer;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class OffersViewModel extends ViewModel {
    private final GetOffersUseCase getOffersUseCase;

    @Inject
    public OffersViewModel(GetOffersUseCase getOffersUseCase) {
        this.getOffersUseCase = getOffersUseCase;
    }

    private final MutableLiveData<MyResult<List<Offer>>> _offersLiveData = new MutableLiveData<>();

    public LiveData<MyResult<List<Offer>>> getOffersLiveData() {
        return _offersLiveData;
    }

    private final CompositeDisposable disposable = new CompositeDisposable();

    public void getAllOffers() {
        disposable.add(getOffersUseCase.execute().subscribe(_offersLiveData::postValue, error -> {
            _offersLiveData.postValue(MyResult.error(new Exception("error " + error.getMessage())));
        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
