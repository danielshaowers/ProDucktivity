package com.example.producktivity.dbs;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface BlacklistDaoAccess {

    @Query("SELECT * FROM blacklist GROUP BY category")
    LiveData<List<BlacklistEntry>> getList();

    @Query("SELECT * FROM blacklist WHERE category = :category")
    LiveData<List<BlacklistEntry>> getListByCategory(Category category);

    @Insert
    void insert(BlacklistEntry entry);

    @Update
    void update(BlacklistEntry entry);

    @Delete
    void delete(BlacklistEntry entry);
}
