package com.example.producktivity.ui.usage_data;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Map;

public class UsageDataHandler {

    Context context;

    public UsageDataHandler(Context context) {this.context = context;}


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void getStats() {

        UsageStatsManager manager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        System.out.println("handling");
        Map<String, UsageStats> usageStatsMap = manager.queryAndAggregateUsageStats(System.currentTimeMillis(), System.currentTimeMillis() - 1000000);
        System.out.println(usageStatsMap.size());
        for (Map.Entry<String, UsageStats> e : usageStatsMap.entrySet()) {
            System.out.println(e);
        }
    }

}
