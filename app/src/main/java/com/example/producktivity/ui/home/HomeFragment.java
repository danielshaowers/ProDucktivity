package com.example.producktivity.ui.home;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.producktivity.R;
import com.example.producktivity.dbs.blacklist.BlacklistEntry;
import com.example.producktivity.ui.blocking.BlockActivity;
import com.example.producktivity.ui.send.BlockSelectViewModel;

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
        //List<String> whiteList = Arrays.asList("Pixel Launcher", "ProDucktive");
        BlockSelectViewModel bsvm = ViewModelProviders.of(getActivity()).get(BlockSelectViewModel.class);
        List<BlacklistEntry> blE;
        public BlockerRunnable() {
            bsvm.getSelectList().observe(getViewLifecycleOwner(), new Observer<List<BlacklistEntry>>() { //just obtains the list but I don't think we need to make any changes
                @Override
                public void onChanged(@Nullable List<BlacklistEntry> s) {
                    blE = s;
                    bsvm.getSelectList().removeObserver(this);
                }
            });
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

                //this long boi decides whether we should block or not. If we should, then it shows the block screen
                if (event.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) { //resumed indicates an activity made it to the foreground
                    boolean found = false;
                    int i = 0;
                    String name = appName(event.getPackageName());
                    System.out.println("app in foreground is " + name);
                    if (!name.equals("ProDucktive") && !name.equals("Huawei Home")){
                    for (; !found && i < blE.size(); i++)  //finds the app in the list
                        found = blE.get(i).getAppName().equals(name);
                    BlacklistEntry entry = found ? blE.get(i - 1) : null;
                    if (entry != null && !entry.isUnrestricted()) {
                        boolean productivityMode = true; //check if productivity mode is active. if it is, then check if the app is marked as productive by the database
                        boolean productive = entry.isInferredProductive();
                        if (productivityMode && !productive) { // if productivity mode is activated and the app was marked as unproductive, then block!
                            showBlockScreen();
                        } else { //we need to check if we passed the time limit
                            //how do we check the time used right here?
                            if (entry.getDayLimit() < entry.getDayUse() || entry.getWeekLimit() < entry.getWeekUse()) { //if exceeded max allowable time
                                System.out.println("we are trying to block " + name);
                                showBlockScreen();
                            } else { //updates the usages if it's not already larger based on the database.
                                //todo: I'm pretty sure this is super slow...might now want to do it
                                long day = 1000 * 60 * 60 * 24;
                                UsageStatsManager manager = (UsageStatsManager) getContext().getSystemService(Context.USAGE_STATS_SERVICE);
                                UsageStats dayUse = manager.queryAndAggregateUsageStats(System.currentTimeMillis() - day, System.currentTimeMillis()).get(entry.getPackageName());
                                UsageStats weekUse = manager.queryAndAggregateUsageStats(System.currentTimeMillis() - day * 7, System.currentTimeMillis()).get(entry.getPackageName());
                                if ((dayUse != null && dayUse.getTotalTimeInForeground() > entry.getDayLimit()) || (weekUse != null && weekUse.getTotalTimeInForeground() > entry.getWeekLimit())) {
                                    entry.setWeekUse(weekUse.getTotalTimeInForeground());
                                    entry.setDayUse(dayUse.getTotalTimeInForeground());
                                    showBlockScreen();
                                }
                            }
                        }
                    }
                        //now we need to find
                    /*
                    if (!whiteList.contains(appName(event.getPackageName())))
                        System.out.println(appName(event.getPackageName())); //todo: send intent to blocker*/


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
        }

        public String appName(String packageName) {
            if (getContext() != null) {
                PackageManager pm = getContext().getPackageManager();
                ApplicationInfo ai;
                try {
                    ai = pm.getApplicationInfo(packageName, 0);
                } catch (PackageManager.NameNotFoundException e) {
                    ai = null;
                }
                return ai == null ? "oh gosh oh darn" : pm.getApplicationLabel(ai).toString();
            }
            return null;
        }


        public void showBlockScreen(){
            Intent startBlock = new Intent(getActivity(), BlockActivity.class);
            startBlock.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            HomeFragment.this.getContext().startActivity(startBlock);
        }
    }
}
