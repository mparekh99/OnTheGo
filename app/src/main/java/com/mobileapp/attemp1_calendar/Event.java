package com.mobileapp.attemp1_calendar;

import java.util.Objects;

public class Event {
    private String category;
    private String title;
    private String description;
    private String date;
    private String time;

    public Event(String category, String title, String description, String date, String time) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Event{" +
                "category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Event otherEvent = (Event) obj;

        return Objects.equals(category, otherEvent.category)
                && Objects.equals(title, otherEvent.title)
                && Objects.equals(description, otherEvent.description)
                && Objects.equals(date, otherEvent.date)
                && Objects.equals(time, otherEvent.time);
    }
}

