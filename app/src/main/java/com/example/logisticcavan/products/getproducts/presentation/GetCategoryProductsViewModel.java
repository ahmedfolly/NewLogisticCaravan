package com.example.logisticcavan.products.getproducts.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.GetCategoryProductsUseCase;
import com.example.logisticcavan.products.getproducts.domain.Product;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class GetCategoryProductsViewModel extends ViewModel {
    private final GetCategoryProductsUseCase getCategoryProductsUseCase;
    private final MutableLiveData<MyResult<List<Product>>> _categoryProductsLiveData = new MutableLiveData<>();

    public LiveData<MyResult<List<Product>>> getCategoryProductsLiveData() {
        return _categoryProductsLiveData;
    }

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public GetCategoryProductsViewModel(GetCategoryProductsUseCase getCategoryProductsUseCase) {
        this.getCategoryProductsUseCase = getCategoryProductsUseCase;
    }

    public void fetchCategoryProducts(String categoryName) {
        disposable.add(getCategoryProductsUseCase.execute(categoryName)
                .subscribe(_categoryProductsLiveData::postValue, error -> {
                    _categoryProductsLiveData.postValue(MyResult.error(new Exception("get category products error" + error.getMessage())));
                }));
    }

}
