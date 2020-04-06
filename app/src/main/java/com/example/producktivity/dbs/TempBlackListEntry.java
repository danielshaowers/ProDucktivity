package com.example.producktivity.dbs;

import java.util.List;

public class TempBlackListEntry {
    private long weekLimit = 0;
    private long dayLimit = 0;
    private String appName;

    public TempBlackListEntry(String name){
        appName = name;
    }
    public TempBlackListEntry(){}
    //converts string in the format hh:mm into milliseconds
    public static long stringToLong(String s){
        StringBuilder sb = new StringBuilder();
        long hours = 0;
        long minutes = 0;
        int i = 0;
        for (; i < s.length() && s.charAt(i) == ':'; i++); //finds the colon
        if (i<s.length() && s.charAt(i) == ':'){
            hours = Long.parseLong(s.substring(0, i));
            minutes = Long.parseLong(s.substring(i+1));
        }
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
    public long getWeekLimit() {
        return weekLimit;
    }

    public void setWeekLimit(long weekLimit) {
        this.weekLimit = weekLimit;
    }

    public long getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(long dayLimit) {
        this.dayLimit = dayLimit;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
