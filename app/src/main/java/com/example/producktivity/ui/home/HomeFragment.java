package com.example.producktivity.ui.home;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.producktivity.R;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.block_apps, container, false);
        Switch sw = root.findViewById(R.id.activate_blocker);
        homeViewModel.setOn(sw.isChecked());
        /*homeViewModel.getOn().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean s) {
                homeViewModel.setOn(s);
            }
        });*/
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                homeViewModel.setOn(true); //not how it's supposed to be done, but whtv
                if (isChecked) {

                    block();

                    /*Intent startBlock = new Intent(HomeFragment.this.getActivity(), BlockActivity.class);
                    startBlock.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(startBlock);*/


                    /*
                    //send to blockeractivity
                    ActivityManager mActivityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
                    List<ActivityManager.RunningAppProcessInfo> RunningTask = mActivityManager.getRunningAppProcesses();
                    // Get the info we need for comparison.
                    ComponentName componentInfo = RunningTask.get(0).importanceReasonComponent;
                    if (componentInfo == null || !componentInfo.getPackageName().equals(getActivity().getPackageName())) {
                        Intent startBlock = new Intent(HomeFragment.this.getActivity(), BlockActivity.class);
                        startBlock.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getActivity().startActivity(startBlock);
                    }*/
                }
            }
        });
        return root;
    }

    public void block() {
        Thread thread = new Thread(new HomeFragment.BlockerRunnable());
        thread.start();
    }

    public class BlockerRunnable implements Runnable {

        PackageManager pManager = HomeFragment.this.getContext().getPackageManager();
        UsageStatsManager usManager = (UsageStatsManager) HomeFragment.this.getContext().getSystemService(Context.USAGE_STATS_SERVICE);

        List<String> whiteList = Arrays.asList("Pixel Launcher", "ProDucktive");

        public BlockerRunnable() {

        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

        @Override
        public void run() {
            while (true)
                if (System.currentTimeMillis() % 1000 == 0)
                    appOnScreen();
        /*System.out.println("hey this is working");
        return Service.START_NOT_STICKY;*/
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void appOnScreen() {
            UsageEvents events = usManager.queryEvents(System.currentTimeMillis() - 1000, System.currentTimeMillis());
            while (events.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                events.getNextEvent(event);

                if (event.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {

                    if (!whiteList.contains(appName(event.getPackageName())))
                        System.out.println(appName(event.getPackageName()));


                    /*boolean isBlocked;
                    try {
                        isBlocked = (pManager.getApplicationInfo(event.getPackageName(), 0).flags & ApplicationInfo.FLAG_SYSTEM) == 0
                                && event.getClassName() != "ProDucktive";
                    } catch (Exception e) {
                        isBlocked = false;
                    }
                    if (isBlocked) {
                        System.out.println("hey that's not allowed");
                        //TODO your own code of the window and whatnot

                    }*/
                }
            }
        }

        public String appName(String packageName) {
            PackageManager pm = HomeFragment.this.getContext().getPackageManager();
            ApplicationInfo ai;
            try {ai = pm.getApplicationInfo(packageName, 0);} catch (PackageManager.NameNotFoundException e) {ai = null;}
            return ai == null ? "oh gosh oh darn" : pm.getApplicationLabel(ai).toString();
        }


        public void showBlockScreen(){
            Intent startBlock = new Intent("com.example.producktivity.ui.blocking.BlockActivity");
            startBlock.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            HomeFragment.this.getContext().startActivity(startBlock);

        }
    }
}
