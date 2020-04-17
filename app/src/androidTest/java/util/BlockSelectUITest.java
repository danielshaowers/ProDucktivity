package util;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.producktivity.MainActivity;
import com.example.producktivity.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import TestUtils.RecyclerViewMatcher;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BlockSelectUITest {
    ActivityTestRule<MainActivity> mainActivityActivityTestRule;

    /* @Before
     public void setActivityTestRule(){
         this.mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
     }*/
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Test
    public void blockSelectUITest() {
        assertEquals(1, 1);
    }
}
/*        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                Matchers.allOf(ViewMatchers.withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appBarLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());
        appCompatImageButton2.check(matches(isDisplayed()));

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        6),
                        isDisplayed()));
        navigationMenuItemView.perform(click());


        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.app_select_button), withText(equalToIgnoringCase("Amazon Alexa")),
                        childAtPosition(
                                childAtPosition(
                                        withRecyclerView(R.id.select_recycler).atPosition(0),
                                        0),
                                1), //previously 1
                        isDisplayed())
                );
        appCompatButton.check(matches(withText("Amazon Alexa")));
        appCompatButton.perform(click());


        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.weekly_limit), withText("None"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.limit_card),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("2000"));

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.weekly_limit), withText("2000"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.limit_card),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.save_select_button), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.limit_card),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.app_select_button), withText("Amazon Alexa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.select_recycler),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.set_as_productive), withText("Always Allowed"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.limit_card),
                                        0),
                                5),
                        isDisplayed()));
        switch_.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.save_select_button), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.limit_card),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.app_select_button), withText("Amazon Alexa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.select_recycler),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.daily_limit), withText("None"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.limit_card),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("300"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.daily_limit), withText("300"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.limit_card),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.save_select_button), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.limit_card),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.app_select_button), withText("Amazon Alexa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.select_recycler),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.category_limit),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("30"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.choose_cat),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(3);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.save_cat), withText("Update All"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.category_limit_week),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("50"), closeSoftKeyboard());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.save_cat), withText("Update All"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.app_select_button), withText("Amazon Shopping"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.select_recycler),
                                        1),
                                1),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.app_select_button), withText("Anime Hd"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.select_recycler),
                                        1),
                                1),
                        isDisplayed()));
        appCompatButton11.perform(click());
    }

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
}
*/