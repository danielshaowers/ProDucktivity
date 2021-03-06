package com.example.producktivity.ui.blockSelect;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.producktivity.dbs.blacklist.BlacklistDaoAccess;
import com.example.producktivity.dbs.blacklist.BlacklistDatabase;
import com.example.producktivity.dbs.blacklist.BlacklistEntry;

import com.example.producktivity.dbs.blacklist.BlacklistRepo;

import com.example.producktivity.ui.usage_data.UsageTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BlockSelectViewModel extends AndroidViewModel {

    public static final int TIME_SORT = 0;
    public static final int NAME_SORT = 1;
    private LiveData<List<BlacklistEntry>> list;
    private BlacklistRepo repo;
    public BlockSelectViewModel(Application app) {
        super(app);
        list = new MutableLiveData<>();
        repo = new BlacklistRepo(app);
        list = repo.getAllEntries();
       //do I need to initialize the list here? I don't think so
    }
    public BlockSelectViewModel(Application app, BlacklistDaoAccess dao, BlacklistDatabase db){
        super(app);
        this.repo = new BlacklistRepo(db, dao);
        list = repo.getAllEntries();
    }

   /* public void setSelectList(List<BlacklistEntry> entry){
        list.setValue(entry);
    }*/
    //todo: figure out if getSelectList should access the repo or not. I think it should
    public LiveData<List<BlacklistEntry>> getSelectList() {
        return list;
    }

    public void insert(BlacklistEntry entry){ repo.insert(entry);}

    public List<BlacklistEntry> updateList(List<UsageTime> ut, List<BlacklistEntry> blacklistEntries){
        return repo.updateAllUsages(ut, blacklistEntries);
    }
    //completely deletes all entries in the database and replaces them with updates values and any new values. called when the fragment view is lost
    public void replaceDB(List<BlacklistEntry> newList){
        repo.repopulateDatabase(newList);
    }

    public static List<BlacklistEntry> sortData(List<BlacklistEntry> a, int sort_flag){
        if (a != null) {
            if (sort_flag == NAME_SORT) {
                NameCompare nameComp = new NameCompare();
                Collections.sort(a, nameComp);
            } else {
                TimeCompare timeComp = new TimeCompare();
                Collections.sort(a, timeComp);
            }
        }
        return a;
    }
    //meant to create a list from an empty database, given a list of usage times
    //I think this is just a more specific (less effective) version of the previous class
    public List<BlacklistEntry> initializeList(List<UsageTime> usage){
        System.out.println("initializing ");
        List<BlacklistEntry> list = new ArrayList<BlacklistEntry>();
        for (UsageTime u: usage){
            BlacklistEntry next = new BlacklistEntry(u.appName);
            next.setWeekUse(u.weekUse);
            next.setDayUse(u.dayUse);
            next.setMonthUse(u.dayUse);
            next.setSpan_flag(u.span_flag);
            next.setPackageName(u.packageName);
            list.add(next);
           insert(next);
        }

        return list;
    }

    static class TimeCompare implements Comparator<BlacklistEntry>
    {
        public int compare(BlacklistEntry m1, BlacklistEntry m2)
        {
            return -Long.compare(m1.getTimeOfFlag(m1.getSpan_flag()), m2.getTimeOfFlag(m1.getSpan_flag()));
        }
    }

    // Class to compare apps by name
    static class NameCompare implements Comparator<BlacklistEntry>
    {
        public int compare(BlacklistEntry m1, BlacklistEntry m2)
        {
            return m1.getAppName().compareTo(m2.getAppName());
        }
    }
}