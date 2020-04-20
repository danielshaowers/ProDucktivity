package com.example.producktivity.ui.share;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.example.producktivity.R;
import com.example.producktivity.dbs.todo.Task;
import com.example.producktivity.ui.scrolling_to_do.ToDoViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarFragment extends Fragment {
    protected final Calendar calendar;
    private final Locale locale;
    private ViewSwitcher calendarSwitcher;
    private TextView currentMonth;
    private CalendarAdapter calendarAdapter;
    private ToDoViewModel tasks;

    //Calendar Adaptor is where we show the switching between days and months etc, while the fragment here shows  the look

    // We use java calendar already set up in this case to create the custom calendar
    public CalendarFragment() {
        calendar = Calendar.getInstance();
        locale = Locale.getDefault();
    }

    //creates the calendar template view, with 7 day grid using grid view and allows for switching month  calling on calendar adaptor
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final RelativeLayout calendarLayout = (RelativeLayout) inflater.inflate(R.layout.calendar, null);
        final GridView calendarDayGrid = (GridView) calendarLayout.findViewById(R.id.calendar_days_grid);
        final GestureDetector swipeDetector = new GestureDetector(getActivity(), new SwipeGesture(getActivity()));
        final GridView calendarGrid = (GridView) calendarLayout.findViewById(R.id.calendar_grid);
      //  final ScrollView tasklist = (ScrollView) calendarLayout.findViewById(R.id.taskview);
        calendarSwitcher = (ViewSwitcher) calendarLayout.findViewById(R.id.calendar_switcher);
        currentMonth = (TextView) calendarLayout.findViewById(R.id.current_month);
        calendarAdapter = new CalendarAdapter(getActivity(), calendar);
        updateCurrentMonth();

        // switches between previous and next month from current based on user clicking
        final TextView nextMonth = (TextView) calendarLayout.findViewById(R.id.next_month);
        nextMonth.setOnClickListener(new NextMonthClickListener());
        final TextView prevMonth = (TextView) calendarLayout.findViewById(R.id.previous_month);
        prevMonth.setOnClickListener(new PreviousMonthClickListener());
        calendarGrid.setOnItemClickListener(new DayItemClickListener());

        calendarGrid.setAdapter(calendarAdapter);


        // allows for option of swipe through current month as well yo get to get to previous and next month
        calendarGrid.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return swipeDetector.onTouchEvent(event);
            }
        });
        calendarDayGrid.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.day_item, getResources().getStringArray(R.array.days_array)));
        return calendarLayout;
    }

    // gets the new month to be shown's specific grid view and placement from calendar adaptor
    protected void updateCurrentMonth() {
        calendarAdapter.refreshDays();
        currentMonth.setText(String.format(locale, "%tB", calendar) + " " + calendar.get(Calendar.YEAR));
    }

    //to get to next month, uses data from current month to update new month
    protected final void onNextMonth() {
        calendarSwitcher.setInAnimation(getActivity(), R.anim.in_from_right);
        calendarSwitcher.setOutAnimation(getActivity(), R.anim.out_to_left);
        calendarSwitcher.showNext();
        if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER) {
            calendar.set((calendar.get(Calendar.YEAR) + 1), Calendar.JANUARY, 1);
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        }
        updateCurrentMonth();
    }

    protected final LiveData<List<Task>> onGetTasks() {
        return tasks.getAllTasks();
    }

    //to add calendar event for specific day chosen
  public void AddCalendarEvent(View view) {
        Calendar calendarEvent = Calendar.getInstance();
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra("beginTime", calendarEvent.getTimeInMillis());
        i.putExtra("allDay", true);
        i.putExtra("rule", "FREQ=YEARLY");
        i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
        i.putExtra("title", "Calendar Event");
        startActivity(i);
    }

    //to get to previous month, uses data from current month to update previous month
    protected final void onPreviousMonth() {
        calendarSwitcher.setInAnimation(getActivity(), R.anim.in_from_left);
        calendarSwitcher.setOutAnimation(getActivity(), R.anim.out_to_right);
        calendarSwitcher.showPrevious();
        if (calendar.get(Calendar.MONTH) == Calendar.JANUARY) {
            calendar.set((calendar.get(Calendar.YEAR) - 1), Calendar.DECEMBER, 1);
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        }
        updateCurrentMonth();
    }

    // selects individual dates based on whether a person clicks on them
    private final class DayItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final TextView dayView = (TextView) view.findViewById(R.id.date);
            final CharSequence text = dayView.getText();
            if (text != null && !"".equals(text)) {
                calendarAdapter.setSelected(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.valueOf(String.valueOf(text)));
            }
        }
    }

    //
    // go back to previous month by clicking on left side icon
    private final class NextMonthClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            onNextMonth();
        }
    }

    // go back to next month by clicking on right side icon
    private final class PreviousMonthClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            onPreviousMonth();
        }
    }

    // allows for switching between months by also swiping leftwards or rightwards so created class based around that
    private final class SwipeGesture extends SimpleOnGestureListener {
        private final int swipeMinDistance;
        private final int swipeThresholdVelocity;

        // swiping constuctor
        public SwipeGesture(Context context) {
            final ViewConfiguration viewConfig = ViewConfiguration.get(context);
            swipeMinDistance = viewConfig.getScaledTouchSlop();
            swipeThresholdVelocity = viewConfig.getScaledMinimumFlingVelocity();
        }

        // establishes whether to get previous or next month based on swipe direction
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThresholdVelocity) {
                onNextMonth();
            } else if (e2.getX() - e1.getX() > swipeMinDistance && Math.abs(velocityX) > swipeThresholdVelocity) {
                onPreviousMonth();
            }
            return false;
        }
    }

}