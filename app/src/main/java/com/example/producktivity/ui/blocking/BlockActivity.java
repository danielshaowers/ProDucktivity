package com.example.producktivity.ui.blocking;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.producktivity.R;

public class BlockActivity extends AppCompatActivity {

    private BlockViewModel blockViewModel;
    private int duckClicks = 0;

    private final int PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_page);

        blockViewModel = ViewModelProviders.of(this).get(BlockViewModel.class);
        System.out.println("creating a blocker");

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true)
                    if (System.currentTimeMillis() % 1000 == 0)
                        doStuff();
            }

            public void doStuff() {
                PackageManager pManager = BlockActivity.this.getPackageManager();
                UsageStatsManager usManager = (UsageStatsManager) BlockActivity.this.getSystemService(Context.USAGE_STATS_SERVICE);
                UsageEvents events = usManager.queryEvents(System.currentTimeMillis() - 2500, System.currentTimeMillis());
                while (events.hasNextEvent()) {
                    UsageEvents.Event event = new UsageEvents.Event();
                    events.getNextEvent(event);


                    System.out.println(event.getPackageName());


                    boolean isBlocked;
                    try {
                        isBlocked = (pManager.getApplicationInfo(event.getPackageName(), 0).flags & ApplicationInfo.FLAG_SYSTEM) == 0
                                && event.getClassName() != "Producktive";
                    } catch (Exception e) {
                        isBlocked = false;
                    }
                    if (isBlocked) {
                        System.out.println("hey that's not allowed");
                        //TODO your own code of the window and whatnot

                    }
                }
            }

        });
        thread.start();



        /*Intent intent = new Intent(this, BlockerService.class);
        startService(intent);
        */

        final TextView textView = findViewById(R.id.title_block);




        /*final TextView textView = findViewById(R.id.title_block);
        ImageButton duck = findViewById(R.id.continueDuck);
        Button returnButt = findViewById(R.id.ReturnButton);
        returnButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //make this add a task in future
                Snackbar.make(view, "Good Choice!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        duck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (++duckClicks > 10) {
                    Intent startMain = new Intent(BlockActivity.this, MainActivity.class);
                    startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startMain);
                } else
                    Snackbar.make(view, "Noooo don't do it!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });*/
    }

}