package com.example.producktivity;


import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.producktivity.dbs.blacklist.Category;
import com.example.producktivity.dbs.todo.Task;

import com.example.producktivity.dbs.blacklist.BlacklistEntry;

import com.example.producktivity.notifs.ReminderNotificationPublisher;
import com.example.producktivity.ui.scrolling_to_do.ModifyTaskListActivity;
import com.example.producktivity.ui.scrolling_to_do.TaskFragment;
import com.example.producktivity.ui.scrolling_to_do.ToDoViewModel;
import com.example.producktivity.ui.blockSelect.BlockSelectViewModel;
import com.example.producktivity.ui.usage_data.UsageDataHandler;
import com.example.producktivity.ui.usage_data.UsageTime;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.common.collect.ComparisonChain.start;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration; //the lefthand bar to navigate

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    private boolean isFabOpen = false;
    public ToDoViewModel toDoVM;
    private BlockSelectViewModel bsViewModel = null;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final boolean[] updated = {false};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab); //THE PLUS BUTTON
        toDoVM = new ViewModelProvider(MainActivity.this).get(ToDoViewModel.class);
        Log.i("ToDoVM", "The todovm object is " + toDoVM.toString());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //make this add a task in future
                Snackbar.make(view, "Create a task", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //once the FAB is clicked
                startActivityForResult(new Intent(
                                MainActivity.this,
                                ModifyTaskListActivity.class),
                        100
                );
            }
        });
        //this is where the navigation bar is linking to all our pages
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_usage_data, R.id.nav_block,
                R.id.nav_todo, R.id.nav_calendar, R.id.nav_profile)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        if (bsViewModel == null) {
            bsViewModel = ViewModelProviders.of(this).get(BlockSelectViewModel.class);
            UsageDataHandler handler = new UsageDataHandler(this);
            //I am hoping this updates the values every time main activity is created, so we don't have to during the usage stats
            bsViewModel.getSelectList().observe(this, new Observer<List<BlacklistEntry>>() {
                @Override
                public void onChanged(@Nullable List<BlacklistEntry> s) {
                    //only get the list with updated usage times and categories
                    if (!updated[0]) {
                        final List<BlacklistEntry>[] update = new List[]{s};
                        //I put this code in a new thread so the main thread doesn't get blocked
                        new Thread(new Runnable() {
                            public void run() {
                                System.out.println("onChanged called in MainActivity");
                                List<UsageTime> usagetimes = handler.getStats(BlacklistEntry.DAY);
                                update[0] = bsViewModel.updateList(usagetimes, update[0]);
                                System.out.println(usagetimes.size() + "time size issdlkfjasdl;fk");
                                ClassificationClient cClient = new ClassificationClient(getApplicationContext());
                                BlacklistClient blacklistClient = new BlacklistClient(s, getApplicationContext());
                                for (BlacklistEntry app : update[0]) {
                                    //strip this out into a new method to call when response arrives
                                    addInfoToEntry(app, cClient, blacklistClient);
                                }
                            }
                        }).start();
                        bsViewModel.replaceDB(s);
                        updated[0] = true;
                    }
                    bsViewModel.getSelectList().removeObserver(this);
                }
            });
        }
    }

    public void setVM(BlockSelectViewModel bs){
        bsViewModel = bs;
    }
    public void addInfoToEntry(BlacklistEntry app, ClassificationClient cClient, BlacklistClient blacklistClient){
        System.out.println(app.getAppName());
        String appId = app.getPackageName();
        String cat = cClient.requestAppCategory(appId);
        try {
            app.setCategory(Category.valueOf(cat));
        }
        catch (IllegalArgumentException i){
            System.out.println("no valid cateogry found");
            return;
        }
        Boolean productive = blacklistClient.classifyApp(appId);
        if(cat.equalsIgnoreCase("SYSTEM") || cat.equalsIgnoreCase("PRODUCKTIVE")){
            productive = true;
        }
        //TODO: add productive classification to app entry
        app.setInferredProductive(productive);
    }
    public BlockSelectViewModel getVM(){return bsViewModel;}


    @Override
    protected void onResume() {
        super.onResume(); //todo: uncomment out the previous lines
        checkPermissions(AppOpsManager.OPSTR_GET_USAGE_STATS, Settings.ACTION_USAGE_ACCESS_SETTINGS);
        checkPermissions(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW, Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
       // checkPermissions(AppOpsManager.OPSTR_WRITE_EXTERNAL_STORAGE, Settings.ACTION_MANAGE_WRITE_SETTINGS);
    }

    private void clearInputs(){
        CardView card= findViewById(R.id.task_card);
        card.setVisibility(View.GONE); //if they entered something into the database
        ((EditText)findViewById(R.id.task_title)).getText().clear();
        ((RadioGroup)findViewById(R.id.priority_group)).clearCheck();
        ((EditText)findViewById(R.id.task_date)).getText().clear();
        ((EditText)findViewById(R.id.reminder)).getText().clear();
        ((EditText)findViewById(R.id.description)).getText().clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void checkPermissions(String permission, String setting) {
        System.out.println(permission + "\t" + setting);
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        if (appOps.checkOpNoThrow(permission, android.os.Process.myUid(), getPackageName()) == AppOpsManager.MODE_ALLOWED)
            System.out.println("we do have permission");
        else startActivityForResult(new Intent(setting), 69);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public  static void scheduleNotification(Context context, Task task) {//delay is after how much time(in millis) from current time you want to schedule the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, default_notification_channel_id)
                .setContentTitle(task.getTitle())
                .setContentText(task.getDesc())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.duck)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, task.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, ReminderNotificationPublisher.class);
        notificationIntent.putExtra(ReminderNotificationPublisher.NOTIFICATION_ID, task.getId());
        notificationIntent.putExtra(ReminderNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, task.getId(), notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = task.getReminderTime().getTime() - SystemClock.elapsedRealtime();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

   /* @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkPermissions() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.PACKAGE_USAGE_STATS) != PackageManager.PERMISSION_GRANTED) {
            //if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.PACKAGE_USAGE_STATS))
            System.out.println("we require permission");
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.PACKAGE_USAGE_STATS}, PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
            startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
        } else
            System.out.println("bro we are vibing");
    }*/

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        System.out.println("reached onActivityResult");
        Log.i("MainAct", "Result code: " + resultCode);
        if (requestCode == 100 && resultCode > 0) {
            Task task = (Task) data.getSerializableExtra("task");
            if (resultCode == 1) {
                toDoVM.insert(task);
            } else if (resultCode == 2) {
                toDoVM.update(task);
            }
        }
        System.out.println("are we ok?");
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 69  && resultCode  == RESULT_OK) {
                System.out.println("we gucci");
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
public void onPause(){
        super.onPause();
      //  JacocoReportGenerator.generateCoverageReport();
}
    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("congrats, we have permission");
                } else
                    System.out.println("ok, now everything fails");
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }*/
}
