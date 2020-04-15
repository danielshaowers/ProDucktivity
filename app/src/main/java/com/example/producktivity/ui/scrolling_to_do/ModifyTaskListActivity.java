package com.example.producktivity.ui.scrolling_to_do;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
import java.util.Calendar;
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

        reminderCalendar = MainActivity.makeCalendar(editTextReminder, ModifyTaskListActivity.this);
        myCalendar = MainActivity.makeCalendar(editTextDueDate, ModifyTaskListActivity.this);

        RadioButton high = findViewById(R.id.high_todo);
        RadioButton medium = findViewById(R.id.medium_todo);
        RadioButton low = findViewById(R.id.low_todo);


        Task oldTask;
        if ((oldTask = (Task) getIntent().getSerializableExtra("task")) != null) {
            update = true;
            editTextTitle.setText(oldTask.getTitle());
            editTextDesc.setText(oldTask.getDesc());
            editTextDueDate.setText(oldTask.getDueDate().toString());
            editTextReminder.setText(oldTask.getReminderTime().toString());
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
                setResult(oldTask, 2);
            } else {
                Task newTask = new Task();
                updateTaskFromFields(newTask);
                setResult(newTask, 1);
            }
        });
    }

    private void updateTaskFromFields(Task t) {
        t.setDesc(Objects.requireNonNull(editTextDesc.getText()).toString());
        t.setTitle(Objects.requireNonNull(editTextTitle.getText()).toString());

        t.setDueDate(myCalendar.getTime());

        t.setReminderTime(reminderCalendar.getTime());

        RadioGroup priorities = findViewById(R.id.todo_priority);
        RadioButton priority = findViewById(priorities.getCheckedRadioButtonId());

        Log.i("todo", "checked: " + priorities.getCheckedRadioButtonId());

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
    }

    private void setResult(Task task, int flag) {
        setResult(flag, new Intent().putExtra("task", task));
        finish();
    }
/*
    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<ModifyTaskListActivity> activityReference;
        private Task task;

        // only retain a weak reference to the activity
        InsertTask(ModifyTaskListActivity context, Task task) {
            activityReference = new WeakReference<>(context);
            this.task = task;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented note id
            activityReference.get().repo.insert(task);
            Log.e("ID ", "doInBackground: " + task.getId());
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool) {
                activityReference.get().setResult(task, 1);
                activityReference.get().finish();
            }
        }
    }*/


}
