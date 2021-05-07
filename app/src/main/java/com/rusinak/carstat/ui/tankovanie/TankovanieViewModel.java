package com.rusinak.carstat.ui.tankovanie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TankovanieViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TankovanieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tankovanie fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
