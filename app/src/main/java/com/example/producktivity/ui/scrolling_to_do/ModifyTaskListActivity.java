package com.example.producktivity.ui.scrolling_to_do;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.producktivity.MainActivity;
import com.example.producktivity.dbs.todo.Priority;
import com.example.producktivity.dbs.todo.Task;
import com.example.producktivity.R;
import com.example.producktivity.dbs.todo.ToDoRepo;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ModifyTaskListActivity extends AppCompatActivity {

    private TextInputEditText editTextTitle, editTextDesc;
    private Calendar myCalendar, reminderCalendar;

    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task_page);

        editTextTitle = findViewById(R.id.todo_title);
        editTextDesc = findViewById(R.id.todo_description);

        EditText editTextDueDate = findViewById(R.id.todo_date);
        EditText editTextReminder = findViewById(R.id.todo_reminder);
        editTextDueDate.setFocusable(false);
        editTextReminder.setFocusable(false);

        reminderCalendar = makeCalendar(editTextReminder, ModifyTaskListActivity.this);
        myCalendar = makeCalendar(editTextDueDate, ModifyTaskListActivity.this);
        RadioButton high = findViewById(R.id.high_todo);
        RadioButton medium = findViewById(R.id.medium_todo);
        RadioButton low = findViewById(R.id.low_todo);


        Task oldTask;
        if ((oldTask = (Task) getIntent().getSerializableExtra("task")) != null) {
            System.out.println("we are updating!");
            update = true;
            editTextTitle.setText(oldTask.getTitle());
            editTextDesc.setText(oldTask.getDesc());


            editTextDueDate.setText(new Timestamp(oldTask.getDueDate().getTime()).toString());
            editTextReminder.setText(new Timestamp(oldTask.getReminderTime().getTime()).toString());

            switch(oldTask.getPriority()) {
                case LOW: low.setChecked(true);
                case MED: medium.setChecked(true);
                case HIGH: high.setChecked(true);
            }
        }
        Button button = findViewById(R.id.done_button_edit);
        button.setOnClickListener(view -> {
            if (update) {
                updateTaskFromFields(oldTask);
                MainActivity.scheduleNotification(this, oldTask);
                setResult(oldTask, 2);
            } else {
                Task newTask = new Task();
                updateTaskFromFields(newTask);
                MainActivity.scheduleNotification(this, newTask);
                setResult(newTask, 1);
            }

        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        boolean handleReturn = super.dispatchTouchEvent(ev);

        View view = getCurrentFocus();

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if(view instanceof EditText){
            View innerView = getCurrentFocus();

            if (ev.getAction() == MotionEvent.ACTION_UP && !viewContains(innerView, x, y)) {

                InputMethodManager input = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                input.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                        .getWindowToken(), 0);
            }
        }

        return handleReturn;
    }

    private boolean viewContains(View v, int x, int y) {
        int[] coords = new int[2];
        v.getLocationOnScreen(coords);
        return x >= coords[0] && x <= coords[0] + v.getRight() && y >= coords[1] && y <= coords[1] + v.getBottom();
    }

    private void updateTaskFromFields(Task t) {

        t.setDesc(Objects.requireNonNull(editTextDesc.getText()).toString());
        t.setTitle(Objects.requireNonNull(editTextTitle.getText()).toString());

        t.setDueDate(myCalendar.getTime());

        t.setReminderTime(reminderCalendar.getTime());

        RadioGroup priorities = findViewById(R.id.todo_priority);
        RadioButton priority = findViewById(priorities.getCheckedRadioButtonId());

        if (priority != null) {
            if (priority.getId() == R.id.high_todo)
                t.setPriority(Priority.HIGH);
            if (priority.getId() == R.id.medium_todo)
                t.setPriority(Priority.MED);
            if (priority.getId() == R.id.low_todo)
                t.setPriority(Priority.LOW);
        }
        else {
            t.setPriority(Priority.LOW);
        }
        Log.i("todo", "checked: " + t.getPriority().toString());
    }

    private void setResult(Task task, int flag) {
        System.out.println("task being passed in setResult, next stop: main activity onActivityForResult");
        setResult(flag, new Intent().putExtra("task", task));
        finish();
    }

    public Calendar makeCalendar(EditText date, Context context) {
        final DatePickerDialog[] picker = new DatePickerDialog[1];
        Calendar myCalendar = Calendar.getInstance();
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                int month = myCalendar.get(Calendar.MONTH);
                int year = myCalendar.get(Calendar.YEAR);
                picker[0] = new DatePickerDialog(ModifyTaskListActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (monthOfYear + 1));
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        myCalendar.set(Calendar.HOUR_OF_DAY, 12);
                        myCalendar.set(Calendar.MINUTE, 0);
                        myCalendar.set(Calendar.SECOND, 0);
                        myCalendar.set(Calendar.MILLISECOND, 0);
                    }
                }, year, month, day);
                picker[0].show();
            }});

        return myCalendar;
    }

}
