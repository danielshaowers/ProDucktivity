package com.example.producktivity.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Boolean> on;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        on = new MutableLiveData<>();
        on.setValue(false);
        mText.setValue("See what matters most to you");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Boolean> getOn() {return on;}
    public void setOn(boolean on) {this.on.setValue(on);}
}