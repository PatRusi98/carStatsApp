package com.rusinak.carstat.ui.stk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class STKViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public STKViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is stk fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
