import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.producktivity.MainActivity;
import com.example.producktivity.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import TestUtils.RecyclerViewMatcher;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ToDoListInsertTask {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        ViewInteraction floatingActionButton = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(R.id.drawer_layout),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.todo_title),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Title"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.todo_description),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("desc"), closeSoftKeyboard());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.todo_date),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.todo_reminder),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.high_todo), withText("High Priority"),
                        childAtPosition(
                                allOf(withId(R.id.todo_priority),
                                        childAtPosition(
                                                withClassName(is("android.widget.TableLayout")),
                                                4)),
                                0),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.done_button_edit), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        5),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());
    }

    @Test
    public void toDoListInsertTask() {
        ViewInteraction floatingActionButton = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(R.id.drawer_layout),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.todo_title),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Title"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.todo_description),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("desc"), closeSoftKeyboard());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.todo_date),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.todo_reminder),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.high_todo), withText("High Priority"),
                        childAtPosition(
                                allOf(withId(R.id.todo_priority),
                                        childAtPosition(
                                                withClassName(is("android.widget.TableLayout")),
                                                4)),
                                0),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.done_button_edit), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        5),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        navigateToToDoList();

        assertTrue(isTaskPresent("Title", "desc", "High Priority", 1));

    }

    @Test
    public void toDoListInsertFromListPage() {
        navigateToToDoList();

        ViewInteraction floatingActionButton = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.coordinatorLayout),
                                        childAtPosition(
                                                withId(R.id.drawer_layout),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.todo_title),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Title2"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.todo_description),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("desc2"), closeSoftKeyboard());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.todo_date),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.todo_reminder),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.high_todo), withText("High Priority"),
                        childAtPosition(
                                allOf(withId(R.id.todo_priority),
                                        childAtPosition(
                                                withClassName(is("android.widget.TableLayout")),
                                                4)),
                                0),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.done_button_edit), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        5),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        assertTrue(isTaskPresent("Title2", "desc2", "High Priority", 1));
    }

    @Test
    public void toDoListChangeTaskView() {
        navigateToToDoList();

        ViewInteraction spinner = onView(withId(R.id.spinner));
        spinner.check(matches(isDisplayed()));
        spinner.perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Completed Tasks"))).perform(click());
        spinner.check(matches(withSpinnerText(containsString("Completed Tasks"))));

        assertTrue(isEmptyListPresent());

        spinner.check(matches(isDisplayed()));
        spinner.perform(click());
        onData(allOf(is(instanceOf(String.class)), is("All Tasks"))).perform(click());
        spinner.check(matches(withSpinnerText(containsString("All Tasks"))));

        //to do card is displayed
        onView(
                allOf(withId(R.id.todo_card_view),
                        isDisplayed()));

        assertTrue(isTaskPresent("Title", "desc", "High Priority", 0));

    }
/*
    @Test
    public void toDoListEditTask() {
        navigateToToDoList();

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.todo_recyclerview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(1);
        appCompatTextView.perform(click());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.todo_title), withText("Title"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("NewTitle"));

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.todo_description), withText("desc"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(replaceText("newdesc"));

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.low_todo), withText("Low Priority"),
                        childAtPosition(
                                allOf(withId(R.id.todo_priority),
                                        childAtPosition(
                                                withClassName(is("android.widget.TableLayout")),
                                                4)),
                                2),
                        isDisplayed()));
        appCompatRadioButton2.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.done_button_edit), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        5),
                                0),
                        isDisplayed()));
        appCompatButton4.perform(click());

        assertTrue(isTaskPresent("NewTitle", "newdesc", "Low Priority", 0));
    } */

   /* @Test
    public void toDoListDelete() {
        navigateToToDoList();

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.todo_recyclerview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(2);
        appCompatTextView.perform(click());

        assertTrue(isEmptyListPresent());

    }

    @Test
    public void toDoMarkCompleteAndRecover() {
        navigateToToDoList();

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.todo_recyclerview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(0);
        appCompatTextView.perform(click());

        ViewInteraction spinner = onView(withId(R.id.spinner));
        spinner.check(matches(withSpinnerText("Incomplete Tasks")));
        spinner.perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Completed Tasks"))).perform(click());
        spinner.check(matches(withSpinnerText(containsString("Completed Tasks"))));

        assertTrue(isTaskPresent("Title", "desc", "High Priority", 0));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.todo_recyclerview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        withText("Recover Task"),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(0);
        appCompatTextView.perform(click());

        assertTrue(isEmptyListPresent());

        spinner.check(matches(withSpinnerText("Completed Tasks")));
        spinner.perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Incomplete Tasks"))).perform(click());
        spinner.check(matches(withSpinnerText(containsString("Incomplete Tasks"))));

        assertTrue(isTaskPresent("Title", "desc", "High Priority", 0));
    } */



    //Private helper methods
    private void navigateToToDoList() {
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
    }

    private boolean isTaskPresent(String title, String desc, String priority, int pos) {
        onView(
                allOf(withId(R.id.todo_title_view),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withRecyclerView(R.id.todo_recyclerview).atPosition(pos),
                                                0
                                        ), 0
                                ), 0
                        ),
                        isDisplayed(),
                        withText(title)
                )
        );

        onView(
                allOf(withId(R.id.todo_description_view),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withRecyclerView(R.id.todo_recyclerview).atPosition(0),
                                                0
                                        ), 0
                                ), 1
                        ),
                        isDisplayed(),
                        withText(desc)
                )
        );

        Date today = Date.from(Instant.now());
        DateFormat dateFormat = new SimpleDateFormat("MM-dd");


        onView(
                allOf(withId(R.id.todo_date_view),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withRecyclerView(R.id.todo_recyclerview).atPosition(0),
                                                0
                                        ), 0
                                ), 2
                        ),
                        isDisplayed(),
                        withText(dateFormat.format(today))
                )
        );

        onView(
                allOf(withId(R.id.todo_reminder_view),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withRecyclerView(R.id.todo_recyclerview).atPosition(0),
                                                0
                                        ), 0
                                ), 3
                        ),
                        isDisplayed(),
                        withText(dateFormat.format(today))
                )
        );

        onView(
                allOf(withId(R.id.todo_priority),
                        childAtPosition(
                                childAtPosition(
                                        childAtPosition(
                                                withRecyclerView(R.id.todo_recyclerview).atPosition(0),
                                                0
                                        ), 0
                                ), 4
                        ),
                        isDisplayed(),
                        withText(priority)
                )
        );

        return true;
    }

    private boolean isEmptyListPresent() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.empty_list), withText("No tasks to show!"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        return true;
    }

    private static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
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
