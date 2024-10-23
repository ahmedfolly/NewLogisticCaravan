package com.example.logisticcavan.products.getproducts.presentation;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.products.getproducts.domain.GetProductsUseCase;
import com.example.logisticcavan.products.getproducts.domain.Product;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;



@HiltViewModel
public class GetProductsViewModel extends ViewModel {

    private final GetProductsUseCase getProductsUseCase;
    private final MutableLiveData<MyResult<List<Product>>> _productsLiveData = new MutableLiveData<>();
    public LiveData<MyResult<List<Product>>> getProductsLiveData() {

       return _productsLiveData;

    }

    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public GetProductsViewModel(GetProductsUseCase getProductsUseCase) {

        this.getProductsUseCase = getProductsUseCase;
    }

    public void fetchProducts(List<String> productsIds) {
        disposable.add(getProductsUseCase.execute(productsIds).subscribe(_productsLiveData::postValue, error -> {
            _productsLiveData.postValue(MyResult.error(new Exception("error" + error.getMessage())));
        }));

    }

    @Override
    protected void onCleared() {


        super.onCleared();
        disposable.clear();


    }

}
