package com.example.producktivity.dbs;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

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
}
