package com.example.producktivity.ui.scrolling_to_do;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.R;
import com.example.producktivity.dbs.todo.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//implements InputAdapter.ItemClickListener if i want it to register button clicks
public class TaskFragment extends AppCompatActivity implements InputAdapter.OnTaskItemClick{
    private InputAdapter mAdapter;
    private List<Task> taskList;
    //public static final int GET_FROM_GALLERY = 1;
    private ToDoViewModel viewModel;
    private int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ToDoViewModel.class);

        taskList = new ArrayList<>();
        mAdapter = new InputAdapter(this, taskList);

        viewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskList = tasks;
                mAdapter.setTasks(tasks);
                mAdapter.notifyDataSetChanged();
            }
        });

        setContentView(R.layout.to_do);

        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(listener);

        RecyclerView recyclerView = findViewById(R.id.todo_recyclerview);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //used to be root's context
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        displayList();

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(TaskFragment.this, ModifyTaskListActivity.class), 100);
        }
    };

    private void displayList() {
        new RetrieveToDosTask(this).execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            if (resultCode == 1) {
                taskList.add((Task) data.getSerializableExtra("task"));
            } else if (resultCode == 2) {
                taskList.add((Task) data.getSerializableExtra("task"));
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onTaskClick(final int pos) {
        new AlertDialog.Builder(Objects.requireNonNull(TaskFragment.this))
                .setTitle("Select Options")
                .setItems( new String[]{"Complete", "Edit", "Delete"}, (dialogInterface, i) ->{
                    switch(i) {
                        case 0:
                            setTaskCompleted(taskList.get(pos));
                        case 1:
                            TaskFragment.this.pos = pos;
                            updateTask(taskList.get(pos));
                        case 2:
                            deleteTask(taskList.get(pos));
                    }
                }).show();
    }

    private void setTaskCompleted(Task t) {
        t.setComplete(true);
        viewModel.update(t);
        mAdapter.notifyDataSetChanged();
    }

    private void updateTask(Task t) {
        startActivityForResult(
                new Intent(TaskFragment.this,
                        ModifyTaskListActivity.class).putExtra("task", t),
                100
        );
        mAdapter.notifyDataSetChanged();
    }

    private void deleteTask(Task t) {
        viewModel.delete(t);
        taskList.remove(t);
        mAdapter.notifyDataSetChanged();
    }

    private static class RetrieveToDosTask extends AsyncTask<Void, Void, LiveData<List<Task>>> {

        private WeakReference<TaskFragment> fragReference;

        RetrieveToDosTask(TaskFragment context) {fragReference = new WeakReference<>(context);}

        @Override
        protected LiveData<List<Task>> doInBackground(Void... voids) {
            if(fragReference.get() != null)
                return fragReference.get().viewModel.getAllTasks();

            else
                return null;
        }

        @Override
        protected void onPostExecute(LiveData<List<Task>> taskList) {
            List<Task> list = taskList.getValue();
            if(list != null && !list.isEmpty()) {
                fragReference.get().taskList.clear();
                fragReference.get().taskList.addAll(list);

                fragReference.get().mAdapter.notifyDataSetChanged();
            }
        }

    }

}
