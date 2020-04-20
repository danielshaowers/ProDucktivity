package com.example.producktivity.ui.share;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.producktivity.R;

import java.util.Calendar;

// fills in all of the calendar related monthly dates and swipe activity
public class CalendarAdapter extends BaseAdapter {
    // calendar set up for monday as first day
    private static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY;
    private final Calendar calendar;
    private final CalendarItem today;
    private final CalendarItem selected;
    private final LayoutInflater inflater;
    private final Context context;
    private CalendarItem[] days;

    //constructor gets each month separately
    public CalendarAdapter(Context context, Calendar monthCalendar) {
        calendar = monthCalendar;
        today = new CalendarItem(monthCalendar);
        selected = new CalendarItem(monthCalendar);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    //get number of days for any month
    @Override
    public int getCount() {
        return days.length;
    }

    //for each box show date associated with it
    @Override
    public Object getItem(int position) {
        return days[position];
    }

    // helper method for visualizing data
    @Override
    public long getItemId(int position) {
        final CalendarItem item = days[position];
        if (item != null) {
            return days[position].id;
        }
        return -1;
    }

    // shows each date separately and allows for clicking between them, today's date is always highlighted in green
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = inflater.inflate(R.layout.calendar_item, null);
        }
        final TextView dayView = (TextView) view.findViewById(R.id.date);
        final CalendarItem currentItem = days[position];

        if (currentItem == null) {
            dayView.setClickable(false);
            dayView.setFocusable(false);
            dayView.setText(null);
        } else {
            view.setForegroundGravity(1);
            if (currentItem.equals(today)) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            } else if (currentItem.equals(selected)) {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.greyed_out));
            } else {
                view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
            }
            dayView.setText(currentItem.text);
        }

        return view;
    }

    //
    // to highlight and select a chosen date
    public final void setSelected(int year, int month, int day) {
        selected.year = year;
        selected.month = month;
        selected.day = day;
        notifyDataSetChanged();
    }

    // to switch etween months and still show synced dates where we find the first and last day of each month specifically , once we find the first and last
    //day we can fill in the rest of the dates with corresponding placement
    public final void refreshDays() {
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        final int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        final int example;
        final CalendarItem[] days;

        if (firstDayOfMonth == FIRST_DAY_OF_WEEK) {
            example = 0;
        } else if (firstDayOfMonth < FIRST_DAY_OF_WEEK) {
            example = Calendar.SATURDAY - (FIRST_DAY_OF_WEEK - 1);
        } else {
            example = firstDayOfMonth - FIRST_DAY_OF_WEEK;
        }
        days = new CalendarItem[lastDayOfMonth + example];

        for (int day = 1, position = example; position < days.length; position++) {
            days[position] = new CalendarItem(year, month, day++);
        }

        this.days = days;
        notifyDataSetChanged();
    }

    // class within the calendar that creates each date i the calendar separately
    private static class CalendarItem {
        public int year;
        public int month;
        public int day;
        public String text;
        public long id;

        // constructor
        public CalendarItem(Calendar calendar) {
            this(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }

        //constructor for each specific day to be visualized
        public CalendarItem(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.text = String.valueOf(day);
            this.id = Long.valueOf(year + "" + month + "" + day);
        }

        // boolean checks for same date
        @Override
        public boolean equals(Object o) {
            if (o != null && o instanceof CalendarItem) {
                final CalendarItem item = (CalendarItem) o;
                return item.year == year && item.month == month && item.day == day;
            }
            return false;
        }
    }
}