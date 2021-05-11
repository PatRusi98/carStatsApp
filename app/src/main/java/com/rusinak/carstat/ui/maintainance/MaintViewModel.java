package com.rusinak.carstat.ui.maintainance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MaintViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public MaintViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is udrzba fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
