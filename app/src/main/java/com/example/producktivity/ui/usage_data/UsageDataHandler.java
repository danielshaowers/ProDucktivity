package com.example.producktivity.ui.usage_data;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsageDataHandler {

    Context context;
    public UsageDataHandler(Context context) {this.context = context;}

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<UsageTime> getStats(int timeFlag) {
        long day = 1000 * 60 * 60 * 24; //milliseconds in a day
        long month = day * 30;
        long week = day * 7;
        UsageStatsManager manager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        List<Map<String, UsageStats>> usageStatsMap = new ArrayList<Map<String, UsageStats>>();
        usageStatsMap.add(manager.queryAndAggregateUsageStats(System.currentTimeMillis() - day, System.currentTimeMillis()));
        usageStatsMap.add(manager.queryAndAggregateUsageStats(System.currentTimeMillis() - week, System.currentTimeMillis()));
        usageStatsMap.add(manager.queryAndAggregateUsageStats(System.currentTimeMillis() - month, System.currentTimeMillis()));
        List<UsageTime> output = new ArrayList<UsageTime>();

        for (String key : usageStatsMap.get(2).keySet()){ //this is assuming all of the maps have the same entries
            /*UsageStats day = usageStatsMap.get(0).get(key);
            UsageStats week = usageStatsMap.get(1).get(key);*/
            UsageStats m = usageStatsMap.get(2).get(key);
            if (m.getTotalTimeInForeground() > 0 && isApp(m)){ //if monthly use is over 1 minute: 60000
                output.add(getUsage(usageStatsMap.get(0).get(key), usageStatsMap.get(1).get(key), m, timeFlag));
            }
        }
         /*   output = usageStatsMap.get(0).values().stream()
                    .filter(v -> v.getTotalTimeInForeground() > 60000 && isApp(v)) //previously  v.getTotalTimeInForeground() > 60000
                    .map(v -> getUsage(v)).collect(Collectors.toList());*/
           /* for (UsageTime a : output)
                System.out.println(a.toString());*/
        return output;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private ApplicationInfo info(UsageStats stats) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai;
        try {ai = pm.getApplicationInfo(stats.getPackageName(), 0);} catch (PackageManager.NameNotFoundException e) {ai = null;}
        return ai;
    }

    public String appName(String packageName) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai;
        try {ai = pm.getApplicationInfo(packageName, 0);} catch (PackageManager.NameNotFoundException e) {ai = null;}
        return ai == null ? "oh gosh oh darn" : pm.getApplicationLabel(ai).toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public UsageTime getUsage(UsageStats day, UsageStats week, UsageStats month, int timeFlag) {
        System.out.println("day " + day + " week " + week + " month " + month);
        long dayTime = day == null ? 0 : day.getTotalTimeInForeground();
        long weekTime = week == null ? 0 : week.getTotalTimeInForeground();
        long monthTime = month == null ? 0 : month.getTotalTimeInForeground();
        return new UsageTime(appName(month.getPackageName()),
                dayTime, weekTime, monthTime,
                month.getPackageName(), timeFlag); //timeFlag sets the primary time to be displayed
    }

    private boolean isApp(UsageStats app) {                     //more can always be added
        List<String> notApps = Arrays.asList("oh gosh oh darn", "Pixel Launcher", "Permission Controller",
                "com.google.android.sdksetup", "Data Transfer Tool", "Android Setup");
        return !notApps.contains(appName(app.getPackageName()));
    }

}
