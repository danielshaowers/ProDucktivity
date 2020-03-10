package com.example.producktivity.dbs;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class}, version = 1)
public abstract class ToDoDatabase extends RoomDatabase {
    public abstract ToDoDaoAccess daoAccess();

    private static volatile ToDoDatabase INSTANCE;
    private static final int NUM_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    static ToDoDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ToDoDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ToDoDatabase.class, "todo_db").build();
                }
            }
        }
        return INSTANCE;
    }
}
