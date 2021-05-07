package com.rusinak.carstat.ui.nastavenia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NastaveniaViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public NastaveniaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is nastavenia fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
