package com.example.producktivity;

import com.example.producktivity.dbs.blacklist.BlacklistEntry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import androidx.test.rule.ActivityTestRule;

import static org.junit.Assert.*;

public class BlacklistClientTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    BlacklistClient c;
    BlacklistClient c2;

    @Before
    public void setUp() throws Exception {
        BlacklistEntry app = new BlacklistEntry("UnrestrictedApp");
        app.setUnrestricted(true);
        app.setPackageName("App1");
        BlacklistEntry app2 = new BlacklistEntry("HasTimeLimit");
        app2.setWeekLimit(10);
        app2.setPackageName("App2");
        List<BlacklistEntry> s = new ArrayList<BlacklistEntry>();
        s.add(app);
        s.add(app2);
        c = new BlacklistClient(s, rule.getActivity().getApplicationContext());
    }

    @Test
    public void classifyTrueApp() {
        Boolean appClass = c.classifyApp("App1");
        assertEquals(true, appClass);
    }

    @Test
    public void classifyFalseApp() {
        Boolean app2Class = c.classifyApp("App2");
        assertEquals(false, app2Class);
    }

    @Before
    public void setUpVoteTest(){
        BlacklistEntry appInQuestion = new BlacklistEntry("app");
        appInQuestion.setPackageName("appInQuestion");
        BlacklistEntry app2 = new BlacklistEntry("app2");
        app2.setUnrestricted(true);
        BlacklistEntry app3 = new BlacklistEntry("app3");
        app3.setUnrestricted(true);
        List<BlacklistEntry> list = new ArrayList<BlacklistEntry>();
        list.add(appInQuestion);
        list.add(app2);
        list.add(app3);
        c2 = new BlacklistClient(list, rule.getActivity().getApplicationContext());
    }

    @Test
    public void classifyAppVote(){
        Boolean g = c2.classifyApp("appInQuestion");
        assertEquals(true, g);
    }


}