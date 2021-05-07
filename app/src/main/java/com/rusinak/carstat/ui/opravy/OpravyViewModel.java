package com.rusinak.carstat.ui.opravy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OpravyViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public OpravyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is opravy fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
