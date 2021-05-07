package com.rusinak.carstat.ui.udrzba;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UdrzbaViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public UdrzbaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is udrzba fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
