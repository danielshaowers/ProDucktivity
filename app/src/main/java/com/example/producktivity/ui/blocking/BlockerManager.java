package com.example.producktivity.ui.blocking;

import android.content.Context;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;

public class BlockerManager extends Worker {

    List<String> blockedApps;

    public BlockerManager(Context context, WorkerParameters params, List<String> blockedApps) {
        super(context, params);
        this.blockedApps = blockedApps;
    }

    public Result doWork() {
        return null;    //TODO
    }

}
