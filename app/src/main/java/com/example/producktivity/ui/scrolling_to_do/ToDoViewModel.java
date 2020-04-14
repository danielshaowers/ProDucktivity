package com.example.producktivity.ui.scrolling_to_do;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.producktivity.dbs.todo.Task;
import com.example.producktivity.dbs.todo.ToDoRepo;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel {
    private ToDoRepo repo;
    private final LiveData<List<Task>> mAllTasks;

    public ToDoViewModel(Application app) {
        super(app);
        repo = new ToDoRepo(app);
        mAllTasks = repo.getAllTasks();

        System.out.println("get tasks from repo " + repo.getAllTasks());
       // mPrioritizedTasks = repo.getPrioritizedTasks();

    }

    public LiveData<List<Task>> getAllTasks() {
        Log.i("dbs", "got task list: " + repo.getAllTasks().getValue());
        return repo.getAllTasks();}

    public void insert(Task task) {repo.insert(task);}

    public void delete(Task task) {repo.delete(task);}

    public void update(Task task) {repo.update(task);}

}
