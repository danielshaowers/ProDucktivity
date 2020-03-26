package com.example.producktivity.ui.usage_data;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsageDataHandler {

    Context context;

    public UsageDataHandler(Context context) {this.context = context;}

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<UsageTime> getStats() {

        UsageStatsManager manager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        System.out.println("handling"); //one month in the past
        Map<String, UsageStats> usageStatsMap = manager.queryAndAggregateUsageStats(System.currentTimeMillis() - 2628000000L, System.currentTimeMillis());
        System.out.println(usageStatsMap.size());
        List<UsageTime> output = usageStatsMap.values().stream()
                .filter(v -> v.getTotalTimeInForeground() > 60000 && isApp(v))
                .map(v -> getUsage(v)).collect(Collectors.toList());
        for (UsageTime a:output)
            System.out.println(a.toString());
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
    public UsageTime getUsage(UsageStats data) {
        return new UsageTime(appName(data.getPackageName()), data.getTotalTimeInForeground());
    }

    private boolean isApp(UsageStats app) {                     //more can always be added
        List<String> notApps = Arrays.asList("oh gosh oh darn", "Pixel Launcher", "Permission Controller",
                "com.google.android.sdksetup", "Data Transfer Tool", "Android Setup");
        return !notApps.contains(appName(app.getPackageName()));
    }

}
