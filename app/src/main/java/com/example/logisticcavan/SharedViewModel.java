package com.example.logisticcavan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Boolean> ratingSubmitted = new MutableLiveData<>();

    public void submitRating() {
        ratingSubmitted.setValue(true);
    }

    public LiveData<Boolean> isRatingSubmitted() {
        return ratingSubmitted;
    }
}
