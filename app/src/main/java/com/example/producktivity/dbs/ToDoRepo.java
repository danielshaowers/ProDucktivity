package com.example.producktivity.dbs;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ToDoRepo {

    private ToDoDaoAccess mToDoDao;
    private LiveData<List<Task>> mAllTasks;

    public ToDoRepo(Application app) {
        ToDoDatabase db = ToDoDatabase.getDatabase(app);
        mToDoDao = db.daoAccess();
        mAllTasks = mToDoDao.getTasksByDueDate();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public void insert(Task task) {
        ToDoDatabase.databaseWriteExecutor.execute(() ->
                mToDoDao.insert(task));
    }

    public void update(Task task) {mToDoDao.update(task);}

    public void delete(Task task) {mToDoDao.delete(task);}
}
