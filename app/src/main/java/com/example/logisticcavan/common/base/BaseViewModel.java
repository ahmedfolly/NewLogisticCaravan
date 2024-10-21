package com.example.logisticcavan.common.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseViewModel  extends ViewModel  {


    private MutableLiveData<String> _showErrorMessage = new MutableLiveData<>();
    public LiveData<String> showErrorMessage = _showErrorMessage;

    private MutableLiveData<String> _showSuccessMessage = new MutableLiveData<>();
    public LiveData<String>        showSuccessMessage = _showSuccessMessage;




    protected void setShowErrorMessage(String message) {
        _showErrorMessage.setValue(message);
    }

    protected void setShowSuccessMessage(String message) {
        _showSuccessMessage.setValue(message);
    }


    public void resetShowErrorMessage(){
        _showErrorMessage.setValue(null);
    }

    public void resetShowSuccessMessage(){
        _showSuccessMessage.setValue(null);
    }

}
