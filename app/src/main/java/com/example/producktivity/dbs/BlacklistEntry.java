package com.example.producktivity.dbs;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;

@Entity(tableName = "blacklist")
public class BlacklistEntry implements Serializable {

    public BlacklistEntry(String appName) {this.appName = appName;}

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "app_ame")
    private String appName;

    @ColumnInfo(name = "category")
    @TypeConverters({CategoryConverter.class})
    private Category category;

    @ColumnInfo(name = "day_limit")
    private long dayLimit;

    @ColumnInfo(name = "week_limit")
    private long weekLimit;

    public void setId(int id) {this.id = id;}

    public void setName(String appName) {
        this.appName = appName;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId() {return id;}

    public String getAppName() {return appName;}

    public Category getCategory() {return category;}

    public long getDayLimit() {return dayLimit;}

    public void setDayLimit(long dayLimit) {this.dayLimit = dayLimit;}

    public long getWeekLimit() {return weekLimit;}

    public void setWeekLimit(long weekLimit) {this.weekLimit = weekLimit;}
}
