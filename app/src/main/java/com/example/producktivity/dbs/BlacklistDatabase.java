package com.example.producktivity.dbs;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BlacklistEntry.class}, version = 1)
public abstract class BlacklistDatabase extends RoomDatabase {
    public abstract BlacklistDaoAccess daoAccess();

    private static volatile BlacklistDatabase INSTANCE;
    private static final int NUM_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    static BlacklistDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (ToDoDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BlacklistDatabase.class, "blacklist_db").build();
                }
            }
        }
        return INSTANCE;
    }
}
