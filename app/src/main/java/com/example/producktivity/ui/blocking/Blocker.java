package com.example.producktivity.ui.blocking;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Blocker {

    Context context;

    public Blocker(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void detect() {
        while (true)
            if (System.currentTimeMillis() % 2000 == 0)
                appOnScreen();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void appOnScreen() {
        PackageManager pManager = context.getPackageManager();
        UsageStatsManager usManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        UsageEvents events = usManager.queryEvents(System.currentTimeMillis() - 2500, System.currentTimeMillis());
        System.out.println("testing");
        while (events.hasNextEvent()) {
            UsageEvents.Event event = new UsageEvents.Event();
            events.getNextEvent(event);
            System.out.println(event.getClassName());
            boolean isBlocked;
            try {
                isBlocked = (pManager.getApplicationInfo(event.getPackageName(), 0).flags & ApplicationInfo.FLAG_SYSTEM) == 0
                    && event.getClassName() != "Producktive";
            } catch (Exception e) {
                isBlocked = false;
            }
            if (isBlocked) {

                //TODO your own code of the window and whatnot

            }
        }
    }

    /*public String[] getForegroundPackageNameClassNameByUsageStats() {
        String packageNameByUsageStats = null;
        String classByUsageStats = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager mUsageStatsManager = (UsageStatsManager)getSystemService("usagestats");
            final long INTERVAL = 1000;
            final long end = System.currentTimeMillis();
            final long begin = end - INTERVAL;
            final UsageEvents usageEvents = mUsageStatsManager.queryEvents(begin, end);
            while (usageEvents.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                usageEvents.getNextEvent(event);
                if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    packageNameByUsageStats = event.getPackageName();
                    classByUsageStats = event.getClassName();
                    Log.d(TAG, "packageNameByUsageStats is" + packageNameByUsageStats + ", classByUsageStats is " + classByUsageStats);
                }
            }
        }
        return new String[]{packageNameByUsageStats,classByUsageStats};
    }*/

    /*public void appOnScreen() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                System.out.println("time: " + System.currentTimeMillis());
                System.out.println(activeApps().get(0).processName);
                System.out.println(activeApps().size());
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private List<ActivityManager.RunningAppProcessInfo> activeApps() {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return manager.getRunningAppProcesses();
    }*/








}
