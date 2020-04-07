package com.example.producktivity.dbs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.time.Duration;
import java.util.List;
@Dao
public interface BlacklistDaoAccess {

    @Query("SELECT * FROM blacklist GROUP BY category")
    LiveData<List<BlacklistEntry>> getList();

    @Query("SELECT * FROM blacklist WHERE category = :category")
    @TypeConverters({CategoryConverter.class})
    LiveData<List<BlacklistEntry>> getListByCategory(Category category);

    @Query("SELECT day_limit FROM blacklist WHERE app_name = :appName")
    LiveData<Long> getDayLimit(String appName);

    @Query("SELECT week_limit FROM blacklist WHERE app_name = :appName")
    LiveData<Long> getWeekLimit(String appName);

    @Insert
    void insert(BlacklistEntry entry);

    @Update
    void update(BlacklistEntry entry);

    @Delete
    void delete(BlacklistEntry entry);
}
