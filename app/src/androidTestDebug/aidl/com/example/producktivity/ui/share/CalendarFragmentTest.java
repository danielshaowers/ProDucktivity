package com.example.producktivity.ui.share;

import java.util.Calendar;
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
import com.example.producktivity.R;


import androidx.fragment.app.Fragment;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.producktivity.R;

import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(AndroidJUnit4.class)
class CalendarFragmentTest {

    private CalendarAdaptor calendar;
    private TextView currentMonth;
    protected final Calendar calendar1;
    private final Locale locale;
    private ViewSwitcher calendarSwitcher;

    public CalendarFragmentTest() {
        calendar1 = Calendar.getInstance();
        locale = Locale.getDefault();
    }

    void onCreateView() {
    }

    void updateCurrentMonth() {
        calendar.refreshDays();

    }

    void onNextMonth() {
        calendarSwitcher.setInAnimation(getActivity(), R.anim.in_from_left);
        calendarSwitcher.setOutAnimation(getActivity(), R.anim.out_to_right);
        calendarSwitcher.showPrevious();
        if (calendar1.get(Calendar.MONTH) == Calendar.JANUARY) {
            calendar1.set((calendar1.get(Calendar.YEAR) - 1), Calendar.DECEMBER, 1);
        } else {
            calendar1.set(Calendar.MONTH, calendar1.get(Calendar.MONTH) - 1);
        }
    }

    void onPreviousMonth() {
    }
}