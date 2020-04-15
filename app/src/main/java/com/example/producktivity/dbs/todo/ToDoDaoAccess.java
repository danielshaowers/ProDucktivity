package com.example.producktivity.dbs.todo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ToDoDaoAccess {

    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM tasks GROUP BY due_date")
    LiveData<List<Task>> getTasksByDueDate();

    @Query("SELECT * FROM tasks WHERE complete = :complete")
    LiveData<List<Task>> getTasksWithComplete(boolean complete);

    @Insert(onConflict = REPLACE)
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update(onConflict = REPLACE)
    void update(Task task);
}
