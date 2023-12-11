package com.mobileapp.attemp1_calendar;

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

    // Add getters and setters if needed

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
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
}

