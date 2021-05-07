package com.rusinak.carstat.ui.statistiky;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatistikyViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public StatistikyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is statistiky fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
