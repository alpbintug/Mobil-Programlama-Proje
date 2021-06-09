package com.example.a17011066_alp_bintug_uzun_project;

public class Event {
    //region VARIABLES
    private String eventType;
    private String location;
    private String date;
    private String name;
    private Combine combine;
    //endregion
    //region CONSTRUCTORS & GETTERS & SETTERS
    public Event() {
    }

    public Event(String eventType, String location, String date, String name,Combine combine) {
        this.eventType = eventType;
        this.location = location;
        this.date = date;
        this.name = name;
        this.combine = combine;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Combine getCombine() {
        return combine;
    }

    public void setCombine(Combine combine) {
        this.combine = combine;
    }
    //endregion
}