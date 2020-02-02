package com.example.producktivity.ui.usage_data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DataViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DataViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("See how productive you've been");
    }
    public LiveData<String> getText() {
        return mText;
    }
}