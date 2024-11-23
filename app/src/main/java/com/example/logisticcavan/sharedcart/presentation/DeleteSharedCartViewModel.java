package com.example.logisticcavan.sharedcart.presentation;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.sharedcart.domain.usecases.DeleteSharedCartUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class DeleteSharedCartViewModel extends ViewModel {
    private final DeleteSharedCartUseCase deleteSharedCartUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    @Inject
    public DeleteSharedCartViewModel(DeleteSharedCartUseCase deleteSharedCartUseCase) {
        this.deleteSharedCartUseCase = deleteSharedCartUseCase;
    }
    public void deleteSharedCart(String sharedProductId,DeleteSharedCartCallback deleteSharedProductCallback){
        disposable.add(deleteSharedCartUseCase.deleteSharedCart(sharedProductId).subscribe(
                result->{
                    if (result)
                        deleteSharedProductCallback.onSuccess("Shared Cart Deleted");
                    else
                        deleteSharedProductCallback.onSuccess("There is a problem in deleting shared cart");
                }
        ));
    }
    public interface DeleteSharedCartCallback{
        void onSuccess(String message);
        void onError(String message);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
