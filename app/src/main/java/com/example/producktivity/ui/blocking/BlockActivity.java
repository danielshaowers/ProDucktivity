package com.example.producktivity.ui.blocking;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.producktivity.MainActivity;
import com.example.producktivity.R;
import com.google.android.material.snackbar.Snackbar;

public class BlockActivity extends AppCompatActivity {

    //private BlockViewModel blockViewModel;
    private int duckClicks = 0;

    private final int PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.block_page);

        //blockViewModel = ViewModelProviders.of(this).get(BlockViewModel.class);
        System.out.println("creating a blocker");
        Intent intent = getIntent(); // get intent that started this activity

        /*Intent intent = new Intent(this, BlockerService.class);
        startService(intent);
        */

        //final TextView textView = findViewById(R.id.title_block);
        final TextView textView = findViewById(R.id.title_block);
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
        });
    }

    /*public void block() {
        Thread thread = new Thread(new BlockerRunnable());
        thread.start();
    }*/

    /*public class BlockerRunnable implements Runnable {

        PackageManager pManager = BlockActivity.this.getPackageManager();
        UsageStatsManager usManager = (UsageStatsManager) BlockActivity.this.getSystemService(Context.USAGE_STATS_SERVICE);

        List<String> whiteList = Arrays.asList("Pixel Launcher", "ProDucktive");

        public BlockerRunnable() {

        }


        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

        @Override
        public void run() {
            while (true)
                if (System.currentTimeMillis() % 1000 == 0)
                    appOnScreen();
        //System.out.println("hey this is working");
        //return Service.START_NOT_STICKY;
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


//                    boolean isBlocked;
//                    try {
//                        isBlocked = (pManager.getApplicationInfo(event.getPackageName(), 0).flags & ApplicationInfo.FLAG_SYSTEM) == 0
//                                && event.getClassName() != "ProDucktive";
//                    } catch (Exception e) {
//                        isBlocked = false;
//                    }
//                    if (isBlocked) {
//                        System.out.println("hey that's not allowed");
//                        //TODO your own code of the window and whatnot
//
//                    }
                }
            }
        }

        public String appName(String packageName) {
            PackageManager pm = BlockActivity.this.getPackageManager();
            ApplicationInfo ai;
            try {ai = pm.getApplicationInfo(packageName, 0);} catch (PackageManager.NameNotFoundException e) {ai = null;}
            return ai == null ? "oh gosh oh darn" : pm.getApplicationLabel(ai).toString();
        }


        public void showBlockScreen(){
            Intent startBlock = new Intent("com.example.producktivity.ui.blocking.BlockActivity");
            startBlock.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BlockActivity.this.startActivity(startBlock);

        }
    }*/


}