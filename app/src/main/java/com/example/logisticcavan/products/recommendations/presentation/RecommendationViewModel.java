package com.example.logisticcavan.products.recommendations.presentation;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.Product;
import com.example.logisticcavan.products.recommendations.domain.GetRecommendationsUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class RecommendationViewModel extends ViewModel {
    private final GetRecommendationsUseCase getRecommendationsUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<MyResult<List<Product>>> products = new MutableLiveData<>();
    @Inject
    public RecommendationViewModel(GetRecommendationsUseCase getRecommendationsUseCase) {
        this.getRecommendationsUseCase = getRecommendationsUseCase;
        getRecommendations();
    }

    public LiveData<MyResult<List<Product>>> getProducts() {
        return products;
    }

    private void getRecommendations() {
        disposable.add(getRecommendationsUseCase.getRecommendations()
                .subscribe(products::postValue));
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
