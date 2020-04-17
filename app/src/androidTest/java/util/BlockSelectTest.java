package util;

import androidx.annotation.Nullable;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
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


    @Test
    public void singleEntry() {
        //create a single fragment
       // AtomicReference<BlockSelectViewModel> blockSelectViewModel = new AtomicReference<>();
        ActivityScenario<MainActivity> activity_sc = ActivityScenario.launch(MainActivity.class);
        activity_sc.onActivity(activity -> {
            //blockSelectViewModel.set(new BlockSelectViewModel(activity.getApplication())); //this should make a database on its own
            BlockSelectViewModel bsvm = new BlockSelectViewModel(activity.getApplication());
            BlacklistEntry next = new BlacklistEntry("fake1");
            next.setDayLimit(1);
            next.setWeekLimit(2);
            next.setUnrestricted(false);
            next.setCategory(Category.ART_AND_DESIGN);
            //blockSelectViewModel.get().insert(next);
            bsvm.insert(next);
            final int[] count = {0};
            bsvm.getSelectList().observe(activity, new Observer<List<BlacklistEntry>>() {
                @Override
                public void onChanged(@Nullable List<BlacklistEntry> s) {
                    //if(count[0]++ == 0) //insert one
                        assertEquals(next, s.get(0));
                    if (count[0]++ == 1) //insert two
                        assertNull(s);
                    //if (count[0]++ == 2)
                }});
            bsvm.replaceDB(new ArrayList<BlacklistEntry>());

           //assertEquals(next, LiveDataTestUtil.getValue(bsvm.getSelectList()).get(0));
        });




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

}
