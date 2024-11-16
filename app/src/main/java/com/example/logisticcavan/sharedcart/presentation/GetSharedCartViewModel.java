package com.example.logisticcavan.sharedcart.presentation;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.sharedcart.domain.model.SharedCart;
import com.example.logisticcavan.sharedcart.domain.usecases.GetSharedCartUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class GetSharedCartViewModel extends ViewModel {
    private final GetSharedCartUseCase getSharedCartUseCase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public GetSharedCartViewModel(GetSharedCartUseCase getSharedCartUseCase) {
        this.getSharedCartUseCase = getSharedCartUseCase;
    }
    public void getSharedCart(SharedCartCallback sharedCartCallback){
        compositeDisposable.add(getSharedCartUseCase.getSharedCart().subscribe(
                sharedCartCallback::onSuccess,
                error-> sharedCartCallback.onError(error.getMessage())
        ));
    }
    public interface SharedCartCallback{
        void onSuccess(SharedCart sharedCart);
        void onError(String errorMessage);
    }
}
