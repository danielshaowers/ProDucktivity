package com.example.producktivity.ui.blocking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BlockViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BlockViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Limit Your App Use");
    }

    public LiveData<String> getText() {
        return mText;
    }
}