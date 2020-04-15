package com.example.producktivity.ui.scrolling_to_do;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

//implements InputAdapter.ItemClickListener if i want it to register button clicks
public class TaskFragment extends Fragment implements InputAdapter.OnTaskItemClick, AdapterView.OnItemSelectedListener{
    private InputAdapter mAdapter;

    private List<Task> taskList;

    private TextView emptyView;
    private RecyclerView recyclerView;
    private ToDoViewModel vm;
    private int pos;

    private String[] selectViewArray = {"Incomplete Tasks", "Completed Tasks", "All Tasks"};
    ArrayList<String> spinnerList = new ArrayList<>(Arrays.asList(selectViewArray));
    Spinner spinner;
    private int currentSelection;

    private String[] forIncompleteTasks = new String[]{"Complete", "Edit", "Delete"};
    private String[] forCompletedTasks = new String[]{"Recover Task", "Edit", "Delete"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = initViews(inflater, container, savedInstanceState);

        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(0);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        vm = ((MainActivity)context).toDoVM;
    }

    private View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.to_do, container, false);

        emptyView = root.findViewById(R.id.empty_list);

        spinner = root.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        recyclerView = root.findViewById(R.id.todo_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new InputAdapter(TaskFragment.this, new ArrayList<>());
        new RetrieveToDosTask(this);

        recyclerView.setAdapter(mAdapter);
        return root;
    }

    private void observeTasks(LiveData<List<Task>> tasks) {
        tasks.observe(getViewLifecycleOwner(), new Observer<List<Task>>() {
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
        Task currentTask = mAdapter.getItemAt(pos);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Objects.requireNonNull(TaskFragment.this.getContext()))
                .setTitle("Select Options");
        DialogInterface.OnClickListener d = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case 0:
                        setTaskCompleted(currentTask, !currentTask.isComplete()); break;
                    case 1:
                        updateTask(mAdapter.getItemAt(pos));
                        break;
                    case 2:
                        deleteTask(mAdapter.getItemAt(pos));
                        break;
                }
        }};
        if(currentTask.isComplete()) {
            alertDialogBuilder.setItems(forCompletedTasks, d).show();
        } else {
            alertDialogBuilder.setItems(forIncompleteTasks, d).show();
        }
    }

    private void setTaskCompleted(Task t, boolean b) {
        t.setComplete(b);
        vm.update(t);
        mAdapter.notifyDataSetChanged();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                observeTasks(vm.getIncompleteTasks()); break;
            case 1:
                observeTasks(vm.getCompleteTasks()); break;
            case 2:
                observeTasks(vm.getAllTasks()); break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
