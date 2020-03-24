package com.example.producktivity.dbs;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ToDoRepo {

    private ToDoDaoAccess mToDoDao;
    private LiveData<List<Task>> mAllTasks;
    private LiveData<List<Task>> mPrioritizedTasks;

    public ToDoRepo(Application app) {
        ToDoDatabase db = ToDoDatabase.getDatabase(app);
        mToDoDao = db.daoAccess();
        mAllTasks = mToDoDao.getTasksByDueDate();
        mPrioritizedTasks = mToDoDao.getTasksByPriority();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public LiveData<List<Task>> getPrioritizedTasks() {
        return mPrioritizedTasks;
    }

    public void insert(Task task) {
        ToDoDatabase.databaseWriteExecutor.execute(() ->
                mToDoDao.insert(task));
    }

    public void updateTask(Task task) {
        ToDoDatabase.databaseWriteExecutor.execute(() ->
                mToDoDao.update(task));
    }

    public void deleteTask(Task task) {
        ToDoDatabase.databaseWriteExecutor.execute(() ->
                mToDoDao.delete(task));
    }
}
