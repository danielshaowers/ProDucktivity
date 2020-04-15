package com.example.producktivity.ui.scrolling_to_do;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.example.producktivity.dbs.todo.ToDoRepo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//implements InputAdapter.ItemClickListener if i want it to register button clicks
public class TaskFragment extends Fragment implements InputAdapter.OnTaskItemClick{
    private InputAdapter mAdapter;

    private List<Task> taskList;
    private TextView emptyView;
    private RecyclerView recyclerView;
    private ToDoViewModel vm;
    private int pos;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vm = new ViewModelProvider(this).get(ToDoViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.to_do, container, false);

        emptyView = root.findViewById(R.id.empty_list);

        recyclerView = root.findViewById(R.id.todo_recyclerview);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        displayList();
        return root;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

    }

    private void displayList() {
        vm.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                Log.i("task frag", "We observed a change!!");
                if(tasks.size() > 0) {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    if(mAdapter == null) {
                        mAdapter = new InputAdapter(TaskFragment.this, tasks);
                        taskList = tasks;
                        recyclerView.setAdapter(mAdapter);
                    } else {
                        taskList = tasks;
                        mAdapter.addTasks(tasks);
                    }
                } else updateEmptyView();
            }
        });
    }

    private void updateEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            Task task = (Task) data.getSerializableExtra("task");
            if (resultCode == 1) {
                taskList.add(task);
                vm.insert(task);
                mAdapter.notifyDataSetChanged();
            } else if (resultCode == 2) {
                taskList.set(pos, (Task) data.getSerializableExtra("task"));
                vm.update(task);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onTaskClick(final int pos) {
        new AlertDialog.Builder(Objects.requireNonNull(TaskFragment.this.getContext()))
                .setTitle("Select Options")
                .setItems( new String[]{"Complete", "Edit", "Delete"}, (dialogInterface, i) ->{
                    switch(i) {
                        case 0:
                            TaskFragment.this.pos = pos;
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
        vm.update(t);
    }

    private void updateTask(Task t) {
        startActivityForResult(
                new Intent(TaskFragment.this.getContext(),
                        ModifyTaskListActivity.class).putExtra("task", t),
                100
        );
    }

    private void deleteTask(Task t) {
        vm.delete(t);
        taskList.remove(t);
        mAdapter.notifyDataSetChanged();
    }
/*
    private static class RetrieveToDosTask extends AsyncTask<Void, Void, LiveData<List<Task>>> {

        private WeakReference<TaskFragment> fragReference;

        RetrieveToDosTask(TaskFragment context) {fragReference = new WeakReference<>(context);}

        @Override
        protected LiveData<List<Task>> doInBackground(Void... voids) {
            if(fragReference.get() != null)
                return fragReference.get().repo.getAllTasks();

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

    }*/
}
