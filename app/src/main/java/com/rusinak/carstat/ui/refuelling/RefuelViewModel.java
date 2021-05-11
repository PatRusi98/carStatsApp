package com.rusinak.carstat.ui.refuelling;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RefuelViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public RefuelViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tankovanie fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
