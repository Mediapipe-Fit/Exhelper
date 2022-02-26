package com.gauravk.bubblebarsample.fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gauravk.bubblebarsample.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.HashSet;

public class CalendarFragment extends Fragment{

    MaterialCalendarView calendarView;
    EventDecorator dotDecoratorGreen;
    EventDecorator dotDecoratorBlue;
    EventDecorator dotDecoratorRed;

    void SetCalendar(){
        HashSet<CalendarDay> datesBlue = new HashSet<>();
        HashSet<CalendarDay> datesRED = new HashSet<>();
        HashSet<CalendarDay> datesGreen = new HashSet<>();
        datesBlue.add(CalendarDay.today());
        datesBlue.add(CalendarDay.from(2021, 10, 15));
        datesRED.add(CalendarDay.from(2022, 2, 10));
        datesRED.add(CalendarDay.from(2022, 2, 7));
        datesGreen.add(CalendarDay.from(2022, 2, 3));

        calendarView = getView().findViewById(R.id.calendar);

        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.state().edit()
                .isCacheCalendarPositionEnabled(false)
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);
        calendarView.setDynamicHeightEnabled(true);
        calendarView.setPadding(0, -20, 0, 30);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = widget.getSelectedDate().getYear();
                int month = widget.getSelectedDate().getMonth();
                int day = widget.getSelectedDate().getDay();

                String today = year + "년" + month + "월" + day + "일";
                Toast.makeText(getActivity(), today, Toast.LENGTH_SHORT).show();
            }
        });
        dotDecoratorBlue = new EventDecorator(CalendarFragment.this, Color.parseColor("#0000FF"), datesBlue);
        dotDecoratorRed = new EventDecorator(CalendarFragment.this, Color.parseColor("#FF0000"), datesRED);
        dotDecoratorGreen = new EventDecorator(CalendarFragment.this, Color.parseColor("#00FF00"), datesGreen);
        calendarView.addDecorator(dotDecoratorBlue);
        calendarView.addDecorator(dotDecoratorRed);
        calendarView.addDecorator(dotDecoratorGreen);

    }

    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
    public void onStart(){
        super.onStart();

        SetCalendar();
    }
}
