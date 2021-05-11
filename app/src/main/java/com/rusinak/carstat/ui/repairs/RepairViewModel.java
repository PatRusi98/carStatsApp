package com.rusinak.carstat.ui.repairs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RepairViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public RepairViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is opravy fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
