package com.example.producktivity;

import android.app.DatePickerDialog;
import android.os.Bundle;

import com.example.producktivity.dbs.Priority;
import com.example.producktivity.dbs.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration; //the lefthand bar to navigate
    private boolean isFabOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab); //THE MAIL BUTTON
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //make this add a task in future
                Snackbar.make(view, "Suck my duck", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            if (!isFabOpen){
                isFabOpen = true;
                CardView taskCard = findViewById(R.id.task_card);
                taskCard.setVisibility(View.VISIBLE); //note to daniel - I might want to set visibility of the layout to gone

                final Calendar myCalendar = Calendar.getInstance();
                EditText dueDate= findViewById(R.id.task_date);
                DatePickerDialog.OnDateSetListener date = (view1, year, monthOfYear, dayOfMonth) -> {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "MM/dd/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    dueDate.setText(sdf.format(myCalendar.getTime()));
                };

                dueDate.setOnClickListener(v -> new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show());
                //done setting date
                Button done = findViewById(R.id.done_button);
                done.setOnClickListener(view12 -> {
                    Task task = new Task();
                    RadioGroup priorities = findViewById(R.id.priority_group);
                    RadioButton priority = findViewById(priorities.getCheckedRadioButtonId());
                    if (priority.getId() == R.id.high_priority)
                        task.setPriority(Priority.HIGH);
                    if (priority.getId() == R.id.medium_priority)
                        task.setPriority(Priority.MED);
                    if (priority.getId() == R.id.low_priority)
                        task.setPriority(Priority.LOW);
                    EditText title = findViewById(R.id.task_title);
                    task.setTitle(title.getText().toString());
                    EditText description = findViewById(R.id.description);
                    if (description.getText() != null)
                        task.setDesc(description.getText().toString());
                    if (myCalendar.getTime() != null){
                        task.setDueDate(myCalendar.getTime());
                    }
                    //also need to set reminder time. would prefer if it was an enum instead of a date
                    task.setComplete( task.getTitle() != null && task.getDesc() != null && task.getPriority() != null &&
                            task.getDueDate() != null); //task.getReminderTime() != null
                });
            }
            else{
                isFabOpen = false;
                findViewById(R.id.task_input).setVisibility(View.GONE);
            }
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
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
