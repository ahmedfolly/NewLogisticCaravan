package com.example.logisticcavan.sharedcart.presentation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.model.SharedProduct;
import com.example.logisticcavan.sharedcart.domain.usecases.AddToSharedCartUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class AddToSharedCartViewModel extends ViewModel {
    private final AddToSharedCartUseCase addToSharedCartUseCase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Inject
    public AddToSharedCartViewModel(AddToSharedCartUseCase addToSharedCartUseCase) {
        this.addToSharedCartUseCase = addToSharedCartUseCase;
    }
    public void addToSharedCart( SharedProduct sharedProduct,String restaurantId,String restaurantName, AddToSharedCartCallback callback) {
        compositeDisposable.add(addToSharedCartUseCase.addToSharedCart(sharedProduct,restaurantId,restaurantName).subscribe(
               result->{result.handle(
                       callback::onSuccess,
                       callback::onError,
                       ()->{}
               );},
                callback::onError
        ));
    }
    public interface AddToSharedCartCallback{
        void onSuccess(String result);
        void onError(Throwable throwable);
    }
}
