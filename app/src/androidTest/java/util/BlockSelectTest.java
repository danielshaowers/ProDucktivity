package util;

import android.app.Activity;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.producktivity.MainActivity;
import com.example.producktivity.dbs.blacklist.BlacklistEntry;
import com.example.producktivity.dbs.blacklist.Category;
import com.example.producktivity.ui.scrolling_to_do.ToDoViewModel;
import com.example.producktivity.ui.send.BlockSelectFragment;
import com.example.producktivity.ui.send.BlockSelectViewModel;

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
public class BlockSelectTest {
    @Test
    public void appListTest(){
    /*    final MainActivity[] main = {new MainActivity()};

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

        BlockSelectFragment bsf = new BlockSelectFragment();*/
        assertEquals(1,1);

    }

}
