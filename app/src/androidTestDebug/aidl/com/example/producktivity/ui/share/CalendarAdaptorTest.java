package com.example.producktivity.ui.share;

import android.content.Context;
import android.view.LayoutInflater;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class CalendarAdaptorTest {
    Context context;
    Calendar calendar;

    @Test
    public void getCount() {
        CalendarAdaptor test = new CalendarAdaptor(context, calendar);
        int days = 31;
        days = CalendarAdaptor.getCount();
    }

    @Test
    public void getItem() {
        int item = 20;
        CalendarAdaptor test = new CalendarAdaptor(context, calendar);

        item = (int) test.getItem(20);
    }

    @Test
    public void getItemId() {
    }

    @Test
    public void getView() {
    }

    @Test
    public void setSelected() {
    }

}