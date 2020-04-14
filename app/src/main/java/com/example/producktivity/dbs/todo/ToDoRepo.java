package com.example.producktivity.dbs.todo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ToDoRepo {

    private ToDoDaoAccess mToDoDao;
    private LiveData<List<Task>> mAllTasks;
    private LiveData<List<Task>> mCompleteTasks;
    private LiveData<List<Task>> mIncompleteTasks;

    public ToDoRepo(Application app) {
        ToDoDatabase db = ToDoDatabase.getDatabase(app);
        mToDoDao = db.daoAccess();
        mAllTasks = mToDoDao.getTasksByDueDate();
        mCompleteTasks = mToDoDao.getTasksWithComplete(true);
        mIncompleteTasks = mToDoDao.getTasksWithComplete(false);
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public LiveData<List<Task>> getTaskWithComplete(boolean complete) {
        return complete? mCompleteTasks : mIncompleteTasks;
    }

    public void insert(Task task) {
        ToDoDatabase.databaseWriteExecutor.execute(() ->
                mToDoDao.insert(task));
        Log.i("congrats", "You inserted a task!");
    }

    public void update(Task task) {
        ToDoDatabase.databaseWriteExecutor.execute(() ->mToDoDao.update(task));
    }

    public void delete(Task task) {
        ToDoDatabase.databaseWriteExecutor.execute(()->mToDoDao.delete(task));
    }
}
