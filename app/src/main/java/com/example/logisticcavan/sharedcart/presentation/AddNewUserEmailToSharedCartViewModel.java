package com.example.logisticcavan.sharedcart.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.logisticcavan.common.utils.MyResult;
import com.example.logisticcavan.sharedcart.domain.usecases.AddUserToSharedCartUseCase;

import javax.inject.Inject;

import dagger.assisted.AssistedInject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class AddNewUserEmailToSharedCartViewModel extends ViewModel {
    private final AddUserToSharedCartUseCase addUserToSharedCartUseCase;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public AddNewUserEmailToSharedCartViewModel(AddUserToSharedCartUseCase addUserToSharedCartUseCase) {
        this.addUserToSharedCartUseCase = addUserToSharedCartUseCase;
    }

    public void addUserToSharedCart(String userEmail, AddUserEmailListener listener) {
        compositeDisposable.add(
                addUserToSharedCartUseCase.addUserToSharedCart(userEmail).subscribe(
                        listener::addUserEmailSuccess
                ));
    }

    public interface AddUserEmailListener {
        void addUserEmailSuccess(String result);
    }
}
