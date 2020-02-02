package com.example.producktivity.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BlockViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BlockViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Limit Your App use");
    }

    public LiveData<String> getText() {
        return mText;
    }
}