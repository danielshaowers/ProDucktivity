package util;


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

import junit.framework.TestCase;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
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
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateTaskTest extends TestCase {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createTaskTest() {
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
                allOf(withId(R.id.activate_blocker),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        switch_2.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView6), withText("Begin blocking"),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Begin blocking")));

        ViewInteraction switch_3 = onView(
                allOf(withId(R.id.smart_blocker),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                2),
                        isDisplayed()));
        switch_3.check(matches(isDisplayed()));

        ViewInteraction switch_4 = onView(
                allOf(withId(R.id.smart_blocker), withText("Leap of faith"),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                2),
                        isDisplayed()));
        switch_4.perform(click());

        ViewInteraction switch_5 = onView(
                allOf(withId(R.id.smart_blocker),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                2),
                        isDisplayed()));
        switch_5.check(matches(isDisplayed()));

        ViewInteraction switch_6 = onView(
                allOf(withId(R.id.activate_blocker),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        switch_6.check(matches(isDisplayed()));

        ViewInteraction switch_7 = onView(
                allOf(withId(R.id.activate_blocker),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        switch_7.check(matches(isDisplayed()));

        ViewInteraction switch_8 = onView(
                allOf(withId(R.id.activate_blocker), withText("Your settings"),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        switch_8.perform(click());

        ViewInteraction switch_9 = onView(
                allOf(withId(R.id.activate_blocker),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        switch_9.check(matches(isDisplayed()));

        ViewInteraction switch_10 = onView(
                allOf(withId(R.id.activate_blocker),
                        childAtPosition(
                                allOf(withId(R.id.block_apps),
                                        childAtPosition(
                                                withId(R.id.nav_host_fragment),
                                                0)),
                                1),
                        isDisplayed()));
        switch_10.check(matches(isDisplayed()));

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

       /* ViewInteraction editText = onView(
                allOf(withId(R.id.todo_title), withText("Enter title"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText.check(matches(withText("Enter title")));*/

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.todo_date), withText("Deadline"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        editText2.check(matches(withText("Deadline")));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.todo_reminder), withText("Reminder"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        2),
                                0),
                        isDisplayed()));
        editText3.check(matches(withText("Reminder")));

        ViewInteraction editText4 = onView(
                allOf(withId(R.id.todo_description), withText("Description"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText4.check(matches(withText("Description")));

        ViewInteraction radioButton = onView(
                allOf(withId(R.id.high_todo),
                        childAtPosition(
                                allOf(withId(R.id.todo_priority),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                                4)),
                                0),
                        isDisplayed()));
        radioButton.check(matches(isDisplayed()));

        ViewInteraction radioButton2 = onView(
                allOf(withId(R.id.medium_todo),
                        childAtPosition(
                                allOf(withId(R.id.todo_priority),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                                4)),
                                1),
                        isDisplayed()));
        radioButton2.check(matches(isDisplayed()));

        ViewInteraction radioButton3 = onView(
                allOf(withId(R.id.low_todo),
                        childAtPosition(
                                allOf(withId(R.id.todo_priority),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                                4)),
                                2),
                        isDisplayed()));
        radioButton3.check(matches(isDisplayed()));

        ViewInteraction button = onView(
                allOf(withId(R.id.done_button_edit),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        5),
                                0),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.done_button_edit),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        5),
                                0),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.todo_title),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("helloh"), closeSoftKeyboard());

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

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.todo_description),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("yur"), closeSoftKeyboard());

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

        ViewInteraction editText5 = onView(
                allOf(withId(R.id.todo_title), withText("helloh"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText5.check(matches(withText("helloh")));

        ViewInteraction editText6 = onView(
                allOf(withId(R.id.todo_date), withText("15/4"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        1),
                                0),
                        isDisplayed()));
        editText6.check(matches(withText("15/4")));

        ViewInteraction editText7 = onView(
                allOf(withId(R.id.todo_reminder), withText("14/4"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        2),
                                0),
                        isDisplayed()));
        editText7.check(matches(withText("14/4")));

        ViewInteraction editText8 = onView(
                allOf(withId(R.id.todo_description), withText("yur"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        0),
                                0),
                        isDisplayed()));
        editText8.check(matches(withText("yur")));

        ViewInteraction radioButton4 = onView(
                allOf(withId(R.id.high_todo),
                        childAtPosition(
                                allOf(withId(R.id.todo_priority),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                                4)),
                                0),
                        isDisplayed()));
        radioButton4.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.done_button_edit),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        5),
                                0),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.done_button_edit),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class),
                                        5),
                                0),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.done_button_edit), withText("Done"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        5),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

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

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.textView), withText("Take flight with your productivity"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation_header_container),
                                        0),
                                2),
                        isDisplayed()));
        textView2.check(matches(withText("Take flight with your productivity")));

        ViewInteraction textView3 = onView(
                allOf(withText("ProDucktive"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation_header_container),
                                        0),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("ProDucktive")));

        ViewInteraction checkedTextView = onView(
                allOf(withId(R.id.design_menu_item_text),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.design_navigation_view),
                                        6),
                                0),
                        isDisplayed()));
        checkedTextView.check(matches(isDisplayed()));

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        4),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.todo_title_view), withText("helloh"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card_view),
                                        0),
                                0),
                        isDisplayed()));
        textView4.check(matches(withText("helloh")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.todo_description_view), withText("yur"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card_view),
                                        0),
                                1),
                        isDisplayed()));
        textView5.check(matches(withText("yur")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.textView2), withText("Due Date"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card_view),
                                        0),
                                2),
                        isDisplayed()));
        textView6.check(matches(withText("Due Date")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.textView3), withText("Reminder"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card_view),
                                        0),
                                4),
                        isDisplayed()));
        textView7.check(matches(withText("Reminder")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.todo_reminder_view), withText("23-14"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card_view),
                                        0),
                                5),
                        isDisplayed()));
        textView8.check(matches(withText("23-14")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.todo_date_view), withText("23-15"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card_view),
                                        0),
                                3),
                        isDisplayed()));
        textView9.check(matches(withText("23-15")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.priority_view), withText("High Priority"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todo_card_view),
                                        0),
                                6),
                        isDisplayed()));
        textView10.check(matches(withText("High Priority")));

        ViewInteraction textView11 = onView(
                allOf(withId(android.R.id.text1), withText("Incomplete Tasks"),
                        childAtPosition(
                                allOf(withId(R.id.spinner),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                                1)),
                                0),
                        isDisplayed()));
        textView11.check(matches(withText("Incomplete Tasks")));

        ViewInteraction spinner = onView(
                allOf(withId(R.id.spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        spinner.check(matches(isDisplayed()));

        ViewInteraction spinner2 = onView(
                allOf(withId(R.id.spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                1),
                        isDisplayed()));
        spinner2.check(matches(isDisplayed()));

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.todo_recyclerview),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.todo_recyclerview),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction linearLayout3 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.todo_recyclerview),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        linearLayout3.perform(click());

        ViewInteraction linearLayout4 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.todo_recyclerview),
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        0)),
                        0),
                        isDisplayed()));
        linearLayout4.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(0);
        appCompatTextView.perform(click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(0);
        appCompatTextView2.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                2),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

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
