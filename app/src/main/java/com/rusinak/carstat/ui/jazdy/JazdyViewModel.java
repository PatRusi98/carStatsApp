package com.rusinak.carstat.ui.jazdy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JazdyViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public JazdyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is jazdy fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
