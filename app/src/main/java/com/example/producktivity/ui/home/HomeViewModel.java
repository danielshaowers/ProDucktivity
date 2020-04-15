package com.example.producktivity.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> on = new MutableLiveData<>();

    public HomeViewModel(Application app) {
        super(app);
    }


    public LiveData<Boolean> getOn() {return on;}
    public void setOn(boolean on) {this.on.setValue(on);}
}