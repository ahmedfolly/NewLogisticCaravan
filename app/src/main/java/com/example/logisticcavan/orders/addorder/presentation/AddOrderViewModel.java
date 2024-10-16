package com.example.logisticcavan.orders.addorder.presentation;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.orders.addorder.domain.AddOrderUseCase;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class AddOrderViewModel extends ViewModel {

    private final AddOrderUseCase addOrderUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public AddOrderViewModel(AddOrderUseCase addOrderUseCase) {
        this.addOrderUseCase = addOrderUseCase;
    }

    public void addOrder(Order order, UploadOrderCallback callback) {
        disposable.add(addOrderUseCase.execute(order).subscribe(result -> result.handle(callback::onSuccess, callback::onError, callback::onLoading)));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
    public interface UploadOrderCallback {
        void onSuccess(String message);

        void onError(Exception e);

        void onLoading();
    }
}


