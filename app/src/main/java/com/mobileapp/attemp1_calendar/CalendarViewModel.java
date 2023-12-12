package com.mobileapp.attemp1_calendar;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CalendarViewModel extends ViewModel {

    private List<Event> list = new ArrayList<>();

    public List<Event> getEventsList() {
        return list;
    }

    public void addEvent(Event event) {
        list.add(event);
    }
}
