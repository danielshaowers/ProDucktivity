package com.example.producktivity.ui.share;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import com.example.producktivity.R;

import androidx.test.runner.AndroidJUnit4;

import com.example.producktivity.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TaskFragmentUpdateTest<ActivityTestRule> {

    @Rule
    public ActivityTestRule mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void taskFragmentUpdateTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(R.id.drawer_layout),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.task_title),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.task_card),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("title1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.task_date),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.task_card),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.done_button), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.task_card),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appBarLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        4),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.todo_title), withText("title1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("title1")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.todo_description), withText("Description"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card),
                                        0),
                                3),
                        isDisplayed()));
        editText2.check(matches(withText("")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.todo_date), withText("58-28"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card),
                                        0),
                                1),
                        isDisplayed()));
        editText3.check(matches(withText("58-28")));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.todo_reminder), withText("58-28"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card),
                                        0),
                                6),
                        isDisplayed()));
        editText4.check(matches(withText("")));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.todo_title), withText("title1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("title2"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.todo_title), withText("title2"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.todo_date), withText("58-28"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("58-29"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.todo_date), withText("58-29"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appBarLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        1),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.appBarLayout),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction navigationMenuItemView3 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        4),
                        isDisplayed()));
        navigationMenuItemView3.perform(click());

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.todo_title), withText("title1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card),
                                        0),
                                0),
                        isDisplayed()));
        editText5.check(matches(withText("title2")));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.todo_date), withText("58-28"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card),
                                        0),
                                1),
                        isDisplayed()));
        editText6.check(matches(withText("58-29")));
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