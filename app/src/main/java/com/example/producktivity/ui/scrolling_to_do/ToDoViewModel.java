package com.example.producktivity.ui.scrolling_to_do;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.producktivity.dbs.Task;
import com.example.producktivity.dbs.ToDoRepo;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel {
    private ToDoRepo repo;
    private final LiveData<List<Task>> mAllTasks;
    private final LiveData<List<Task>> mPrioritizedTasks;

    public ToDoViewModel(Application app) {
        super(app);
        repo = new ToDoRepo(app);
        mAllTasks = repo.getAllTasks();
        mPrioritizedTasks = repo.getPrioritizedTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public LiveData<List<Task>> getPrioritizedTasks() {
        return mPrioritizedTasks;
    }

    public void insert(Task task) {repo.insert(task);}

}
