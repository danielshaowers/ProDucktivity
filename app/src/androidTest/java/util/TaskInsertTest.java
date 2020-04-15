package util;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.producktivity.MainActivity;
import com.example.producktivity.ui.scrolling_to_do.TaskFragment;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class TaskInsertTest {

    TaskFragment taskFrag;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    void setUpFragment() {
        assertTrue(rule.getActivity().getSupportFragmentManager().getFragments().size() == 5);
    }


}
