package com.example.producktivity.ui.blocking;

import android.content.Context;

public class Blocker {

    Context context;

    public Blocker(Context context) {
        this.context = context;
    }

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
