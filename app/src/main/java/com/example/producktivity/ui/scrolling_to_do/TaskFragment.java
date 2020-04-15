package com.example.producktivity.ui.scrolling_to_do;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producktivity.MainActivity;
import com.example.producktivity.R;
import com.example.producktivity.dbs.todo.Task;
import com.example.producktivity.dbs.todo.ToDoRepo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

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
        //vm = new ViewModelProvider(this).get(ToDoViewModel.class);
        /*View root = LayoutInflater.from(this.getContext())
                .inflate(R.layout.to_do, null, false);
        emptyView = root.findViewById(R.id.empty_list);

        recyclerView = root.findViewById(R.id.todo_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new InputAdapter(TaskFragment.this, new ArrayList<Task>());
        recyclerView.setAdapter(mAdapter);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.to_do, container, false);

        emptyView = root.findViewById(R.id.empty_list);

        recyclerView = root.findViewById(R.id.todo_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new InputAdapter(TaskFragment.this, new ArrayList<Task>());
        new RetrieveToDosTask(this);

        recyclerView.setAdapter(mAdapter);
        displayList();
        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        vm = ((MainActivity)context).toDoVM;
    }

    private void displayList() {
        vm.getAllTasks().observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                Log.i("task frag", "We observed a change!!");
                Log.i("task frag", "task list size: " + tasks.size());
                if(tasks.size() > 0) {
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    mAdapter.addTasks(tasks);
                    mAdapter.notifyDataSetChanged();
                    Log.i("task frag", "data set is actually notified. :)");
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
        Log.i("Task frag", "result called!!!!");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode > 0) {
            Task task = (Task) data.getSerializableExtra("task");
            if (resultCode == 1) {

                vm.insert(task);
            } else if (resultCode == 2) {

                vm.update(task);
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
                            setTaskCompleted(mAdapter.getItemAt(pos));
                        case 1:
                            updateTask(mAdapter.getItemAt(pos));
                        case 2:
                            deleteTask(mAdapter.getItemAt(pos));
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
    }

    private static class RetrieveToDosTask extends AsyncTask<Void, Void, LiveData<List<Task>>> {

        private WeakReference<TaskFragment> fragReference;

        RetrieveToDosTask(TaskFragment context) {fragReference = new WeakReference<>(context);}

        @Override
        protected LiveData<List<Task>> doInBackground(Void... voids) {
            if(fragReference.get() != null)
                return fragReference.get().vm.getAllTasks();

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
