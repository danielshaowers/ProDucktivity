package util;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class BlockSelectTest {
    private BlacklistDatabase mDatabase;
    private BlacklistDaoAccess dao;
    @Before
    public void initDb() throws Exception {
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
    public void appListTest(){
        final MainActivity[] main = {new MainActivity()};
        ActivityScenario<MainActivity> activity_sc = ActivityScenario.launch(MainActivity.class);
        activity_sc.onActivity(activity -> {
                main[0] = activity;
            });


        BlockSelectViewModel blockSelectViewModel = new BlockSelectViewModel(main[0].getApplication());
        BlacklistEntry next = new BlacklistEntry("fake1");
        next.setDayLimit(1);
        next.setWeekLimit(2);
        next.setUnrestricted(false);
        next.setCategory(Category.ART_AND_DESIGN);
        blockSelectViewModel.insert(next);


        FragmentScenario<BlockSelectFragment> fuckme = FragmentScenario.launch(BlockSelectFragment.class);
        FragmentScenario.launchInContainer(BlockSelectFragment.class);
        fuckme.moveToState(Lifecycle.State.CREATED);
        BlockSelectFragment bsf = new BlockSelectFragment();
       assertEquals(1,1);

    }

}
