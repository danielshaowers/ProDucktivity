package util;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.uiautomator.UiObjectNotFoundException;

import com.example.producktivity.MainActivity;
import com.example.producktivity.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class blockActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Test
    public void blockActivityTest() throws InterruptedException, UiObjectNotFoundException {
        ViewInteraction switch_ = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.activate_blocker), withText("Your settings"),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        switch_.perform(click());

        ViewInteraction switch_2 = onView(
                allOf(withId(R.id.smart_blocker), withText("Leap of faith"),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                2),
                        isDisplayed()));
        switch_2.perform(click());


        /*ViewInteraction switch_3 = onView(
                allOf(withId(R.id.activate_blocker), withText("Your settings"),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        switch_3.perform(click());

        ViewInteraction switch_4 = onView(
                allOf(withId(R.id.activate_blocker), withText("Your settings"),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        switch_4.perform(click());*/

        /*UiDevice myDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        myDevice.pressHome();
UiObject allApps = myDevice.findObject(new UiSelector().description("Apps"));
        allApps.clickAndWaitForNewWindow();


        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.setAsHorizontalList();

        UiObject clock = appViews.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()),"Clock");
        clock.clickAndWaitForNewWindow();


        Thread.sleep(50000);

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.continueDuck),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.ReturnButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());*/
    }
}
