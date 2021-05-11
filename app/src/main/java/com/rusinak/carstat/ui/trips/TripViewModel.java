package com.rusinak.carstat.ui.trips;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TripViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TripViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is jazdy fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
