package com.example.producktivity.ui.usage_data;

public class UsageTime implements Comparable<UsageTime>{

    public String appName;
    public long millisUsed;

    public UsageTime(String appName, long millisUsed) {
        this.appName = appName;
        this.millisUsed = millisUsed;
    }


    @Override
    public int compareTo(UsageTime o){
        return -Long.compare(millisUsed, o.millisUsed);
    }

    public String toString() {return appName + ",\t" + millisUsed;}

}