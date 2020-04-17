package com.example.producktivity.dbs.todo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public class ToDoRepo {

    private ToDoDaoAccess mToDoDao;
    private LiveData<List<Task>> mAllTasks;
    private LiveData<List<Task>> mCompleteTasks;
    private LiveData<List<Task>> mIncompleteTasks;
    private ToDoDatabase db;

    public ToDoRepo(Application app) {
        db = Room.databaseBuilder(app.getApplicationContext(),
                ToDoDatabase.class, "todo_db").build();
        mToDoDao = db.daoAccess();
        mAllTasks = mToDoDao.getTasks();
        mCompleteTasks = mToDoDao.getTasksWithComplete(true);
        mIncompleteTasks = mToDoDao.getTasksWithComplete(false);
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public LiveData<List<Task>> getTaskWithComplete(boolean complete) {
        return complete? mCompleteTasks : mIncompleteTasks;
    }

    @SuppressLint("StaticFieldLeak")
    public void insert(Task task) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.daoAccess().insert(task);
                return null;
            }
        }.execute();
        Log.i("congrats", "You inserted a task!");
    }

    @SuppressLint("StaticFieldLeak")
    public void update(Task task) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.daoAccess().update(task);
                Log.i("repo", "this is actually updating" + task.getTitle());
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void delete(Task task) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.daoAccess().delete(task);
                return null;
            }
        }.execute();
    }
}
