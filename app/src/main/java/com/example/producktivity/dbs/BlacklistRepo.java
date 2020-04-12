package com.example.producktivity.dbs;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.producktivity.ui.usage_data.UsageTime;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BlacklistRepo {

    private BlacklistDaoAccess dao;
    private LiveData<List<BlacklistEntry>> allEntries;

    public BlacklistRepo(Application app) {
        BlacklistDatabase db = BlacklistDatabase.getDatabase(app);
        dao = db.daoAccess();
        allEntries = dao.getList();
    }

    public LiveData<List<BlacklistEntry>> getAllEntries() {
        return allEntries;
    }

    public LiveData<List<BlacklistEntry>> getEntriesByCategory(Category c) {
        return dao.getListByCategory(c);
    }

    public void insert(BlacklistEntry entry) {
        BlacklistDatabase.databaseWriteExecutor.execute(() ->
                dao.insert(entry));
    }
    //given a list of usage times, it updates a list of blacklist entries by adding any new usage times and changing the usage times of existing ones. requires the list to already exist
    //updates database and the list itself
    //todo: make it notice deletions as well. currently can only update and insert
    public List<BlacklistEntry> updateAllUsages(List<UsageTime> ut, List<BlacklistEntry> allEntries){
        //create a hashmap
        HashMap<String, BlacklistEntry> map = new HashMap<>();
        List<BlacklistEntry> newList = new ArrayList<>();
        for (BlacklistEntry b : allEntries){
            map.put(b.getAppName(), b);
        }
        for (UsageTime time : ut){
            BlacklistEntry entry = map.get(time.appName);
            if (entry == null){
                entry = new BlacklistEntry(time.appName);
                entry = updateEntry(entry, time);
                insert(entry);
                newList.add(entry);
            }
            else{
                entry = updateEntry(entry, time);
                update(entry);
            }
            newList.add(entry);
        }
        return newList;
    }

    public void repopulateDatabase(List<BlacklistEntry> newList){
        for (BlacklistEntry old : Objects.requireNonNull(allEntries.getValue())){
            delete(old);
        }
        for(BlacklistEntry newE: newList) {
            insert(newE);
        }
    }

    public BlacklistEntry updateEntry(BlacklistEntry entry, UsageTime time){
        entry.setSpan_flag(time.span_flag);
        entry.setDayUse(time.dayUse);
        entry.setMonthUse(time.monthUse);
        entry.setWeekUse(time.weekUse);
        entry.setPackageName(time.packageName);
        entry.setAppName(time.appName);
        return entry;
    }

    public LiveData<Long> getDayLimit(String appName) { return dao.getDayLimit(appName);}

    public LiveData<Long> getWeekLimit(String appName) { return dao.getWeekLimit(appName);}

    public void update(BlacklistEntry entry) { dao.update(entry);}

    public void delete(BlacklistEntry entry) { dao.delete(entry);}

}
