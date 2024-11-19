package com.example.logisticcavan.orders.addorder.presentation;

import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.orders.addorder.domain.AddOrderIdToUserUseCase;
import com.example.logisticcavan.orders.addorder.domain.AddOrderUseCase;
import com.example.logisticcavan.orders.getOrders.domain.Order;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class AddOrderViewModel extends ViewModel {

    private final AddOrderUseCase addOrderUseCase;
    private final AddOrderIdToUserUseCase addOrderIdToUserUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();

    @Inject
    public AddOrderViewModel(AddOrderUseCase addOrderUseCase,AddOrderIdToUserUseCase addOrderIdToUserUseCase) {
        this.addOrderUseCase = addOrderUseCase;
        this.addOrderIdToUserUseCase = addOrderIdToUserUseCase;
    }

    public void addOrder(Order order, UploadOrderCallback callback) {
        disposable.add(addOrderUseCase.execute(order).subscribe(result -> result.handle(callback::onSuccess, callback::onError, callback::onLoading)));
    }
    public void addOrderIdToUser(String orderId,List<String> userEmails, UploadOrderToUser callback) {
        disposable.add(addOrderIdToUserUseCase.execute(orderId,userEmails).subscribe(
                callback::onSuccess,
                error->callback.onError(error.getMessage())
        ));
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
    public interface UploadOrderToUser{
        void onSuccess(String message);
        void onError(String message);
    }
    public interface UploadOrderCallback {
        void onSuccess(String orderId);

        void onError(Exception e);

        void onLoading();
    }
}


