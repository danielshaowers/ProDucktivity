package com.example.producktivity.ui.usage_data;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

public class DataViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<UsageDataHandler.UsageTime>> data = new MutableLiveData<>(); //app name and time spent
    public Context context;     //TODO, risky


    public DataViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("See how productive you've been");
    }
    public LiveData<String> getText() {
        return mText;
    }
    @RequiresApi(api = Build.VERSION_CODES.N) //sorts by length of use
    public List<UsageDataHandler.UsageTime> sortData(List<UsageDataHandler.UsageTime> a){
       Collections.sort(a);
       return a;
    }

    //note that long is in milliseconds
    public LiveData<List<UsageDataHandler.UsageTime>> getAllData(){return data; }
    //I would like to sort the data
    public void setAllData(List<UsageDataHandler.UsageTime> allData){
        sortData(allData);
        data.setValue(allData);
    }
    public void insert(String app, Long time){
        UsageDataHandler h = new UsageDataHandler(context);
        data.getValue().add(h.new UsageTime(app, time));
    }

}