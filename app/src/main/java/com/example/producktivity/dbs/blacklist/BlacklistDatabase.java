package com.example.producktivity.dbs.blacklist;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.producktivity.dbs.todo.ToDoDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {com.example.producktivity.dbs.blacklist.BlacklistEntry.class}, version = 3)
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
                            BlacklistDatabase.class, "blacklist_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


// add before .build -> .addCallback(sRoomDatabaseCallback).
  /*  private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriteExecutor.execute(() ->{
                BlacklistDaoAccess dao = INSTANCE.daoAccess();
                BlacklistEntry next = new BlacklistEntry("testForInsert");
                next.setCategory(Category.BEAUTY);
                next.setDayUse(10);
                next.setWeekUse(10);
                next.setMonthUse(10);
                next.setSpan_flag(1);
                next.setPackageName("blah");
                dao.insert(new BlacklistEntry("testForInsert"));
                System.out.println(dao.getList().getValue().size() + "dao size after test insert");
            });
        }
    }; */
}
