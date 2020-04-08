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

    @ColumnInfo(name = "app_name")
    private String appName;

    @ColumnInfo(name = "category")
    @TypeConverters({CategoryConverter.class})
    private Category category;

    @ColumnInfo(name = "day_limit")
    private long dayLimit;

    @ColumnInfo(name = "week_limit")
    private long weekLimit;


    @ColumnInfo(name = "unrestricted")
    private boolean unrestricted;

    @ColumnInfo(name = "time_used")
    private long timeUsed;

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

    public boolean isUnrestricted() {return unrestricted;}

    public void setUnrestricted(boolean unrestricted) {this.unrestricted = unrestricted;}

    public long getTimeUsed() {return timeUsed;}

    public void setTimeUsed(long timeUsed) {this.timeUsed = timeUsed;}

    //Converter methods for longs and input strings
    public static long stringToLong(String s){
        long hours = 0;
        long minutes = 0;
        int i = 0;
        for (; i < s.length() && s.charAt(i) <= '9' && s.charAt(i) >= '0'; i++); //finds the colon
        if (i<s.length() && s.charAt(i) <= '9' && s.charAt(i) >= '0'){
            hours = Long.parseLong(s.substring(0, i));
            minutes = Long.parseLong(s.substring(i+1));
        }
        else
            hours = Long.parseLong(s);
        return minutes * 60 * 1000 + hours * 60 * 60 * 1000;
    }

    public static String longToString(long millis) {
        String output = "";
        long minutes = millis / 60000;

        if (minutes > 1440) {
            output += (minutes / 1440) + "d ";
            minutes %= 1440;
        }
        if (minutes > 60) {
            output += (minutes / 60) + "h ";
            minutes %= 60;
        }
        return output + minutes + "m";
    }

}
