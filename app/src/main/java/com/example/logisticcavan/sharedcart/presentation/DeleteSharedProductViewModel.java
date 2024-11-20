package com.example.logisticcavan.sharedcart.presentation;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.sharedcart.domain.usecases.DeleteSharedCartProductUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class DeleteSharedProductViewModel extends ViewModel {
    private final DeleteSharedCartProductUseCase deleteSharedCartProductUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    @Inject
    DeleteSharedProductViewModel(DeleteSharedCartProductUseCase deleteSharedCartProductUseCase){
        this.deleteSharedCartProductUseCase = deleteSharedCartProductUseCase;
    }

    public void deleteSharedCartProduct(String productId,DeleteSharedProductCallback deleteSharedProductCallback){
        disposable.add(deleteSharedCartProductUseCase.deleteSharedProduct(productId).subscribe(
                deleteSharedProductCallback::onSuccess
        ));
    }
    public interface DeleteSharedProductCallback{
        void onSuccess(String message);
        void onError(String message);
    }
}
