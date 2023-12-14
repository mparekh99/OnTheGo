package com.mobileapp.attemp1_calendar;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarViewModel extends ViewModel {

    private Map<String, List<Event>> map = new HashMap<>();

    public void addEvent(String date, Event event) {
        if (!map.containsKey(date)) {
            // Creating a new list and adding the event if the date doesn't exist
            List<Event> newList = new ArrayList<>();
            newList.add(event);
            map.put(date, newList);
        } else {
            // Else, checks to see if there's a duplicate event
            List<Event> eventExists = map.get(date);
            boolean isDuplicateEvent = false;

            for (Event eventExist : eventExists) {
                if (eventExist.equals(event)) {
                    // Duplicate founded
                    isDuplicateEvent = true;
                    break;
                }
            }

            // If duplicate is not found, then add a new event
            if (!isDuplicateEvent) {
                eventExists.add(event);
            }
        }
    }

    public Map<String, List<Event>> getEventsMap() {
        return map;
    }

    public List<Event> getListForKey(String key) {
        List<Event> eventList = map.get(key);

        return eventList;
    }
}
