package com.mobileapp.attemp1_calendar;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarViewModel extends ViewModel {

    private Map<String, List<Event>> map = new HashMap<>();

    public void addEvent(String key, Event event) {
        // Check if the key exists in the map
        if (map.containsKey(key)) {
            // Key exists, retrieve the existing list and add the event
            List<Event> existingList = map.get(key);
            existingList.add(event);
        } else {
            // Key doesn't exist, create a new list, add the event, and put it in the map
            List<Event> newList = new ArrayList<>();
            newList.add(event);
            map.put(key, newList);
        }
    }
//    private List<Event> list = new ArrayList<>();

    public Map<String, List<Event>> getEventsMap() {
        return map;
    }

    public List<Event> getListForKey(String key) {
        List<Event> eventList = map.get(key);

        return eventList;
    }
}
