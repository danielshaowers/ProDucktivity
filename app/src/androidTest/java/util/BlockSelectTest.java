package util;

import androidx.annotation.Nullable;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.producktivity.MainActivity;
import com.example.producktivity.dbs.blacklist.BlacklistDaoAccess;
import com.example.producktivity.dbs.blacklist.BlacklistDatabase;
import com.example.producktivity.dbs.blacklist.BlacklistEntry;
import com.example.producktivity.dbs.blacklist.Category;
import com.example.producktivity.ui.blockSelect.BlockSelectFragment;
import com.example.producktivity.ui.blockSelect.BlockSelectViewModel;
import com.example.producktivity.ui.usage_data.UsageTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import TestUtils.LiveDataTestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BlockSelectTest {
     BlacklistDatabase mDatabase;
     BlacklistDaoAccess dao;

     BlacklistEntry data1;
     BlacklistEntry data2;
     BlacklistEntry data3;
     BlacklistEntry data4;
     List<BlacklistEntry> beList = new ArrayList<>();

    //@Rule //provides functional testing of this activity. launched before each test and before any before method. provides the activity for our initDb method
    //ActivityScenarioRule<SingleFragmentActivity> testActivityRule = new ActivityScenarioRule<>(SingleFragmentActivity.class);


    @Before
    public void initDb() throws Exception {
        System.out.println("does t his test even run");
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                BlacklistDatabase.class)
                .allowMainThreadQueries()
                .build();

        dao = mDatabase.daoAccess();
    }


    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

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
        beList.add(data1);
        beList.add(data2);
        beList.add(data3);
        beList.add(data4);
    }
    @Test
    public void singleEntry() {
        //create a single fragment

        // AtomicReference<BlockSelectViewModel> blockSelectViewModel = new AtomicReference<>();

        BlockSelectFragment frag = new BlockSelectFragment();
        FragmentScenario<BlockSelectFragment> ugh = FragmentScenario.launchInContainer(BlockSelectFragment.class);
        ugh.moveToState(Lifecycle.State.CREATED);
        ugh.onFragment(fragment -> {
            fragment.onCreate(null);
            BlacklistEntry next = new BlacklistEntry("oh");
            BlockSelectViewModel bsvm = fragment.getVM();
            bsvm.insert(next);
            UsageTime ut = new UsageTime("oh", 10, 20, 30, "fakename", UsageTime.DAY);
            List<UsageTime> times = new ArrayList<UsageTime>();
            List<BlacklistEntry> output = bsvm.updateList(times, null);
            assertTrue(output.size() == 0);
            times.add(ut);
            List<BlacklistEntry> list = fragment.getAdapterList();
            list.add(next);
            assertTrue(list.size() > 0);
            assertTrue(list.contains(next));
            List<BlacklistEntry> updated = bsvm.updateList(times, list);
            assertTrue(updated.size() > 0);
            fragment.getAdapter().setLimitList(bsvm.updateList(times, list));


            //assertTrue(fragment.getAdapterList().contains(new BlacklistEntry(ut.appName)));
        });
    }
    /*@Test
    public void testVM(){
          ActivityScenario<MainActivity> activity_sc = ActivityScenario.launch(MainActivity.class);
        activity_sc.moveToState(Lifecycle.State.CREATED);

        activity_sc.onActivity(activity -> {
            //blockSelectViewModel.set(new BlockSelectViewModel(activity.getApplication()));
        BlockSelectViewModel bs = new BlockSelectViewModel(activity.getApplication(), dao, mDatabase);
        //okay so now we have a viewmodel with the database we've defined.
            bs.insert(beList.get(0));
           // assertEquals(LiveDataTestUtil.getValue(bs.getSelectList()).get(0), beList.get(0));
        });
    }*/
        //INTERACTING WITH ACTIVITY!!
      /*  ActivityScenario<MainActivity> activity_sc = ActivityScenario.launch(MainActivity.class);
        activity_sc.moveToState(Lifecycle.State.CREATED); */

       /* activity_sc.onActivity(activity -> {
            //blockSelectViewModel.set(new BlockSelectViewModel(activity.getApplication())); //this should make a database on its own
            BlockSelectViewModel bsvm = activity.getVM();//new BlockSelectViewModel(activity.getApplication());
            //BlockSelectViewModel bsvm = activity.getVM();
            BlacklistEntry next = new BlacklistEntry("fake1");
            next.setDayLimit(1);
            next.setWeekLimit(2);
            next.setUnrestricted(false);
            next.setCategory(Category.BEAUTY);
            //blockSelectViewModel.get().insert(next);
            final int[] count = {0};
            final List<BlacklistEntry>[] ble = new List[]{new ArrayList<BlacklistEntry>()};
            Observer<List<BlacklistEntry>> observer = new Observer<List<BlacklistEntry>>() {
                @Override
                public void onChanged(List<BlacklistEntry> s) {
                            ble[0] = s;
                            assertEquals(ble[0], s); //it's free real estate
                            bsvm.getSelectList().removeObserver(this);
                }
            }; */

            //here we need to create a fragment and give it the observer
           /* BlockSelectFragment frag = new BlockSelectFragment();
            frag.setVM(bsvm);
            FragmentScenario<BlockSelectFragment> ugh = FragmentScenario.launchInContainer(BlockSelectFragment.class);
            ugh.onFragment(fragment -> {
                fragment.setVM(bsvm);
                fragment.onCreate(null);
                bsvm.insert(new BlacklistEntry("oh"));
                List<BlacklistEntry> list = fragment.getAdapterList();
                assertTrue(list.size() > 0);
            }); */


          /*  bsvm.getSelectList().observe(activity, observer);
            assertTrue(ble[0].size() == 0);
            bsvm.insert(next);
            bsvm.getSelectList().observe(activity, observer);
           // if (ble[0].size() > 0)
            assertTrue(ble[0].get(0).equals(next));
            bsvm.insert(new BlacklistEntry("fake2"));
            bsvm.getSelectList().observe(activity, observer);
           // if (ble[0].size() > 1)
                assertTrue(ble[0].get(1).equals(new BlacklistEntry("fake2"))); */

         //   assertNotNull(ble[0]);
        //    bsvm.replaceDB(new ArrayList<BlacklistEntry>());

           //assertEquals(next, LiveDataTestUtil.getValue(bsvm.getSelectList()).get(0));
        /*    BlockSelectViewModel bsvm = activity.getVM();//new BlockSelectViewModel(activity.getApplication());
            //BlockSelectViewModel bsvm = activity.getVM();
            BlacklistEntry next = new BlacklistEntry("fake1");
            next.setDayLimit(1);
            next.setWeekLimit(2);
            next.setUnrestricted(false);
            next.setCategory(Category.BEAUTY);
            //blockSelectViewModel.get().insert(next);
            final int[] count = {0};
            final List<BlacklistEntry>[] ble = new List[]{new ArrayList<BlacklistEntry>()};
            Observer<List<BlacklistEntry>> observer = new Observer<List<BlacklistEntry>>() {
                @Override
                public void onChanged(List<BlacklistEntry> s) {
                    ble[0] = s;
                    assertEquals(ble[0], s); //it's free real estate
                    bsvm.getSelectList().removeObserver(this);
                }
            };
            //here we need to create a fragment and give it the observer
        }); */

      /*  BlacklistEntry next = new BlacklistEntry("fake1");
        next.setDayLimit(1);
        next.setWeekLimit(2);
        next.setUnrestricted(false);
        next.setCategory(Category.ART_AND_DESIGN);
        //blockSelectViewModel.get().insert(next);

        AtomicReference<BlockSelectFragment> frag = new AtomicReference<>();
        FragmentScenario<BlockSelectFragment> fuckme = FragmentScenario.launch(BlockSelectFragment.class);
        fuckme.onFragment(frag::set);
        assertNotNull(frag.get()); //our first test!
        FragmentScenario.launchInContainer(BlockSelectFragment.class);
        fuckme.moveToState(Lifecycle.State.CREATED);
        BlockSelectFragment bsf = new BlockSelectFragment();
    //   assertEquals(1,1); */



    }
  /*  @Test
    public void blockselectTest(){
        FragmentScenario<BlockSelectFragment> fuckme = FragmentScenario.launchInContainer(BlockSelectFragment.class);
        assertEquals(1,1);
        fuckme.onFragment(fragment -> {

        });
    }*/

