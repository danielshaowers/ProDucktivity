
import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.producktivity.dbs.Priority;
import com.example.producktivity.dbs.Task;
import com.example.producktivity.dbs.ToDoDaoAccess;
import com.example.producktivity.dbs.ToDoDatabase;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.platform.app.InstrumentationRegistry;


import java.util.Date;
import java.util.List;

import util.LiveDataTestUtil;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ToDoDBTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ToDoDaoAccess toDoDao;
    private ToDoDatabase db;

    private Task task;
    private Task task2;
    private Task task3;
    private Task task4;

    @Before
    public void createDbAndTestTasks() {
        task = new Task();
        task.setId(1);
        task.setDesc("Test task");
        task.setDueDate(new Date());
        task.setReminderTime(new Date());
        task.setPriority(Priority.HIGH);
        task.setTitle("Test task");

        task2 = new Task();
        task2.setId(2);
        task2.setDesc("Test task 2");
        task2.setDueDate(new Date());
        task2.setReminderTime(new Date());
        task2.setPriority(Priority.HIGH);
        task2.setTitle("Test task 2");

        task3 = new Task();
        task3.setId(3);
        task3.setDesc("Test task 3");
        task3.setDueDate(new Date());
        task3.setReminderTime(new Date());
        task3.setPriority(Priority.LOW);
        task3.setTitle("Test task 3");

        task4 = new Task();
        task4.setId(4);
        task4.setDesc("Test task 4");
        task4.setDueDate(new Date());
        task4.setReminderTime(new Date());
        task4.setPriority(Priority.HIGH);
        task4.setTitle("Test task 4");

        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), ToDoDatabase.class)
                .allowMainThreadQueries().build();
        toDoDao = db.daoAccess();
    }

    @Test
    public void writeAndRead() throws InterruptedException {
        List<Task> taskList = LiveDataTestUtil.getValue(toDoDao.getTasks());
        assertTrue(taskList.isEmpty());
        toDoDao.insert(task);

        taskList = LiveDataTestUtil.getValue(toDoDao.getTasks());
        assertEquals(1, taskList.size());
        assertTrue(taskList.get(0).getTitle().matches("Test task"));

        toDoDao.delete(task);

        assertTrue(LiveDataTestUtil.getValue(toDoDao.getTasks()).isEmpty());
    }

    @Test
    public void testUpdate() throws InterruptedException {

        List<Task> taskList = LiveDataTestUtil.getValue(toDoDao.getTasks());
        assertTrue(taskList.isEmpty());
        toDoDao.insert(task);

        task.setTitle("New test task title");
        toDoDao.update(task);

        assertTrue(LiveDataTestUtil.getValue(toDoDao.getTasks()).get(0).getTitle().matches("New test task title"));
    }

    @Test
    public void testGroupBy()  throws InterruptedException{
        toDoDao.insert(task2);
        toDoDao.insert(task3);
        toDoDao.insert(task4);

        List<Task> orderedTaskList = LiveDataTestUtil.getValue(toDoDao.getTasksByDueDate());
        assertEquals(orderedTaskList.get(0).getId(), 1);
        assertEquals(orderedTaskList.get(1).getId(), 2);
        assertEquals(orderedTaskList.get(2).getId(), 3);
        assertEquals(orderedTaskList.get(3).getId(), 4);
    }


    @After
    public void closeDb() {
        db.close();
    }
}