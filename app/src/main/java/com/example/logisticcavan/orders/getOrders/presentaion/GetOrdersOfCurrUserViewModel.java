package com.example.logisticcavan.orders.getOrders.presentaion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.orders.getOrders.domain.GetOrdersIdsUseCase;
import com.example.logisticcavan.orders.getOrders.domain.GetOrdersOfCurrUserUseCase;
import com.example.logisticcavan.orders.getOrders.domain.Order;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class GetOrdersOfCurrUserViewModel extends ViewModel {
    private final GetOrdersOfCurrUserUseCase getOrdersOfCurrUserUseCase;
    private final GetOrdersIdsUseCase getOrdersIdsUseCase;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<MyResult<List<Order>>> ordersLiveData = new MutableLiveData<>();

    @Inject
    public GetOrdersOfCurrUserViewModel(GetOrdersOfCurrUserUseCase getOrdersOfCurrUserUseCase,
                                        GetOrdersIdsUseCase getOrdersIdsUseCase) {
        this.getOrdersOfCurrUserUseCase = getOrdersOfCurrUserUseCase;
        this.getOrdersIdsUseCase = getOrdersIdsUseCase;
    }

    public LiveData<MyResult<List<Order>>> getOrdersLiveData() {
        return ordersLiveData;
    }

    public void fetchOrders() {
        disposable.add(getOrdersIdsUseCase.getOrdersIds()
                .flatMap(getOrdersOfCurrUserUseCase::ordersOfCurrUser)
                .subscribe(ordersLiveData::postValue, throwable -> {
                    // Handle error here
                })
        );
}

@Override
protected void onCleared() {
    super.onCleared();
    disposable.clear();
}
}
