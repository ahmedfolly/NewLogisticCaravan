package com.example.logisticcavan.sharedcart.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.sharedcart.domain.model.SharedProductWithSharedCart;
import com.example.logisticcavan.sharedcart.domain.usecases.GetSharedProductsUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel

public class GetSharedProductsViewModel extends ViewModel {
    private final GetSharedProductsUseCase getSharedProductsUseCase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<MyResult<List<SharedProductWithSharedCart>>> sharedProductsLiveData = new MutableLiveData<>();
    @Inject
    public GetSharedProductsViewModel(GetSharedProductsUseCase getSharedProductsUseCase) {
        this.getSharedProductsUseCase = getSharedProductsUseCase;
    }
    public LiveData<MyResult<List<SharedProductWithSharedCart>>> getSharedProductsLiveData() {
        return sharedProductsLiveData;
    }
    public void fetchSharedProducts(){
        compositeDisposable.add(getSharedProductsUseCase.getSharedProducts()
                .subscribe(
                        sharedProductsLiveData::postValue
                ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
