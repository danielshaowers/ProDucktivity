package com.example.producktivity;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.producktivity.dbs.BlacklistEntry;
import com.example.producktivity.dbs.BlacklistRepo;
import com.example.producktivity.dbs.Category;

import java.util.List;

public class BlacklistViewModel extends AndroidViewModel {
    private BlacklistRepo repo;
    private final LiveData<List<BlacklistEntry>> mAllEntries;

    public BlacklistViewModel(Application app) {
        super(app);
        repo = new BlacklistRepo(app);
        mAllEntries = repo.getAllEntries();
    }

    public LiveData<List<BlacklistEntry>> getAllEntries() {
        return mAllEntries;
    }

    public LiveData<List<BlacklistEntry>> getEntriesByCategory(Category c) {
        return repo.getEntriesByCategory(c);
    }

    public void insert(BlacklistEntry entry) {repo.insert(entry);}

    public void delete(BlacklistEntry entry) {repo.deleteEntry(entry);}

}
