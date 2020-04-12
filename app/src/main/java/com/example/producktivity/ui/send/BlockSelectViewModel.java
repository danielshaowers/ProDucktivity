package com.example.producktivity.ui.send;

import android.app.Activity;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.producktivity.R;
import com.example.producktivity.dbs.BlacklistEntry;
import com.example.producktivity.dbs.BlacklistRepo;
import com.example.producktivity.ui.usage_data.UsageDataHandler;
import com.example.producktivity.ui.usage_data.UsageTime;

import java.util.ArrayList;
import java.util.List;

public class BlockSelectViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<BlacklistEntry>> list;
    private BlacklistRepo repo;
    public BlockSelectViewModel(Application app) {
        super(app);
        list = new MutableLiveData<>();
        repo = new BlacklistRepo(app);
       //do I need to initialize the list here? I don't think so
    }
    public void setSelectList(List<BlacklistEntry> entry){
        list.setValue(entry);
    }
    //
    public LiveData<List<BlacklistEntry>> getSelectList() {
        return list;
    }

    public List<BlacklistEntry> updateList(List<UsageTime> ut, List<BlacklistEntry> blacklistEntries){
        return repo.updateAllUsages(ut, blacklistEntries);
    }
    //completely deletes all entries in the database and replaces them with updates values and any new values. called when the fragment view is lost
    public void replaceDB(List<BlacklistEntry> newList){
        repo.repopulateDatabase(newList);
    }
    //meant to create a list from an empty database, given a list of usage times
    public List<BlacklistEntry> initializeList(List<UsageTime> usage){
        List<BlacklistEntry> list = new ArrayList<BlacklistEntry>();
        for (UsageTime u: usage){
            BlacklistEntry next = new BlacklistEntry(u.appName);
            next.setWeekUse(u.weekUse);
            next.setDayUse(u.dayUse);
            next.setMonthUse(u.dayUse);
            next.setSpan_flag(u.span_flag);
            next.setPackageName(u.packageName);
            list.add(next);

        }

        return list;
    }
}