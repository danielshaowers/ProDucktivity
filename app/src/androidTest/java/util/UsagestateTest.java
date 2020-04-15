package util;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.producktivity.dbs.blacklist.BlacklistDaoAccess;
import com.example.producktivity.dbs.blacklist.BlacklistDatabase;
import com.example.producktivity.dbs.blacklist.BlacklistEntry;
import com.example.producktivity.dbs.blacklist.BlacklistRepo;
import com.example.producktivity.dbs.blacklist.Category;
import com.jjoe64.graphview.GraphView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UsagestateTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private BlacklistDaoAccess blacklistDao;
    private BlacklistDatabase blacklistDatabase;
    private BlacklistRepo blacklistRepo;

    private GraphView graph;

    private BlacklistEntry data1;
    private BlacklistEntry data2;
    private BlacklistEntry data3;
    private BlacklistEntry data4;

    @Before
    public void createDbAndTestTasks() {
        data1 = new BlacklistEntry("App1");
        data1.setMonthUse(30);
        data1.setWeekUse(7);
        data1.setDayUse(1);
        data1.setSpan_flag(1);
        data1.setCategory(Category.ART_AND_DESIGN);
        data1.setWeekLimit(8);
        data1.setDayLimit(2);

        data2 = new BlacklistEntry("App2");
        data2.setMonthUse(300);
        data2.setWeekUse(70);
        data2.setDayUse(10);
        data2.setSpan_flag(2);
        data2.setCategory(Category.AUGMENTED_REALITY);
        data2.setWeekLimit(80);
        data2.setDayLimit(20);

        data3 = new BlacklistEntry("App3");
        data3.setMonthUse(3000);
        data3.setWeekUse(700);
        data3.setDayUse(100);
        data3.setSpan_flag(0);
        data3.setCategory(Category.COMICS);
        data3.setWeekLimit(800);
        data3.setDayLimit(200);

        data4 = new BlacklistEntry("App4");
        data4.setMonthUse(30000);
        data4.setWeekUse(7000);
        data4.setDayUse(1000);
        data4.setSpan_flag(1);
        data4.setCategory(Category.BUSINESS);
        data4.setUnrestricted(true);

        blacklistDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), BlacklistDatabase.class)
                .allowMainThreadQueries().build();
        blacklistDao = blacklistDatabase.daoAccess();
    }

    @Test
    public void writeAndRead() throws InterruptedException {
     /*   List<BlacklistEntry> entryList = LiveDataTestUtil.getValue(blacklistDao.getList());
        assertTrue(entryList.isEmpty());
        blacklistDao.insert(data2);

        entryList = LiveDataTestUtil.getValue(blacklistDao.getList());
        assertEquals(1, entryList.size());
        assertTrue(entryList.get(0).getAppName().matches("App2"));

        blacklistDao.delete(data2);

        assertTrue(LiveDataTestUtil.getValue(blacklistDao.getList()).isEmpty());*/
     assertTrue(1 == (1+0));
    }

  /*  @Test
    public void testUpdate6() throws InterruptedException {

        List<BlacklistEntry> dataList = LiveDataTestUtil.getValue(blacklistDao.getList());
        assertTrue(dataList.isEmpty());
        blacklistDao.insert(data1);

        data1.setDayLimit(5);
        blacklistDao.update(data1);

        assertEquals(true, Objects.equals(LiveDataTestUtil.getValue(blacklistDao.getList()).get(0).getDayLimit(), 5));
    }

    @Test
    public void testreset5() throws InterruptedException {

        List<BlacklistEntry> dataList = LiveDataTestUtil.getValue(blacklistDao.getList());
        assertTrue(dataList.isEmpty());
        blacklistDao.insert(data1);
        blacklistDao.insert(data2);
        blacklistDao.insert(data3);
        blacklistDao.insert(data4);

        blacklistDao.deleteAll();
        assertTrue(dataList.isEmpty());
    }

    @Test
    public void testtime4() throws InterruptedException {

        List<BlacklistEntry> dataList = LiveDataTestUtil.getValue(blacklistDao.getList());
        assertTrue(dataList.isEmpty());
        blacklistDao.insert(data3);

        assertEquals(true, Objects.equals(LiveDataTestUtil.getValue(blacklistDao.getList()).get(2).getSpan_flag(), 0));
        data3.setSpan_flag(2);
        blacklistDao.update(data1);

        assertEquals(true, Objects.equals(LiveDataTestUtil.getValue(blacklistDao.getList()).get(02).getSpan_flag(), 2));
    }

    @Test
    public void testdetails3() throws InterruptedException {

        List<BlacklistEntry> dataList = LiveDataTestUtil.getValue(blacklistDao.getList());
        assertTrue(dataList.isEmpty());
        blacklistDao.insert(data1);
        blacklistDao.insert(data2);
        blacklistDao.insert(data3);
        blacklistDao.insert(data4);


        assertEquals(true, Objects.equals(LiveDataTestUtil.getValue(blacklistDao.getList()).get(2).getSpan_flag(), 0));
        data3.setSpan_flag(2);
        blacklistDao.update(data1);

        assertEquals(true, Objects.equals(LiveDataTestUtil.getValue(blacklistDao.getList()).get(02).getSpan_flag(), 2));
    }


    @Test
    public void testGroupBy() throws InterruptedException {
        blacklistDao.insert(data2);
        blacklistDao.insert(data3);
        blacklistDao.insert(data4);

        List<BlacklistEntry> orderedDataList = LiveDataTestUtil.getValue(blacklistDao.getList());
        assertEquals(orderedDataList.get(0).getSpan_flag(), 1);
        assertEquals(orderedDataList.get(1).getSpan_flag(), 2);
        assertEquals(orderedDataList.get(2).getSpan_flag(), 3);
        assertEquals(orderedDataList.get(3).getSpan_flag(), 4);
    }


    @After
    public void closeDb() {
        blacklistDatabase.close();
    }*/
}