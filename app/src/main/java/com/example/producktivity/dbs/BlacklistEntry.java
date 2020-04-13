package com.example.producktivity.dbs;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.producktivity.ui.usage_data.UsageTime;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "blacklist")
public class BlacklistEntry implements Serializable, Comparable<BlacklistEntry> {

    public BlacklistEntry(String appName) {this.appName = appName; this.category = Category.BEAUTY;}
    //todo: remove the default category!!

   // @PrimaryKey(autoGenerate = true)
    //private int id;
    @PrimaryKey @NonNull
    @ColumnInfo(name = "app_name")
    private String appName;

    @ColumnInfo(name = "package_name")
    private String packageName;

    @ColumnInfo(name = "month_use")
    private long monthUse;

    @ColumnInfo(name = "week_use")
    private long weekUse;

    @ColumnInfo(name = "day_use")
    private long dayUse;

    @ColumnInfo(name = "span_flag")
    private int span_flag; //indicates which length of time we're currently looking at

    public static final int MONTH = 2;
    public static final int WEEK = 1;
    public static final int DAY = 0;

    @ColumnInfo(name = "category")
    @TypeConverters({CategoryConverter.class})
    private Category category;

    @ColumnInfo(name = "day_limit")
    private long dayLimit;

    @ColumnInfo(name = "week_limit")
    private long weekLimit;

    @ColumnInfo(name = "unrestricted")
    private boolean unrestricted;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    //public int getId(){return id;}
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getMonthUse() {
        return monthUse;
    }

    public void setMonthUse(long monthUse) {
        this.monthUse = monthUse;
    }

    public long getWeekUse() {
        return weekUse;
    }

    public void setWeekUse(long weekUse) {
        this.weekUse = weekUse;
    }

    public long getDayUse() {
        return dayUse;
    }

    public void setDayUse(long dayUse) {
        this.dayUse = dayUse;
    }

    public int getSpan_flag() {
        return span_flag;
    }
    //public void setId(int id){this.id = id;}

    public void setSpan_flag(int span_flag) {
        this.span_flag = span_flag;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(long dayLimit) {
        this.dayLimit = dayLimit;
    }

    public long getWeekLimit() {
        return weekLimit;
    }

    public void setWeekLimit(long weekLimit) {
        this.weekLimit = weekLimit;
    }

    public boolean isUnrestricted() {
        return unrestricted;
    }

    public void setUnrestricted(boolean unrestricted) {
        this.unrestricted = unrestricted;
    }

    public long getTimeOfFlag(int flag){
        if (flag == MONTH)
            return monthUse;
        if (flag == WEEK)
            return weekUse;
        return dayUse;
    }

    //Converter methods for longs and input strings
    public static long stringToLong(String s){
        long hours = 0;
        long minutes = 0;
        if (s.length() > 0) {
            ArrayList<Integer> nonchars = new ArrayList<>();
            ArrayList<Long> time = new ArrayList<>();
            nonchars.add(-1); //we have one at the start and end as base cases

            for (int i = 0; i < s.length(); i++){
                if ((s.charAt(i) > '9' || s.charAt(i) < '0')) {
                    nonchars.add(i);
                }
            } //now we have an array of indices for non characters, find substrings using these as bounds
            nonchars.add(s.length());
            for (int i = nonchars.size() - 1; i > 0; i--){
                if (nonchars.get(i) - nonchars.get(i - 1) > 1) //if the nonnumericals are not adjacent, then must have a num between
                time.add(Long.parseLong(s.substring(nonchars.get(i - 1) + 1, nonchars.get(i))));
            }
            return  time.size() <= 0 ? 0:time.get(0) * 60 * 1000 + (time.size() <= 1 ? 0:time.get(1) * 3600000
                    + (time.size() >= 3 ? time.get(3) * 3600000 * 24:0));
        }
            return 0;
    }
    //todo: fix this so it actually works with our string parser stringToLong
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


    @Override
    public int compareTo(BlacklistEntry o) {
        return Long.compare(getTimeOfFlag(this.span_flag), o.getTimeOfFlag(this.span_flag));
    }

    @Override
    public String toString() {return appName + ",\t" + getTimeOfFlag(this.span_flag);}
}
//need a method to update the select fragment