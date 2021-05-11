package com.rusinak.carstat.ui.inspection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InspectViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public InspectViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is stk fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
