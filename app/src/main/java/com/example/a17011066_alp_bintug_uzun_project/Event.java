package com.example.a17011066_alp_bintug_uzun_project;

import java.util.Date;

public class Event {
    //region VARIABLES
    private String eventType;
    private String location;
    private Date date;
    private String name;
    private int ID;
    //endregion
    //region CONSTRUCTORS & GETTERS & SETTERS
    public Event() {
    }

    public Event(String eventType, String location, Date date, String name,int ID) {
        this.eventType = eventType;
        this.location = location;
        this.date = date;
        this.name = name;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //endregion
}