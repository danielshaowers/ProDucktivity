package com.example.producktivity.ui.usage_data;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UsageDataHandler {

    class UsageTime {

        public String appName;
        public long millisUsed;

        public UsageTime(String appName, long millisUsed) {
            this.appName = appName;
            this.millisUsed = millisUsed;
        }

        public String toString() {return appName + ",\t" + millisUsed;}

    }

    Context context;

    public UsageDataHandler(Context context) {this.context = context;}


    @RequiresApi(api = 24)
    public void getStats() {

        UsageStatsManager manager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        System.out.println("handling");
        Map<String, UsageStats> usageStatsMap = manager.queryAndAggregateUsageStats(System.currentTimeMillis() - 2628000000L, System.currentTimeMillis());
        System.out.println(usageStatsMap.size());
        List<UsageTime> output = usageStatsMap.values().stream()
                .filter(v -> v.getTotalTimeInForeground() > 0 && (v.getPackageName().contains("producktivity") || v.getPackageName().contains("app")))
                .map(v -> getUsage(v)).collect(Collectors.toList());
        for (UsageTime t : output)
            System.out.println(t);
    }

    private String packageName(String packageName) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai;
        try {ai = pm.getApplicationInfo(packageName, 0);} catch (PackageManager.NameNotFoundException e) {ai = null;}
        return ai == null ? "oh gosh oh darn" : pm.getApplicationLabel(ai).toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private UsageTime getUsage(UsageStats data) {
        return new UsageTime(packageName(data.getPackageName()), data.getTotalTimeInForeground());
    }

}
