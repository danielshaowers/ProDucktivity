package com.example.producktivity.ui.usage_data;

public class UsageTime implements Comparable<UsageTime>{
    public String packageName;
    public String appName;
    public long monthUse;
    public long weekUse;
    public long dayUse;
    public int span_flag; //indicates which length of time we're currently looking at
    public static final int MONTH = 2;
    public static final int WEEK = 1;
    public static final int DAY = 0;

    public UsageTime(String appName, long day, long week, long month, String pName, int span_flag) {
        this.appName = appName;
        this.monthUse = month;
        weekUse = week;
        dayUse = day;
        packageName = pName;
        this.span_flag = span_flag;
    }

    public long getTimeOfFlag(){
        if (span_flag == MONTH)
            return monthUse;
        if (span_flag == WEEK)
            return weekUse;
        return dayUse;
    }

    //we want the longer time. is negative correct?
    @Override
    public int compareTo(UsageTime o){
        return -Long.compare(getTimeOfFlag(), o.getTimeOfFlag());
    }

    public String toString() {return appName + ",\t" + getTimeOfFlag();}
    @Override
    public boolean equals(Object o){
        UsageTime obj = (UsageTime) o;
        return appName.equals(obj.appName);
    }

}