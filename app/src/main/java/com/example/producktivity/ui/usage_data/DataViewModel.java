package com.example.producktivity.ui.usage_data;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;
//honestly this class shouldn't exist. it should just draw from the blacklist database and work with blacklist entries.
public class DataViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<UsageTime>> data = new MutableLiveData<>(); //app name and time spent


    public DataViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("See how productive you've been");
    }
    public LiveData<String> getText() {
        return mText;
    }
    @RequiresApi(api = Build.VERSION_CODES.N) //sorts by length of use

    public List<UsageTime> sortData(List<UsageTime> a){
       Collections.sort(a);
       return a;
    }

    //note that long is in milliseconds
    public LiveData<List<UsageTime>> getAllData(){return data; }
    //I would like to sort the data
    public void setAllData(List<UsageTime> allData){
        sortData(allData);
        data.setValue(allData);
    }
    public void insert(String app, Long day, Long week, Long month, String packageName, int flag){
        data.getValue().add(new UsageTime(app, day, week, month, packageName, flag));
    }

}