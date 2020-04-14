package com.example.producktivity;


import android.app.AppOpsManager;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.producktivity.dbs.todo.Priority;
import com.example.producktivity.dbs.todo.Task;

import com.example.producktivity.dbs.blacklist.BlacklistEntry;
import com.example.producktivity.dbs.blacklist.Category;

import com.example.producktivity.ui.scrolling_to_do.ModifyTaskListActivity;
import com.example.producktivity.ui.scrolling_to_do.TaskFragment;
import com.example.producktivity.ui.scrolling_to_do.ToDoViewModel;
import com.example.producktivity.ui.send.BlockSelectViewModel;
import com.example.producktivity.ui.usage_data.UsageDataHandler;
import com.example.producktivity.ui.usage_data.UsageTime;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration; //the lefthand bar to navigate


    private boolean isFabOpen = false;
    private ToDoViewModel toDoVM;

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

                /*
                if (!isFabOpen){
                    isFabOpen = true;
                    CardView taskCard = findViewById(R.id.task_card);
                    taskCard.setVisibility(View.VISIBLE);
                    EditText dueDate = findViewById(R.id.task_date);
                    Calendar myCalendar = MainActivity.makeCalendar(dueDate, MainActivity.this);
                    EditText reminder = findViewById(R.id.reminder);
                    Calendar reminderCalendar = MainActivity.makeCalendar(reminder, MainActivity.this);

                    //done setting date
                    Button done = findViewById(R.id.done_button);
                    done.setOnClickListener(view12 -> {
                        Task task = new Task();
                        EditText title = findViewById(R.id.task_title);
                        if (title.getText() != null){
                            task.setTitle(title.getText().toString());
                            RadioGroup priorities = findViewById(R.id.priority_group);
                            RadioButton priority = findViewById(priorities.getCheckedRadioButtonId());
                            if (priority != null) {
                                if (priority.getId() == R.id.high_priority)
                                    task.setPriority(Priority.HIGH);
                                if (priority.getId() == R.id.medium_priority)
                                    task.setPriority(Priority.MED);
                                if (priority.getId() == R.id.low_priority)
                                    task.setPriority(Priority.LOW);
                            } else
                                task.setPriority(Priority.LOW);
                            EditText description = findViewById(R.id.description);
                            if (!description.getText().toString().equals(""))
                                task.setDesc(description.getText().toString());
                            if (!myCalendar.getTime().toString().equals("")) {
                                task.setDueDate(myCalendar.getTime());
                            }
                            task.setReminderTime(reminderCalendar.getTime());
                            //also need to set reminder time. would prefer if it was an enum instead of a date
                            task.setComplete(task.getTitle() != null && task.getDesc() != null && task.getPriority() != null &&
                            task.getDueDate() != null); //task.getReminderTime() != null
                            toDoVM = new ViewModelProvider(MainActivity.this).get(ToDoViewModel.class);
                            toDoVM.insert(task);

                            taskCard.setVisibility(View.GONE);
                            isFabOpen = false;
                            //clear all views
                            clearInputs();
                            Snackbar.make(view, "Task Saved", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }});
                }
                else{
                    isFabOpen = false;
                    CardView card= findViewById(R.id.task_card);
                    card.setVisibility(View.GONE); //if they entered something into the database
                    clearInputs();
                }*/
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

        BlockSelectViewModel bsViewModel =  ViewModelProviders.of(this).get(BlockSelectViewModel.class);
        UsageDataHandler handler = new UsageDataHandler(this);
        //I am hoping this updates the values every time main activity is created, so we don't have to during the usage stats
        bsViewModel.getSelectList().observe(this, new Observer<List<BlacklistEntry>>() {
            @Override
            public void onChanged(@Nullable List<BlacklistEntry> s) {
                if (!updated[0]) {
                    System.out.println("onChanged called for the blockselect viewmodel");
                    List<UsageTime> usagetimes = handler.getStats(BlacklistEntry.DAY);
                    bsViewModel.updateList(usagetimes, s);
                    ClassificationClient cClient = new ClassificationClient();
                    BlacklistClient blacklistClient = new BlacklistClient();
                    /*for(BlacklistEntry app: s){
                        String appId = app.getPackageName();
                        String cat = cClient.requestAppCategory(appId);
                        app.setCategory(Category.valueOf(cat));
                        //Boolean productive = blacklistClient.classifyApp(cat);
                        //TODO: add productive classification to app entry
                        //app.setInferredProductive(productive);
                    }
                    bsViewModel.replaceDB(s);
                    updated[0] = true;*/
                }
                bsViewModel.getSelectList().removeObserver(this);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkPermissions(AppOpsManager.OPSTR_GET_USAGE_STATS, Settings.ACTION_USAGE_ACCESS_SETTINGS);
        checkPermissions(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW, Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
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
    //sets the onclick listener and returns the calendar with the selected date
    public static Calendar makeCalendar(EditText date, Context context) {
        Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener duedate = (view1, year, monthOfYear, dayOfMonth) -> {
            //myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //myCalendar.set(Calendar.MINUTE, minuteOfHour);
          //  myCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            String myFormat = "MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            date.setText(sdf.format(myCalendar.getTime()));
        };
        // date.setOnClickListener(v -> new DatePickerDialog(MainActivity.this, date, myCalendar
        date.setOnClickListener(v -> new DatePickerDialog(context, duedate, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        return myCalendar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void checkPermissions(String permission, String setting) {
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

    /*@RequiresApi(api = Build.VERSION_CODES.M)
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
